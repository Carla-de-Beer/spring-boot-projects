package com.cadebe.jsonprocessing.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BaseTest {

    TulipDto getDto() {
        return TulipDto.builder()
                .id(UUID.randomUUID())
                .commonName("Tulip Name")
                .binomial("Ale")
                .createdDate(OffsetDateTime.now())
                .lastUpdatedDate(OffsetDateTime.now())
                .upc(1234567890L)
                .price(new BigDecimal("12.99"))
                .myLocalDate(LocalDate.now())
                .build();
    }
}
