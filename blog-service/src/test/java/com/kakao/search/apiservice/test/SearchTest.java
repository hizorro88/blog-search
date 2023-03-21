package com.kakao.search.apiservice.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.kakao.search.apiservice.constants.ConstType.SortType;
import com.kakao.search.apiservice.domains.blog.response.BlogSearchResponse;
import com.kakao.search.apiservice.domains.blog.response.NaverBlogSearchResponse;
import com.kakao.search.apiservice.service.feign.KakaoSearchService;
import com.kakao.search.apiservice.service.feign.NaverSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SearchTest {

  @Autowired
  private KakaoSearchService kakaoSearchService;

  @Autowired
  private NaverSearchService naverSearchService;

  /**
   * 검색 테스트 1
   */
  @Test
  void testSearch1() {

    BlogSearchResponse blogSearchResponse = kakaoSearchService.blogSearch("jj", "accuracy", 1, 10);

    assertNotNull(blogSearchResponse);

    assertTrue(blogSearchResponse.getMeta().getTotal_count() > 0);
  }

  /**
   * 검색 테스트 2
   */
  @Test
  void testSearch2() {
    SortType sortType = SortType.valueOf("accuracy");
    NaverBlogSearchResponse naverBlogSearchResponse = naverSearchService.blogSearch("jj",
        sortType.toString(), 1, 10);

    assertNotNull(naverBlogSearchResponse);

    assertTrue(naverBlogSearchResponse.getTotal() > 0);
  }
}
