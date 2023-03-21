package com.kakao.search.apiservice.service;

import com.kakao.search.apiservice.domains.statistics.rank.SearchRankResponse;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public interface StatisticsService {

  SearchRankResponse getRank() throws IOException;

}
