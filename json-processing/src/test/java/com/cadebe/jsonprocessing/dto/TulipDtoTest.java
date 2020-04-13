package com.cadebe.jsonprocessing.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
        // gives a slice => the json environment to work with
class TulipDtoTest extends BaseTest {

    // Use the ObjectMapper configured by Spring Boot
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testSerializeJson() throws JsonProcessingException {
        TulipDto tulipDto = getDto();

        String jsonString = objectMapper.writeValueAsString(tulipDto);

        System.out.println(jsonString);
    }
}


