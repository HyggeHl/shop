package com.hygge.shop.security.shopsecurity.component;

import cn.hutool.json.JSONUtil;
import com.hygge.shop.common.api.apiRetuen.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: shop
 * @description: 自定义无权限访问的返回结果
 * @author: hygge
 * @create: 2023/03/29
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest request,
                     HttpServletResponse response,
                     AccessDeniedException e) throws IOException, ServletException {
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Cache-Control","no-cache");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response.getWriter().println(JSONUtil.parse(CommonResult.forbidden(e.getMessage())));
    response.getWriter().flush();
  }
}
