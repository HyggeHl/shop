package com.hygge.shop.admin.controller;

import com.hygge.shop.common.api.apiRetuen.CommonResult;
import com.hygge.shop.admin.entity.dto.MultipartFileDTO;
import com.hygge.shop.admin.enums.UploadConstants;
import com.hygge.shop.admin.service.impl.BreakPointFileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: shop
 * @description: 文件分片上传，断点上传，秒传。文件分片下载
 * @author: hygge
 * @create: 2023/06/26
 */
@Slf4j
@Controller
@RequestMapping("/fileUpload")
public class BreakPointFileController {

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Autowired
  private BreakPointFileServiceImpl breakPointFileService;


  /**
   * @author itcheetah
   * @description 秒传判断，断点判断
   * @date 2021/7/31 10:33
   * @Param [md5]
   * @return java.lang.Object
   **/
  @RequestMapping(value = "/upload", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult checkFileMd5(MultipartFileDTO multipartFileDTO) throws IOException {
    String md5 = multipartFileDTO.getIdentifier();
    //文件是否上传
    Object processingObj = stringRedisTemplate.opsForHash().get(UploadConstants.FILE_UPLOAD_STATUS.getValue(), md5);
    if (processingObj == null) {
      return CommonResult.success(null,"该文件没有上传过");
    }
    boolean processing = Boolean.parseBoolean(processingObj.toString());
    String value = stringRedisTemplate.opsForValue().get(UploadConstants.FILE_MD5_KEY + md5);
    //完整文件上传完成是true，未完成时false
    if (processing) {
      return CommonResult.success(value,"文件已存在");
    } else {
      File confFile = new File(value);
      byte[] completeList = FileUtils.readFileToByteArray(confFile);
      List<Integer> missChunkList = new LinkedList<>();
      for (int i = 0; i < completeList.length; i++) {
        if (completeList[i] == Byte.MAX_VALUE) {
          missChunkList.add(i);
        }
      }
      return CommonResult.noContent(missChunkList, "该文件上传了一部分");
    }
  }

  /**
   * 上传文件
   *
   * @param multipartFileDTO
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult fileUpload(MultipartFileDTO multipartFileDTO, HttpServletRequest request) {
    //是否是带文件的请求
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    if (isMultipart) {
      log.info("上传文件start。");
      try {
        // 方法1
        boolean flag = breakPointFileService.uploadFileRandomAccessFile(multipartFileDTO);
        // 方法2 这个更快点
//        boolean flag = breakPointFileService.uploadFileByMappedByteBuffer(multipartFileDTO);
        if(flag){
          return CommonResult.success(null, "上传成功");
        }
      } catch (IOException e) {
        e.printStackTrace();
        log.error("文件上传失败。{}", multipartFileDTO.toString());
      }
      log.info("上传文件end。");
    }
    return CommonResult.success(null, "分片上传成功");
  }

  @GetMapping(value = "/getUploadedFile")
  @ResponseBody
  public CommonResult<List<MultipartFileDTO>> getUploadedFile() {
    return breakPointFileService.getUploadedFile();
  }

  @PostMapping(value = "/download")
  @ResponseBody
  public void downloadbyname(HttpServletRequest request, HttpServletResponse response, MultipartFileDTO multipartFileDTO) throws IOException {
    breakPointFileService.downloadByName(multipartFileDTO, request, response);
  }
}
