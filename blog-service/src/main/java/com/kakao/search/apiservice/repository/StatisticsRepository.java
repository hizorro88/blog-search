package com.kakao.search.apiservice.repository;

import com.kakao.search.apiservice.domains.Statistics;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<Statistics, String> {

  Optional<Statistics> findStatisticsByKeyword(String keyword);

  List<Statistics> findTop10ByOrderByCountDesc();

}
