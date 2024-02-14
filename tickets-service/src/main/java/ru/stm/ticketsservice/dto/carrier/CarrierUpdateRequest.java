package ru.stm.ticketsservice.dto.carrier;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Schema(description = "Dto for carrier update request")
@Data
public class CarrierUpdateRequest {

    @Schema(description = "Carrier Id")
    private UUID id;

    @Schema(description = "Name of the carrier company", example = "Lufthansa")
    private String name;

    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
    @Schema(description = "Phone of the carrier company", example = "+79105551188")
    private String phone;
}
