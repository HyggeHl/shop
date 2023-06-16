package com.hygge.shop.common.service;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface CaptchaService {

  public ModelAndView defaultKaptcha(HttpServletRequest request, HttpServletResponse response, String type, Map<String, String> codeMap);
}
