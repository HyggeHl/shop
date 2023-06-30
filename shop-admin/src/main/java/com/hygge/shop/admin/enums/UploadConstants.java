package com.hygge.shop.admin.enums;

public enum UploadConstants {
  FILE_UPLOAD_STATUS("FILE_UPLOAD_STATUS"),
  FILE_MD5_KEY("FILE_MD5");

  private String value;

  UploadConstants(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
