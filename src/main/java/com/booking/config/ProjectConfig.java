package com.booking.config;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.servlet.Filter;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.EnumSet;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()));
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

//    @Bean
//    static FilterRegistrationBean<Filter> handlerMappingIntrospectorCacheFilter(HandlerMappingIntrospector hmi) {
//        Filter cacheFilter = hmi.createCacheFilter();
//        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>(cacheFilter);
//        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        registrationBean.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
//        return registrationBean;
//    }
}
