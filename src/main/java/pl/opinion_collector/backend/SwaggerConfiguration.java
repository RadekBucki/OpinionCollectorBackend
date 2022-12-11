package pl.opinion_collector.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebMvc
public class SwaggerConfiguration {
    /**
     * Swagger 2 docked initialization
     *
     * @return Docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfoMetaData())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(List.of(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * API info
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfoMetaData() {
        return new ApiInfoBuilder()
                .title("Opinion Collector")
                .description("API Endpoints for OpinionCollector")
                .license("Apache 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }

    /**
     * Api Key
     *
     * @return ApiKey
     */
    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }

    /**
     * Security Context
     *
     * @return SecurityContext
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(
            List.of(
                new SecurityReference(
                    "Bearer",
                    new AuthorizationScope[]{
                        new AuthorizationScope("global", "accessEverything")
                    }
                )
            )
        ).build();
    }
}
