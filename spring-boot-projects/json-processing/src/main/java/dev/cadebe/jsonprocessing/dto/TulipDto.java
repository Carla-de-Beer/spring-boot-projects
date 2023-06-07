package dev.cadebe.jsonprocessing.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TulipDto {

    @Null
    @JsonProperty("tulipId")
    private UUID id;

    @NotBlank
    private String commonName;

    @NotBlank
    private String binomial;

    @Positive
    private Long upc;

    @Positive
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape= JsonFormat.Shape.STRING)
    private OffsetDateTime createdDate;

    private OffsetDateTime lastUpdatedDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate myLocalDate;
}
