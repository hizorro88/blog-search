package com.kakao.search.apiservice.service.impl;

import com.kakao.search.apiservice.constants.ConstType.SortType;
import com.kakao.search.apiservice.domains.Meta;
import com.kakao.search.apiservice.domains.Statistics;
import com.kakao.search.apiservice.domains.blog.response.BlogSearchResponse;
import com.kakao.search.apiservice.domains.blog.response.Document;
import com.kakao.search.apiservice.domains.blog.response.NaverBlogSearchResponse;
import com.kakao.search.apiservice.domains.blog.response.NaverItem;
import com.kakao.search.apiservice.error.exception.BlogServiceException;
import com.kakao.search.apiservice.error.exception.BlogServiceExceptionEnum;
import com.kakao.search.apiservice.repository.StatisticsRepository;
import com.kakao.search.apiservice.service.BlogSearchService;
import com.kakao.search.apiservice.service.feign.KakaoSearchService;
import com.kakao.search.apiservice.service.feign.NaverSearchService;
import com.kakao.search.common.util.DateTimeUtils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Transactional
public class BlogSearchServiceImpl implements BlogSearchService {

  @Autowired
  private KakaoSearchService kakaoSearchService;

  @Autowired
  private NaverSearchService naverSearchService;

  @Autowired
  private StatisticsRepository statisticsRepository;

  private final ReentrantLock lock = new ReentrantLock();

  @Override
  public BlogSearchResponse blogSearch(String query, String sort, Integer page, Integer size)
      throws ParseException {

    BlogSearchResponse blogSearchResponse = kakaoSearchService.blogSearch(query, sort, page, size);

    if (blogSearchResponse == null) {
      // request convert
      SortType sortType = SortType.valueOf(sort);
      NaverBlogSearchResponse naverBlogSearchResponse = naverSearchService.blogSearch(query,
          sortType.toString(), page, size);

      if (naverBlogSearchResponse != null) {
        // response convert
        blogSearchResponse = resConvert(naverBlogSearchResponse);
      }
    }

    if (blogSearchResponse != null && blogSearchResponse.getMeta().getTotal_count() > 0) {
      lock.lock();

      Optional<Statistics> optionalStatistics = statisticsRepository.findStatisticsByKeyword(query);
      Statistics statistics;
      if (optionalStatistics.isPresent()) {
        statistics = optionalStatistics.get();
        statistics.setCount(statistics.getCount() + 1);
      } else {
        statistics = new Statistics(query, 1);
      }
      statisticsRepository.save(statistics);

      lock.unlock();
    }

    if (blogSearchResponse == null) {
      // 둘 다 이상이 있는 경우, 서버 에러
      throw new BlogServiceException(BlogServiceExceptionEnum.알수없는통신오류_500);
    }

    return blogSearchResponse;
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
