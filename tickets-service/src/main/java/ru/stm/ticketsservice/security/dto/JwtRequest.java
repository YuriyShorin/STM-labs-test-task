package ru.stm.ticketsservice.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Jwt token request dto")
@Data
public class JwtRequest {

    @Schema(description = "Jwt token")
    private String token;
}
