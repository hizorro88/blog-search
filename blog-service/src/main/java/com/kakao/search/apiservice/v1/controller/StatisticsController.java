package com.kakao.search.apiservice.v1.controller;

import com.kakao.search.apiservice.domains.statistics.rank.SearchRankResponse;
import com.kakao.search.apiservice.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"통계 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Validated
public class StatisticsController {

  @Autowired
  private StatisticsService statisticsService;

  @ApiOperation(
      value = "검색어 순위를 보여주는 API",
      notes =
          "검색어 순위를 보여주는 API",
      response = SearchRankResponse.class)
  @ApiResponses({
      @ApiResponse(code = 200, message = "success"),
      @ApiResponse(code = 400, message = "user data is invalid"),
      @ApiResponse(code = 500, message = "server side error")
  })
  @GetMapping(value = "/rank", produces = MediaType.APPLICATION_JSON_VALUE)
  public SearchRankResponse getRank() throws IOException {
    return statisticsService.getRank();
  }

}
