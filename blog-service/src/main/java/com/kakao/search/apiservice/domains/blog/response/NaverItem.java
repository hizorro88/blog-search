package com.kakao.search.apiservice.domains.blog.response;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaverItem {

  private String title;
  private String description;
  private String link;
  private String bloggername;
  private Date postdate;
}
