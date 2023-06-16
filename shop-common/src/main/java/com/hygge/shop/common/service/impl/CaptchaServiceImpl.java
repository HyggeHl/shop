package com.hygge.shop.common.service.impl;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.hygge.shop.common.service.CaptchaService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * @program: shop
 * @description: 验证码服务
 * @author: hygge
 * @create: 2023/04/23
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
  @Resource(name = "captchaProducer")
  private Producer captchaProducer;

  @Resource(name = "captchaProducerMath")
  private Producer captchaProducerMath;

  @Override
  public ModelAndView defaultKaptcha(HttpServletRequest request, HttpServletResponse response, String type, Map<String, String> codeMap) {
    ServletOutputStream out = null;
    try
    {
      HttpSession session = request.getSession();
      response.setDateHeader("Expires", 0);
      response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
      response.addHeader("Cache-Control", "post-check=0, pre-check=0");
      response.setHeader("Pragma", "no-cache");
      response.setContentType("image/jpeg");

      String capStr = null;
      String code = null;
      BufferedImage bi = null;
      if ("math".equals(type))
      {
        String capText = captchaProducerMath.createText();
        capStr = capText.substring(0, capText.lastIndexOf("@"));
        code = capText.substring(capText.lastIndexOf("@") + 1);
        bi = captchaProducerMath.createImage(capStr);
      }
      else if ("char".equals(type))
      {
        capStr = code = captchaProducer.createText();
        bi = captchaProducer.createImage(capStr);
      }
      codeMap.put("code", code);
      session.setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
      out = response.getOutputStream();
      ImageIO.write(bi, "jpg", out);
      out.flush();

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        if (out != null)
        {
          out.close();
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return null;
  }
}
