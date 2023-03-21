package com.kakao.search.apiservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

@Component
public class OAWorkaround implements WebMvcOpenApiTransformationFilter {

  @Override
  public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
    final OpenAPI openApi = context.getSpecification();
    openApi.setServers(Arrays.asList(
        // 이후에 다른 url 로 swagger 호출 보낼 시 변경
        new Server().url("http://localhost:8080").description("development")
    ));
    return openApi;
  }

  @Override
  public boolean supports(DocumentationType documentationType) {
    return documentationType.equals(DocumentationType.OAS_30);
  }
}