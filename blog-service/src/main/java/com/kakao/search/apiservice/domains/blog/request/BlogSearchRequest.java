package com.kakao.search.apiservice.domains.blog.request;

import lombok.Data;

@Data
public class BlogSearchRequest {

  final private String query;
  final private String sort; //accuracy(정확도순) 또는 recency(최신순)
  final private Integer page; // 결과 페이지 번호, 1~50 사이의 값, 기본 값 1 =start
  final private Integer size; // =display
}
