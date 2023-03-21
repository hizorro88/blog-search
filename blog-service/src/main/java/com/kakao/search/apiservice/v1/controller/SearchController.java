package com.kakao.search.apiservice.v1.controller;

import com.kakao.search.apiservice.constants.ConstType.SortType;
import com.kakao.search.apiservice.domains.blog.response.BlogSearchResponse;
import com.kakao.search.apiservice.error.exception.BlogServiceException;
import com.kakao.search.apiservice.error.exception.BlogServiceExceptionEnum;
import com.kakao.search.apiservice.service.BlogSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"검색 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/search", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Validated
public class SearchController {

  public final static boolean HIDDEN = true;

  @Autowired
  private BlogSearchService blogSearchService;

  @ApiOperation(
      value = "블로그 검색 API",
      notes =
          "카카오 블로그 검색 API 를 사용한 검색",
      response = BlogSearchResponse.class)
  @ApiResponses({
      @ApiResponse(code = 200, message = "success"),
      @ApiResponse(code = 400, message = "user data is invalid"),
      @ApiResponse(code = 500, message = "server side error")
  })
  @GetMapping(value = "/blog", produces = MediaType.APPLICATION_JSON_VALUE)
  public BlogSearchResponse blogSearch(
      @RequestParam(value = "query") String query,
      @RequestParam(value = "sort", required = false, defaultValue = "accuracy") SortType sort,
      @RequestParam(value = "page", required = false, defaultValue = "1") @Valid @Min(value = 1) @Max(value = 50) Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "10") @Valid @Min(value = 1) @Max(value = 80) Integer size) {

    if (StringUtils.isEmpty(query)) {
      throw new BlogServiceException(BlogServiceExceptionEnum.입력오류_400);
    }

    return blogSearchService.blogSearch(query, sort.name(), page, size);
  }

}
