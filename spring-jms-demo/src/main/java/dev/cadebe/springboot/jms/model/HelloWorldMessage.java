package dev.cadebe.springboot.jms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelloWorldMessage implements Serializable {

    static final long serialVersionUID = 5507758012092957186L;

    private UUID id;
    private String message;
}
