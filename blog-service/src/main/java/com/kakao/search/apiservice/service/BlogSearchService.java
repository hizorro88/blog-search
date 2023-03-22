package com.kakao.search.apiservice.service;

import com.kakao.search.apiservice.domains.blog.response.BlogSearchResponse;
import java.text.ParseException;
import org.springframework.stereotype.Service;

@Service
public interface BlogSearchService {

  BlogSearchResponse blogSearch(String query, String sort, Integer page, Integer size)
      throws ParseException;

}
