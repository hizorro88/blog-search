package com.kakao.search.apiservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.search.apiservice.domains.statistics.rank.SearchRankResponse;
import com.kakao.search.apiservice.service.BatchService;
import com.kakao.search.apiservice.service.StatisticsService;
import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

  ObjectMapper mapper = new ObjectMapper();

  @Autowired
  private BatchService batchService;

  /**
   * 랭크 조회
   *
   * @return 키워드, 검색횟수의 리스트
   */
  public SearchRankResponse getRank() throws IOException {

    // 파일에서 읽어서 결과를 내려줌
    File f = new File(System.getProperty("user.home") + File.separator + "keyword_rank");

    if (!f.exists()) {
      batchService.aggregateRank();
    }

    return mapper.readValue(f, SearchRankResponse.class);
  }

}
