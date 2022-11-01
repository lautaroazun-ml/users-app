package com.lazun.usersapp.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.lazun.usersapp.models.dto.EndpointLogDTO;
import com.lazun.usersapp.models.entity.EndpointLogEntity;
import com.lazun.usersapp.repository.EndpointLogRepository;
import com.lazun.usersapp.utils.MapperUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class EndpointLoggerServiceImplTest {

  @SpyBean private EndpointLoggerServiceImpl endpointLoggerService;

  @MockBean private EndpointLogRepository repository;

  @SpyBean private MapperUtil mapperUtil;

  @SpyBean private ModelMapper modelMapper;

  @Test
  public void testSignUP_whenUserIsOK_shouldReturnNothing() {
    // Given
    EndpointLogDTO log = new EndpointLogDTO();
    log.setRequestBody("");
    log.setRequestURL("/user?name=first");
    log.setResponseStatus(HttpStatus.OK.value());
    log.setResponseBody("true");

    // When
    given(repository.save(any(EndpointLogEntity.class))).willReturn(new EndpointLogEntity());

    // Then
    assertDoesNotThrow(() -> endpointLoggerService.log(log));
    verify(repository, times(1)).save(any(EndpointLogEntity.class));
  }

  @Test
  public void testGetEndpointsLog_whenExistsSomeRegistersIntoRepo_shouldReturnPages() {
    // Given
    Pageable pageable = PageRequest.of(0, 10, Sort.by("createdDate").descending());
    EndpointLogEntity log = new EndpointLogEntity();
    log.setRequestBody("");
    log.setRequestURL("/user?name=first");
    log.setResponseStatus(HttpStatus.OK.value());
    log.setResponseBody("true");
    log.setCreatedDate(LocalDateTime.now().minusDays(1));

    List<EndpointLogEntity> logEntityList = List.of(log);
    Page<EndpointLogEntity> pagedLogs =
        new PageImpl<>(logEntityList, pageable, logEntityList.size());
    given(repository.findAll(pageable)).willReturn(pagedLogs);
    // When
    Page<EndpointLogDTO> responseDTO = endpointLoggerService.getEndpointsLog(pageable);

    // Then
    Assertions.assertFalse(responseDTO.isEmpty());
    Assertions.assertEquals(responseDTO.getTotalElements(), logEntityList.size());
  }
}
