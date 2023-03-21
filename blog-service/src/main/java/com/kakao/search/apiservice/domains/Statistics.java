package com.kakao.search.apiservice.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "STATISTICS")
@AllArgsConstructor
@Log4j2
public class Statistics {

  @Id
  @Column(name = "KEYWORD", nullable = false)
  private String keyword;

  @Column(name = "COUNT", nullable = false)
  private Integer count;

}
