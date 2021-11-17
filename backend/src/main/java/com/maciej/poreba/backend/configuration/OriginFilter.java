package com.maciej.poreba.backend.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class OriginFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String origin = request.getHeader(HttpHeaders.ORIGIN);
    response.setHeader("Access-Control-Allow-Origin", origin);
    response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
    response.setHeader("Access-Control-Allow-Headers", "*");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Max-Age", "180");

    if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
      String requestedAllowHeaders = request.getHeader("Access-Control-Request-Headers");
      response.setHeader("Access-Control-Allow-Headers", requestedAllowHeaders);
      response.setStatus(200);
    }

    filterChain.doFilter(request, response);
  }
}
