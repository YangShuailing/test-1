package upload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Author: Johnny
 * Date: 2016/12/14
 * Time: 20:58
 * api doc -- springfox swagger configuration
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    //http://localhost:8080/swagger-ui.html
    private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("upload.controller"))
                //.paths(regex("/user/.*"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("demo of swagger for RESTful APIs")
                .description("this is demo swagger-ui page")
                .version("1.1")
                .build();
    }
}
