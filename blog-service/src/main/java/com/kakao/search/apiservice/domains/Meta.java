package com.kakao.search.apiservice.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meta {

  private Integer total_count;
  private Integer pageable_count;
  @JsonProperty("is_end")
  private boolean is_end;
}
