package br.com.control.students.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@PropertySource("classpath:swagger.properties")
public class SwaggerConfig {

	@Value("${swagger.info.title}")
	private String title;

	@Value("${swagger.info.description}")
	private String description;

	@Value("${swagger.info.version}")
	private String version;

	@Value("${swagger.info.termsOfServiceUrl}")
	private String termsOfServiceUrl;

	@Value("${swagger.info.contactName}")
	private String contactName;

	@Value("${swagger.info.contactUrl}")
	private String contactUrl;

	@Value("${swagger.info.contactEmail}")
	private String contactEmail;

	@Value("${swagger.info.license}")
	private String license;

	@Value("${swagger.info.licenseUrl}")
	private String licenseUrl;
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
					.title(title)
					.description(description)
					.version(version)
					.termsOfServiceUrl(termsOfServiceUrl)
					.contact(getContact())
					.license(license)
					.licenseUrl(licenseUrl).build();
	}
	
	private Contact getContact () {
		return new Contact(contactName, contactUrl, contactEmail);
	}
}
