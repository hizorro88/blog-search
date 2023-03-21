package com.kakao.search.apiservice.config;

import static com.google.common.collect.Lists.newArrayList;

import java.sql.Timestamp;
import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


  @Bean
  public Docket api() {

    final AuthorizationScope[] authScopes = new AuthorizationScope[1];
    authScopes[0] = new AuthorizationScopeBuilder().scope("global").description("full access")
        .build();
    final SecurityReference securityReference = SecurityReference.builder().reference("apikey")
        .scopes(authScopes).build();

    final ArrayList<SecurityContext> securityContexts = newArrayList(
        SecurityContext.builder().securityReferences(newArrayList(securityReference)).build());

    return new Docket(DocumentationType.OAS_30)
        .groupName("com.kakao.search")
        .host("api.search.kakao.com")
        // 이후에 필요시 변경
        .securitySchemes(newArrayList(new ApiKey("apikey", "Authorization-Key", "header")))
        .securityContexts(securityContexts)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.kakao.search.apiservice"))
        .build()
        .directModelSubstitute(Timestamp.class, Long.class);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("검색 서비스")
        .description("검색&통계 제공")
        .contact(new Contact("JJ", "https://github.com/hizorro88/blog-search.git",
            "hizorro88@naver.com"))
        .license("JJ Licensed")
        .version("0.1")
        .build();
  }

}
