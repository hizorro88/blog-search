package com.kakao.search.apiservice.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.kakao.search.apiservice.constants.ConstType.SortType;
import com.kakao.search.apiservice.domains.Meta;
import com.kakao.search.apiservice.domains.blog.response.BlogSearchResponse;
import com.kakao.search.apiservice.domains.blog.response.Document;
import com.kakao.search.apiservice.domains.blog.response.NaverBlogSearchResponse;
import com.kakao.search.apiservice.domains.blog.response.NaverItem;
import com.kakao.search.apiservice.service.feign.KakaoSearchService;
import com.kakao.search.apiservice.service.feign.NaverSearchService;
import com.kakao.search.common.util.DateTimeUtils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
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

  /**
   * 검색 테스트 3. convert
   */
  @Test
  void testSearch3() throws ParseException {

    SortType sortType = SortType.valueOf("accuracy");
    NaverBlogSearchResponse naverBlogSearchResponse = naverSearchService.blogSearch("jj",
        sortType.toString(), 1, 10);

    assertNotNull(naverBlogSearchResponse);

    // response convert
    BlogSearchResponse blogSearchResponse = resConvert(naverBlogSearchResponse);

    assertNotNull(blogSearchResponse.getMeta().getTotal_count());
    assertFalse(blogSearchResponse.getMeta().is_end());
    assertSame(blogSearchResponse.getDocuments().size(), 10);

    assertTrue(naverBlogSearchResponse.getTotal() > 0);
  }

  private BlogSearchResponse resConvert(NaverBlogSearchResponse naverBlogSearchResponse)
      throws ParseException {

    BlogSearchResponse blogSearchResponse = new BlogSearchResponse();

    if (naverBlogSearchResponse.getTotal() != null) {
      Meta meta = new Meta();
      meta.setTotal_count(naverBlogSearchResponse.getTotal());

      if (naverBlogSearchResponse.getStart() != null
          && naverBlogSearchResponse.getDisplay() != null) {
        meta.set_end(naverBlogSearchResponse.getTotal() < (naverBlogSearchResponse.getStart()
            * naverBlogSearchResponse.getDisplay()));
      }

      blogSearchResponse.setMeta(meta);
    }
    if (CollectionUtils.isNotEmpty(naverBlogSearchResponse.getItems())) {
      List<Document> documents = new ArrayList<>();

      for (NaverItem item : naverBlogSearchResponse.getItems()) {
        Document document = new Document();
        if (item.getBloggername() != null) {
          document.setBlogname(item.getBloggername());
        }
        if (item.getLink() != null) {
          document.setUrl(item.getLink());
        }
        if (item.getDescription() != null) {
          document.setContents(item.getDescription());
        }
        if (item.getTitle() != null) {
          document.setTitle(item.getTitle());
        }
        if (item.getPostdate() != null) {
          Date date = DateTimeUtils.parseDate(item.getPostdate());
          document.setDatetime(date);
        }

        documents.add(document);
      }

      blogSearchResponse.setDocuments(documents);
    }

    return blogSearchResponse;
  }
}
