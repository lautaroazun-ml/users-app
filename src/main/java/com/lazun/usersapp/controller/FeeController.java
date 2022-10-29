package com.lazun.usersapp.controller;

import com.lazun.usersapp.models.dto.AmountDTO;
import com.lazun.usersapp.service.FeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fee")
@RequiredArgsConstructor
public class FeeController {

  private final FeeService feeService;

  @GetMapping("/{first_number}/{snd_number}")
  public ResponseEntity<AmountDTO> getFee(
      @PathVariable(name = "first_number") Integer firstNumber,
      @PathVariable(name = "snd_number") Integer sndNumber,
      @RequestParam(name = "mock_error", defaultValue = "false") Boolean mockError) {
    AmountDTO amountDTO = feeService.sumWithFee(firstNumber, sndNumber, mockError);
    return ResponseEntity.ok(amountDTO);
  }
}
