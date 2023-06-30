package com.hygge.shop.admin.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: shop
 * @description: 分块断点上传
 * @author: hygge
 * @create: 2023/06/26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultipartFileDTO {

  /**
   * 总分片数量
   **/
  private int totalChunks;

  /**
   * 当前块的次序，第一个块是 1，注意不是从 0 开始的。
   **/
  private int chunkNumber;

  /**
   * 分块大小
   */
  private long chunkSize;

  /**
   * 当前分片大小
   **/
  private long currentChunkSize = 0L;

  /**
   * 文件总大小
   */
  private long totalSize;

  /**
   * 文件名
   **/
  private String filename;

  /**
   * 分片对象
   **/
  private MultipartFile file;

  /**
   * 文件生成的MD5
   **/
  private String identifier;

  /**
   * 文件夹上传的时候文件的相对路径属性
   */
  private String relativePath;
}
