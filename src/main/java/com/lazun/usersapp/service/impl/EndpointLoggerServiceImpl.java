package com.lazun.usersapp.service.impl;

import com.lazun.usersapp.models.dto.EndpointLogDTO;
import com.lazun.usersapp.models.entity.EndpointLogEntity;
import com.lazun.usersapp.repository.EndpointLogRepository;
import com.lazun.usersapp.service.EndpointLoggerService;
import com.lazun.usersapp.utils.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EndpointLoggerServiceImpl implements EndpointLoggerService {

  @Autowired private EndpointLogRepository repository;

  @Autowired private ModelMapper modelMapper;

  @Autowired private MapperUtil mapperUtil;

  @Override
  @Async
  public void log(EndpointLogDTO endpointLog) {
    EndpointLogEntity endpointLogEntity = modelMapper.map(endpointLog, EndpointLogEntity.class);
    repository.save(endpointLogEntity);
  }

  public Page<EndpointLogDTO> getEndpointsLog(Pageable pageable) {
    Page<EndpointLogEntity> leadSMSEntities = repository.findAll(pageable);
    return mapperUtil.mapEntityPageIntoDtoPage(leadSMSEntities, EndpointLogDTO.class);
  }
}
