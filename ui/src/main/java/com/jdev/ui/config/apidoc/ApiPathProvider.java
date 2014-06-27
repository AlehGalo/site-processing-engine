package com.jdev.ui.config.apidoc;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;

import com.mangofactory.swagger.core.SwaggerPathProvider;

public class ApiPathProvider implements SwaggerPathProvider {
    private SwaggerPathProvider defaultSwaggerPathProvider;

    @Autowired
    private ServletContext servletContext;

    private final String docsLocation;

    public ApiPathProvider(final String docsLocation) {
        this.docsLocation = docsLocation;
    }

    @Override
    public String getApiResourcePrefix() {
        return defaultSwaggerPathProvider.getApiResourcePrefix();
    }

    @Override
    public String getAppBasePath() {
        return UriComponentsBuilder.fromHttpUrl(docsLocation).path(servletContext.getContextPath())
                .build().toString();
    }

    @Override
    public String getSwaggerDocumentationBasePath() {
        return UriComponentsBuilder.fromHttpUrl(getAppBasePath()).pathSegment("api-docs/").build()
                .toString();
    }

    @Override
    public String getRequestMappingEndpoint(final String requestMappingPattern) {
        return defaultSwaggerPathProvider.getRequestMappingEndpoint(requestMappingPattern);
    }

    public void setDefaultSwaggerPathProvider(final SwaggerPathProvider defaultSwaggerPathProvider) {
        this.defaultSwaggerPathProvider = defaultSwaggerPathProvider;
    }
}