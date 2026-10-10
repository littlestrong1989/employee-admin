package com.demo.admin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SCHEME = "bearerAuth";

    @Bean
    public OpenAPI employeeAdminOpenApi() {
        return new OpenAPI()
            .info(new Info()
                .title("Employee Admin API")
                .version("1.0.0")
                .description("企业管理后台示例：JWT 鉴权 + 员工 CRUD + 分页检索"))
            .addSecurityItem(new SecurityRequirement().addList(SCHEME))
            .components(new Components().addSecuritySchemes(SCHEME,
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
    }
}
