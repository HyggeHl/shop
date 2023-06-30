package com.hygge.shop.admin.service;

import com.hygge.shop.admin.entity.dto.MultipartFileDTO;
import com.hygge.shop.common.api.apiRetuen.CommonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface BreakPointFileService {

  /**
   * 删除全部数据
   */
  void deleteAll();

  /**
   * 初始化方法
   */
  void init();

  /**
   * 上传文件方法1
   *
   * @param multipartFileDTO
   * @throws IOException
   */
  boolean uploadFileRandomAccessFile(MultipartFileDTO multipartFileDTO) throws IOException;

  /**
   * 上传文件方法2
   * 处理文件分块，基于MappedByteBuffer来实现文件的保存
   *
   * @param multipartFileDTO
   * @throws IOException
   */
  boolean uploadFileByMappedByteBuffer(MultipartFileDTO multipartFileDTO) throws IOException;

  /**
   * 获取已上传完成的文件
   */
  public CommonResult<List<MultipartFileDTO>> getUploadedFile();

  /**
   * 分片下载文件
   */
  void downloadByName(MultipartFileDTO multipartFileDTO, HttpServletRequest request, HttpServletResponse response);
}
