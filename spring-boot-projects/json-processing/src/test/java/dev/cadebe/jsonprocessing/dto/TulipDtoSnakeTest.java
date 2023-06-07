package dev.cadebe.jsonprocessing.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("kebab")
@JsonTest
        // gives a slice => the json environment to work with
class TulipDtoSnakeTest extends BaseTest {

    // Use the ObjectMapper configured by Spring Boot
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testSerializeJson() throws JsonProcessingException {
        TulipDto tulipDto = getDto();

        String jsonString = objectMapper.writeValueAsString(tulipDto);

        System.out.println(jsonString);
    }

    @Test
    void testDeserializeJson() throws JsonProcessingException {
        // Explicitly set tulipId to match the @JsonProperty annotation value
        String json = "{\"common-name\":\"Tulip Name\",\"binomial\":\"Tulipus vulgarus\",\"upc\":1234567890,\"price\":\"12.99\"," +
                "\"created-date\":\"2020-04-10T17:04:12+0200\"," +
                "\"last-updated-date\":\"2020-04-10T17:04:12.5635+02:00\"," +
                "\"my-local-date\":\"20200410\"," +
                "\"tulipId\":\"4430e35b-8b79-4a0c-8599-7b80b55a0374\"}";

        TulipDto dto = objectMapper.readValue(json, TulipDto.class);

        System.out.println(dto);
    }
}

