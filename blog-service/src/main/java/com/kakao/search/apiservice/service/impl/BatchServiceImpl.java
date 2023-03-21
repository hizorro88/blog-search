package com.kakao.search.apiservice.service.impl;

import static com.kakao.search.apiservice.constants.ConstInfo.KEYWORD_RANK_PERIOD;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.search.apiservice.domains.Statistics;
import com.kakao.search.apiservice.domains.statistics.rank.SearchRankResponse;
import com.kakao.search.apiservice.repository.StatisticsRepository;
import com.kakao.search.apiservice.service.BatchService;
import com.kakao.search.common.util.DateTimeUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BatchServiceImpl implements BatchService {

  @Autowired
  private StatisticsRepository statisticsRepository;

  ObjectMapper mapper = new ObjectMapper();

  @Override
  @Scheduled(fixedDelay = KEYWORD_RANK_PERIOD) // 10초마다 실행
  public void aggregateRank() {
    List<Statistics> statisticsList = statisticsRepository.findTop10ByOrderByCountDesc();

    SearchRankResponse searchRank = new SearchRankResponse(statisticsList,
        DateTimeUtils.getDateTime(System.currentTimeMillis()));

    try {
      File f = new File(System.getProperty("user.home") + File.separator + "keyword_rank");
      f.createNewFile();

      BufferedWriter bw = new BufferedWriter(new FileWriter(f));
      bw.write(mapper.writeValueAsString(searchRank));
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
