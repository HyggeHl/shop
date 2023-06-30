package com.hygge.shop.admin.service.impl;

import com.hygge.shop.admin.entity.dto.MultipartFileDTO;
import com.hygge.shop.admin.enums.UploadConstants;
import com.hygge.shop.admin.service.BreakPointFileService;
import com.hygge.shop.common.api.apiRetuen.CommonResult;
import com.hygge.shop.common.utils.FileMD5Util;
import com.hygge.shop.common.utils.FileUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program: shop
 * @description: 文件分块断点上传
 * @author: hygge
 * @create: 2023/06/26
 */
@Service
public class BreakPointFileServiceImpl implements BreakPointFileService {

  private final static Logger log = LoggerFactory.getLogger(BreakPointFileServiceImpl.class);

  // 保存文件的根目录
  private Path rootPath;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  //这个必须与前端设定的值一致
  @Value("${breakpoint.upload.chunkSize}")
  private long CHUNK_SIZE;

  @Value("${breakpoint.upload.dir}")
  private String finalDirPath;

  @Autowired
  public BreakPointFileServiceImpl(@Value("${breakpoint.upload.dir}") String location) {
    this.rootPath = Paths.get(location);
  }


  @Override
  public void deleteAll() {
    log.info("开发初始化清理数据，start");
    FileSystemUtils.deleteRecursively(rootPath.toFile());
    stringRedisTemplate.delete(UploadConstants.FILE_UPLOAD_STATUS.getValue());
    stringRedisTemplate.delete(UploadConstants.FILE_MD5_KEY.getValue());
    log.info("开发初始化清理数据，end");
  }

  @Override
  public void init() {
    try {
      Files.createDirectory(rootPath);
    } catch (FileAlreadyExistsException e) {
      log.error("文件夹已经存在了，不用再创建。");
    } catch (IOException e) {
      log.error("初始化root文件夹失败。", e);
    }
  }

  @Override
  public boolean uploadFileRandomAccessFile(MultipartFileDTO multipartFileDTO) throws IOException {
    String fileName = multipartFileDTO.getFilename();
    String tempDirPath = finalDirPath + multipartFileDTO.getIdentifier();
    String tempFileName = fileName + "_tmp";
    File tmpDir = new File(tempDirPath);
    File tmpFile = new File(tempDirPath, tempFileName);
    if (!tmpDir.exists()) {
      tmpDir.mkdirs();
    }

    RandomAccessFile accessTmpFile = new RandomAccessFile(tmpFile, "rw");
    long offset = CHUNK_SIZE * (multipartFileDTO.getChunkNumber() - 1);
    //定位到该分片的偏移量
    accessTmpFile.seek(offset);
    //写入该分片数据
    accessTmpFile.write(multipartFileDTO.getFile().getBytes());
    // 释放
    accessTmpFile.close();

    boolean isOk = checkAndSetUploadProgress(multipartFileDTO, tempDirPath);
    if (isOk) {
      boolean flag = renameFile(tmpFile, fileName);
      System.out.println("upload complete !!" + flag + " name=" + fileName);
      return flag;
    }
    return false;
  }

  @Override
  public boolean uploadFileByMappedByteBuffer(MultipartFileDTO multipartFileDTO) throws IOException {
    //路径：服务器存储地址/md5字符串
    String uploadDirPath = finalDirPath + multipartFileDTO.getIdentifier();
    File tmpDir = new File(uploadDirPath);
    if (!tmpDir.exists()) {
      tmpDir.mkdirs();
    }

    String fileName = multipartFileDTO.getFilename();
    //临时文件名
    String tempFileName = fileName + "_tmp";
    File tmpFile = new File(uploadDirPath, tempFileName);

    //读操作和写操作都是允许的
    RandomAccessFile tempRaf = new RandomAccessFile(tmpFile, "rw");
    //它返回的就是nio通信中的file的唯一channel
    FileChannel fileChannel = tempRaf.getChannel();

    //写入该分片数据   分片大小 * 第几块分片获取偏移量
    long offset = CHUNK_SIZE * (multipartFileDTO.getChunkNumber() - 1);
    //分片文件大小
    byte[] fileData = multipartFileDTO.getFile().getBytes();
    //将文件的区域直接映射到内存
    MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileData.length);
    mappedByteBuffer.put(fileData);
    // 释放
    FileMD5Util.freedMappedByteBuffer(mappedByteBuffer);
    fileChannel.close();

    boolean isOk = checkAndSetUploadProgress(multipartFileDTO, uploadDirPath);
    if (isOk) {
      boolean flag = renameFile(tmpFile, fileName);
      System.out.println("upload complete !!" + flag + " name=" + fileName);
      return flag;
    }
    return false;
  }

  /**
   * 检查并修改文件上传进度
   *
   * @multipartFileDTO multipartFileDTO
   * @multipartFileDTO uploadDirPath
   * @return
   * @throws IOException
   */
  private boolean checkAndSetUploadProgress(MultipartFileDTO multipartFileDTO, String uploadDirPath) throws IOException {
    String fileName = multipartFileDTO.getFilename();
    //路径/filename.conf
    File confFile = new File(uploadDirPath, fileName + ".conf");
    RandomAccessFile accessConfFile = new RandomAccessFile(confFile, "rw");
    //把该分段标记为 true 表示完成
    System.out.println("set part " + multipartFileDTO.getChunkNumber() + " complete");
    accessConfFile.setLength(multipartFileDTO.getTotalChunks());
    accessConfFile.seek(multipartFileDTO.getChunkNumber() - 1);
    accessConfFile.write(Byte.MAX_VALUE);

    //completeList 检查是否全部完成,如果数组里是否全部都是(全部分片都成功上传)
    byte[] completeList = FileUtils.readFileToByteArray(confFile);
    byte isComplete = Byte.MAX_VALUE;
    for (int i = 0; i < completeList.length && isComplete == Byte.MAX_VALUE; i++) {
      //与运算, 如果有部分没有完成则 isComplete 不是 Byte.MAX_VALUE
      isComplete = (byte) (isComplete & completeList[i]);
      System.out.println("check part " + (i + 1) + " complete?:" + completeList[i]);
    }

    accessConfFile.close();
    //更新redis中的状态：如果是true的话证明是已经该大文件全部上传完成
    if (isComplete == Byte.MAX_VALUE) {
      stringRedisTemplate.opsForHash().put(UploadConstants.FILE_UPLOAD_STATUS.getValue(), multipartFileDTO.getIdentifier(), "true");
      stringRedisTemplate.opsForValue().set(UploadConstants.FILE_MD5_KEY + multipartFileDTO.getIdentifier(), uploadDirPath + "/" + fileName);
      return true;
    } else {
      if (!stringRedisTemplate.opsForHash().hasKey(UploadConstants.FILE_UPLOAD_STATUS.getValue(), multipartFileDTO.getIdentifier())) {
        stringRedisTemplate.opsForHash().put(UploadConstants.FILE_UPLOAD_STATUS.getValue(), multipartFileDTO.getIdentifier(), "false");
      }
      if (!stringRedisTemplate.hasKey(UploadConstants.FILE_MD5_KEY + multipartFileDTO.getIdentifier())) {
        stringRedisTemplate.opsForValue().set(UploadConstants.FILE_MD5_KEY + multipartFileDTO.getIdentifier(), uploadDirPath + "/" + fileName + ".conf");
      }
      return false;
    }
  }

  /**
   * 文件重命名
   *
   * @multipartFileDTO toBeRenamed   将要修改名字的文件
   * @multipartFileDTO toFileNewName 新的名字
   * @return
   */
  public boolean renameFile(File toBeRenamed, String toFileNewName) {
    //检查要重命名的文件是否存在，是否是文件
    if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
      log.info("File does not exist: " + toBeRenamed.getName());
      return false;
    }
    String p = toBeRenamed.getParent();
    File newFile = new File(p + File.separatorChar + toFileNewName);
    //修改文件名
    return toBeRenamed.renameTo(newFile);
  }

  @Override
  public void downloadByName(MultipartFileDTO multipartFileDTO, HttpServletRequest request, HttpServletResponse response) {
    try {
      Object processingObj = stringRedisTemplate.opsForHash().get(UploadConstants.FILE_UPLOAD_STATUS.getValue(), multipartFileDTO.getIdentifier());
      if(processingObj == null || Boolean.parseBoolean(processingObj.toString()) == false) {
        String data = "下载出错！";
        response.setHeader("content-type", "text/html;charset=UTF-8");//通过设置响应头控制浏览器以UTF-8的编码显示数据
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
      } else {
        String value = stringRedisTemplate.opsForValue().get(UploadConstants.FILE_MD5_KEY + multipartFileDTO.getIdentifier());
        File toFile = new File(value);

        long chunkSize = multipartFileDTO.getChunkSize();
        long chunkNumber = multipartFileDTO.getChunkNumber();
        long totalChunks  =multipartFileDTO.getTotalChunks();

        File resultFile = new File(value);

        //最后一片前端已计算好chunkSize
        long offset = (long) multipartFileDTO.getChunkSize() * (multipartFileDTO.getChunkNumber() - 1);
        if(Objects.equals(chunkNumber, totalChunks)){
          offset = resultFile.length() - chunkSize;
        }

        RandomAccessFile randomAccessFile = new RandomAccessFile(value, "r");
        // 定位到该分片的偏移量
        randomAccessFile.seek(offset);
        //读取
        byte[] buffer = new byte[(int) chunkSize];
        randomAccessFile.read(buffer);

        log.info("下载文件分片" + value + "," + chunkNumber + "," + chunkSize + "," + buffer.length + "," + offset);
        String filename = FileUtil.filenameEncoding(multipartFileDTO.getFilename(), request);
//        response.addHeader("Access-Control-Allow-Origin","Content-Disposition");
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        response.addHeader("Content-Length", "" + (buffer.length));
        response.setHeader("filename", filename);

        response.setContentType("application/octet-stream");

        ServletOutputStream out = null;
        out = response.getOutputStream();
        out.write(buffer);
        out.flush();
        out.close();
//        FileUtil.downloadFile(request, response, toFile, false);
      }
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public CommonResult<List<MultipartFileDTO>> getUploadedFile() {
    Map<Object, Object> hashEntries = stringRedisTemplate.opsForHash().entries(UploadConstants.FILE_UPLOAD_STATUS.getValue());
    List<MultipartFileDTO> rtnData = new ArrayList<>();
    List<String> uploadedFiles = new ArrayList<>();
    hashEntries.forEach((key, value) -> {
      if(Boolean.parseBoolean(String.valueOf(value))) {
        uploadedFiles.add(String.valueOf(key));
      }
    });
    uploadedFiles.forEach(md5 -> {
      String value = stringRedisTemplate.opsForValue().get(UploadConstants.FILE_MD5_KEY + md5);
      if(!ObjectUtils.isEmpty(value)) {
        File file = new File(value);
        int fileSplitLen = value.split("/").length;
        MultipartFileDTO multipartFileDTO = MultipartFileDTO.builder()
          .filename(value)
          .identifier(md5)
          .totalSize(file.length())
          .relativePath(value).filename(value.split("/")[fileSplitLen - 1])
          .build();
        rtnData.add(multipartFileDTO);
      }
    });
    return CommonResult.success(rtnData);
  }

}
