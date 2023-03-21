package com.kakao.search.apiservice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.kakao.search.apiservice.domains.Statistics;
import com.kakao.search.apiservice.repository.StatisticsRepository;
import com.kakao.search.apiservice.service.StatisticsService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StatisticsTest {

  @Autowired
  private StatisticsRepository statisticsRepository;

  @Autowired
  private StatisticsService statisticsService;

  /**
   * 저장 & keyword 조회
   */
  @Test
  void testFindKeyword() {
    Statistics statistics = new Statistics("jj", 20);
    statisticsRepository.save(statistics);

    Statistics saved = statisticsRepository.findStatisticsByKeyword("jj").get();
    assertNotNull(saved);

    assertEquals(statistics.getKeyword(), saved.getKeyword());
    assertSame(statistics.getCount(), saved.getCount());
  }

  /**
   * 저장 & rank 조회
   */
  @Test
  void testGetRank() {
    Statistics statistics = new Statistics("jj", 20);
    statisticsRepository.save(statistics);

    statistics = new Statistics("j1", 1000);
    statisticsRepository.save(statistics);

    statistics = new Statistics("j2", 2);
    statisticsRepository.save(statistics);

    statistics = new Statistics("j3", 20);
    statisticsRepository.save(statistics);

    statistics = new Statistics("j4", 20);
    statisticsRepository.save(statistics);

    statistics = new Statistics("kek", 11);
    statisticsRepository.save(statistics);

    statistics = new Statistics("j9", 1);
    statisticsRepository.save(statistics);

    statistics = new Statistics("j111", 20);
    statisticsRepository.save(statistics);

    List<Statistics> statisticsList = statisticsRepository.findTop10ByOrderByCountDesc();
    assertNotNull(statisticsList);

    assertEquals(statisticsList.size(), 8);
    assertEquals(statisticsList.get(0).getKeyword(), "j1");
    assertEquals(statisticsList.get(0).getCount(), 1000);
    assertEquals(statisticsList.get(7).getKeyword(), "j9");
    assertEquals(statisticsList.get(7).getCount(), 1);
  }
}
