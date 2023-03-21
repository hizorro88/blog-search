package com.kakao.search.apiservice.domains.blog.response;

import com.kakao.search.apiservice.domains.Meta;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogSearchResponse {

  private Meta meta;
  private List<Document> documents;
}
