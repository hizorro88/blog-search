package com.kakao.search.apiservice.domains.blog.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaverBlogSearchResponse {

  private String lastBuildDate;
  private Integer total;
  private Integer start;
  private Integer display;

  private List<NaverItem> items;
}
