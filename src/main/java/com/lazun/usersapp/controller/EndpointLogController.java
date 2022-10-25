package com.lazun.usersapp.controller;

import com.lazun.usersapp.models.dto.EndpointLogDTO;
import com.lazun.usersapp.service.EndpointLoggerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("log/endpoints")
public class EndpointLogController {

  private static final Logger logger = LogManager.getLogger(EndpointLogController.class);

  @Autowired private EndpointLoggerService service;

  @GetMapping("")
  public ResponseEntity<Page<EndpointLogDTO>> get(
      @PageableDefault(page = 0, size = 10, sort = "createdDate", direction = Sort.Direction.DESC)
          Pageable pageable) {
    Page<EndpointLogDTO> leadSMSDTOS = service.getEndpointsLog(pageable);
    return logger.traceExit(ResponseEntity.status(HttpStatus.OK).body(leadSMSDTOS));
  }
}
