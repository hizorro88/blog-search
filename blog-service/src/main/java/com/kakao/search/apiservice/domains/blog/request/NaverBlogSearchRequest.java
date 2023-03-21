package com.kakao.search.apiservice.domains.blog.request;

import lombok.Data;

@Data
public class NaverBlogSearchRequest {

  final private String query;
  final private String sort; //sim, date
  final private Integer start;
  final private Integer display;
}
