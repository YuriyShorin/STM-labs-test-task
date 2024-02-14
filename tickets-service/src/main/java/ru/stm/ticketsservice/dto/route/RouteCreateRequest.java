package ru.stm.ticketsservice.dto.route;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Schema(description = "Dto for route creation request")
@Data
@NoArgsConstructor
public class RouteCreateRequest {

    @Schema(description = "Departure", example = "London")
    @NotNull(message = "Departure cannot be null")
    private String departure;

    @Schema(description = "Arrival", example = "Liverpool")
    @NotNull(message = "Arrival cannot be null")
    private String arrival;

    @Schema(description = "Carrier Id", example = "86ae734e-87d6-44f1-8e7d-991e308b3121")
    @NotNull(message = "Carrier Id cannot be null")
    private UUID carrierId;

    @Schema(description = "Trip time in minutes", example = "180")
    @NotNull(message = "Trip time cannot be null")
    @Min(value = 0, message = "Should be non-negative")
    private Integer tripTimeInMinutes;
}
