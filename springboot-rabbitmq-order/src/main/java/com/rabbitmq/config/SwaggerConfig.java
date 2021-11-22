package com.rabbitmq.config;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Hafiz
 */
@Profile("!prod")
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "com.rabbitmq.api")
public class SwaggerConfig {

    public static final String authorizationScopeGlobal = "global";
    public static final String authorizationScopeGlobalDesc ="accessEverything";

	@Value("${server.contextPath}")
	private String contextPath;

    /**
     *
     * @return Docket
     */
    @Bean
    public Docket api(ServletContext servletContext) {

        return new Docket(DocumentationType.SWAGGER_2) 
        		.pathProvider(new RelativePathProvider(servletContext) {
					@Override
					public String getApplicationBasePath() {
						return contextPath;
					}
				})
        		.groupName("RabbitMQ Webservice for Order")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rabbitmq.api"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    /**
     *
     * @return ApiInf
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("RabbitMQ Webservice for Order").description("REST API with Springboot, Swagger, RabbitMQ and JPA")
                .termsOfServiceUrl("")
                .contact(new Contact("Developers", "muhammad.alhafiz00@gmail.com", ""))
                .license("Open Source")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .version("1.0.0")
                .build();

    }
}
