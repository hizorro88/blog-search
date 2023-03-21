package com.kakao.search.apiservice.service.feign;

import com.kakao.search.apiservice.config.NaverBlogSearchFeignConfig;
import com.kakao.search.apiservice.domains.blog.response.NaverBlogSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(
    url = "${feign.url.naver-search}",
    name = "naver-search",
    configuration = NaverBlogSearchFeignConfig.class)
public interface NaverSearchService {

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/v1/search/blog.json",
      produces = MediaType.APPLICATION_JSON_VALUE)
  NaverBlogSearchResponse blogSearch(
      @RequestParam("query") String query,
      @RequestParam("sort") String sort,
      @RequestParam("start") Integer start,
      @RequestParam("display") Integer display
  );

}