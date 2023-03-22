package com.kakao.search.apiservice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.kakao.search.apiservice.domains.Statistics;
import com.kakao.search.apiservice.repository.StatisticsRepository;
import com.kakao.search.apiservice.service.StatisticsService;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StatisticsTest {

  @Autowired
  private StatisticsRepository statisticsRepository;

  @Autowired
  private StatisticsService statisticsService;

  private final ReentrantLock lock = new ReentrantLock();

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


  /**
   * lock & unlock 정상동작하는지 테스트. 하나의 키워드를 100회 호출해서 정상적으로 100이라는 값이 저장되는지 확인
   */
  @Test
  void concurrentCountTest() throws InterruptedException {

    ExecutorService executorService = Executors.newCachedThreadPool();

    for (int i = 0; i < 100; i++) {
      // save 요청
      Runnable task = this::saveKeyword;
      executorService.submit(task);
    }

    executorService.shutdown();

    if (executorService.awaitTermination(10, TimeUnit.SECONDS)) {
      Optional<Statistics> optionalStatistics = statisticsRepository.findStatisticsByKeyword("jj");
      if (optionalStatistics.isPresent()) {
        Statistics statistics = optionalStatistics.get();

        assertEquals(statistics.getCount(), 100);
      }
    }
  }

  private void saveKeyword() {
    lock.lock();

    Optional<Statistics> optionalStatistics = statisticsRepository.findStatisticsByKeyword("jj");
    Statistics statistics;
    if (optionalStatistics.isPresent()) {
      statistics = optionalStatistics.get();
      statistics.setCount(statistics.getCount() + 1);
    } else {
      statistics = new Statistics("jj", 1);
    }
    statisticsRepository.save(statistics);

    lock.unlock();
  }

}
