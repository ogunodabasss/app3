package com.example.app3.m2.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.boot.jackson.JsonMixinModule;
import org.springframework.data.geo.GeoModule;
import org.springframework.data.web.config.SpringDataJacksonConfiguration;

public class JsonUtil {


    public static ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        objectMapper.registerModule(new Hibernate6Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new GeoModule());
        objectMapper.registerModule(new JsonMixinModule());
        objectMapper.registerModule(new SpringDataJacksonConfiguration.PageModule());
        objectMapper.registerModule(new ParameterNamesModule());

        return objectMapper;
    }
}
