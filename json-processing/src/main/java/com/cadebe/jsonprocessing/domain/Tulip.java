package com.cadebe.jsonprocessing.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tulip {

    @Null
    private UUID id;

    @NotBlank
    private String commonName;

    @NotBlank
    private String binomial;

    @Positive
    private Long upc;

    private Timestamp createdDate;

    private Timestamp lastUpdatedDate;
}
