package com.StationManager.simulator;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
    public static ObjectMapper getObjectMapper() {
        var mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper;
    }
}