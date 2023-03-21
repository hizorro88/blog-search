package com.kakao.search.apiservice.domains.blog.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Document {

  private String title;
  private String contents;
  private String url;
  private String blogname;
  private String thumbnail;
  private Date datetime;
}
