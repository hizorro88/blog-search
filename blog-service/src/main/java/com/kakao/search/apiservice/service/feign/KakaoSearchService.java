package com.kakao.search.apiservice.service.feign;

import com.kakao.search.apiservice.config.KakaoBlogSearchFeignConfig;
import com.kakao.search.apiservice.domains.blog.response.BlogSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(
    url = "${feign.url.kakao-search}",
    name = "kakao-search",
    configuration = KakaoBlogSearchFeignConfig.class)
public interface KakaoSearchService {

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/v2/search/blog",
      produces = MediaType.APPLICATION_JSON_VALUE)
  BlogSearchResponse blogSearch(
      @RequestParam("query") String query,
      @RequestParam("sort") String sort,
      @RequestParam("page") Integer page,
      @RequestParam("size") Integer size
  );

}