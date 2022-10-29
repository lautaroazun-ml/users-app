package com.lazun.usersapp.config;

import com.lazun.usersapp.models.dto.EndpointLogDTO;
import com.lazun.usersapp.service.EndpointLoggerService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@RequiredArgsConstructor
public class RequestFilter implements Filter {
  private static final Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);

  private final EndpointLoggerService endpointLogger;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    LOGGER.info("doFilter, parsing request");
    // LOG REQUEST
    ContentCachingRequestWrapper wrappedRequest =
        new ContentCachingRequestWrapper((HttpServletRequest) request);
    ContentCachingResponseWrapper wrappedResponse =
        new ContentCachingResponseWrapper((HttpServletResponse) response);

    // Execution request chain
    chain.doFilter(wrappedRequest, wrappedResponse);

    logCall(wrappedRequest, wrappedResponse);
    wrappedResponse.copyBodyToResponse();
  }

  @Async
  void logCall(
      ContentCachingRequestWrapper wrappedRequest, ContentCachingResponseWrapper wrappedResponse) {
    // Get Cache
    String requestURL = getFullURL(wrappedRequest);

    byte[] reqBody = wrappedRequest.getContentAsByteArray();
    String requestBody = new String(reqBody, StandardCharsets.UTF_8);

    byte[] respBody = wrappedResponse.getContentAsByteArray();
    String responseBody = "";
    if (wrappedResponse.getStatus() < 300) {
      responseBody = new String(respBody, StandardCharsets.UTF_8);
    }
    Integer status = wrappedResponse.getStatus();
    LOGGER.info("request URL = {}", requestURL);
    LOGGER.info("request body = {}", requestBody);
    LOGGER.info("request status = {}", status);
    LOGGER.info("response body = {}", responseBody);

    EndpointLogDTO endpointLogDTO = new EndpointLogDTO();
    endpointLogDTO.setRequestURL(requestURL);
    endpointLogDTO.setRequestBody(requestBody);
    endpointLogDTO.setResponseStatus(status);
    endpointLogDTO.setResponseBody(responseBody);
    endpointLogger.log(endpointLogDTO);
  }

  private static String getFullURL(HttpServletRequest request) {
    StringBuilder requestURI = new StringBuilder(request.getRequestURI());
    String queryString = request.getQueryString();
    if (queryString == null) {
      return requestURI.toString();
    } else {
      return requestURI.append('?').append(queryString).toString();
    }
  }
}
