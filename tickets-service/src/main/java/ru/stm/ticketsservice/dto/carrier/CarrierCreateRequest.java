package ru.stm.ticketsservice.dto.carrier;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Dto for carrier creation request")
@Data
@NoArgsConstructor
public class CarrierCreateRequest {

    @Schema(description = "Name of the carrier company", example = "Lufthansa")
    @NotNull(message = "Name of the carrier company cannot be null")
    private String name;

    @Schema(description = "Phone of the carrier company", example = "+79105551188")
    @NotNull(message = "Phone of the carrier company cannot be null")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
    private String phone;
}
