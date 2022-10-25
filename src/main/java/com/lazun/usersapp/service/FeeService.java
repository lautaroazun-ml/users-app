package com.lazun.usersapp.service;

import com.lazun.usersapp.models.dto.AmountDTO;

public interface FeeService {

  AmountDTO sumWithFee(Integer firstNumber, Integer secNumber, Boolean mockError);
}
