package com.kakao.search.apiservice.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meta {

  private Integer total_count;
  private Integer pageable_count;
  @JsonProperty("is_end")
  private boolean is_end;
}
