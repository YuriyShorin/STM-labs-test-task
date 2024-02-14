package ru.stm.ticketsservice.dto.carrier;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Schema(description = "Dto for carrier response")
@Data
@NoArgsConstructor
public class CarrierResponse {

    @Schema(description = "Carrier Id")
    private UUID id;

    @Schema(description = "Name of the carrier company", example = "Lufthansa")
    private String name;

    @Schema(description = "Phone of the carrier company", example = "+79105551188")
    private String phone;
}
