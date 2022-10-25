package com.lazun.usersapp.service;

import com.lazun.usersapp.models.dto.EndpointLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EndpointLoggerService {

  void log(EndpointLogDTO endpointLog);

  Page<EndpointLogDTO> getEndpointsLog(Pageable pageable);
}
