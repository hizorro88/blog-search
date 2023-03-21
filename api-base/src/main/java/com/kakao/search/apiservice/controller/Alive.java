package com.kakao.search.apiservice.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(value = "/alive")
@Slf4j
@Validated
public class Alive {

  @ApiOperation(value = "Health check", notes = "컨테이너 상태체크")
  @ApiResponses({
      @ApiResponse(code = 200, message = "success"),
      @ApiResponse(code = 500, message = "server side error")
  })
  @GetMapping
  public String alive() {
    return "ok";
  }
}
