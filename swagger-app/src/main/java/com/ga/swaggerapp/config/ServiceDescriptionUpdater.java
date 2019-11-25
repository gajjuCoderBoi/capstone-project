package com.ga.swaggerapp.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ServiceDescriptionUpdater {

    private static final Logger logger = LoggerFactory.getLogger(ServiceDescriptionUpdater.class);

    private static final String DEFAULT_SWAGGER_URL = "/v2/api-docs";

    private static final List<String> skipServiceList = Arrays.asList("api-gateway", "swagger-app");

    @Autowired
    private DiscoveryClient discoveryClient;

    private final RestTemplate template;

    public ServiceDescriptionUpdater() {
        this.template = new RestTemplate();
    }

    @Autowired
    private ServiceDefinitionsContext definitionContext;

    @Scheduled(fixedDelayString = "${swagger.config.refreshrate}")
    public void refreshSwaggerConfigurations() {


        discoveryClient.getServices().stream().forEach(serviceId -> {
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
            if (serviceInstances == null || serviceInstances.isEmpty()) {
                logger.info("No instances available for service : {} ", serviceId);
            } else {
                ServiceInstance instance = serviceInstances.get(0);
                if (skipServiceList.contains(instance.getHost())) {
                    logger.info("Skipping Service id: {}",serviceId);
                } else {
                    String swaggerURL = getSwaggerURL(instance);

                    Optional<Object> jsonData = getSwaggerDefinitionForAPI(serviceId, swaggerURL);

                    if (jsonData.isPresent()) {
                        String content = getJSON(serviceId, jsonData.get());
                        definitionContext.addServiceDefinition(serviceId, content);
                    } else {
                        logger.error("Skipping service id : {} Error : Could not get Swagegr definition from API ", serviceId);
                    }

                    logger.info("Service Definition Context Refreshed at :  {}", LocalDate.now());
                }
            }
        });
    }

    private String getSwaggerURL(ServiceInstance instance) {
        return instance.getUri() + DEFAULT_SWAGGER_URL;
    }

    private Optional<Object> getSwaggerDefinitionForAPI(String serviceName, String url) {
        logger.debug("Accessing the SwaggerDefinition JSON for Service : {} : URL : {} ", serviceName, url);
        try {
            Object jsonData = template.getForObject(url, Object.class);
            return Optional.of(jsonData);
        } catch (RestClientException ex) {
            logger.error("Error while getting service definition for service : {} Error : {} ", serviceName, ex.getMessage());
            return Optional.empty();
        }

    }

    public String getJSON(String serviceId, Object jsonData) {
        try {
            return new ObjectMapper().writeValueAsString(jsonData);
        } catch (JsonProcessingException e) {
            logger.error("Error : {} ", e.getMessage());
            return "";
        }
    }
}
