package com.kakao.search.apiservice.domains.statistics.rank;

import com.kakao.search.apiservice.domains.Statistics;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRankResponse {

  private List<Statistics> ranks; //검색어, 카운트 리스트
  private String datetime; //기준시간
}
