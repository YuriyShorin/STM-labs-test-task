package ru.stm.ticketsservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Schema(description = "Dto for id response")
@Data
@AllArgsConstructor
public class IdResponse {

    @Schema(description = "Id")
    private UUID id;
}
