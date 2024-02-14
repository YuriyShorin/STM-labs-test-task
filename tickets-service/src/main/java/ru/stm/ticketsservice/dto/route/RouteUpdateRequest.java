package ru.stm.ticketsservice.dto.route;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.UUID;

@Schema(description = "Dto for route update request")
@Data
public class RouteUpdateRequest {

    @Schema(description = "Route Id", example = "86ae734e-87d6-44f1-8e7d-991e308b3121")
    private UUID id;

    @Schema(description = "Departure", example = "London")
    private String departure;

    @Schema(description = "Arrival", example = "Liverpool")
    private String arrival;

    @Schema(description = "Carrier Id", example = "86ae734e-87d6-44f1-8e7d-991e308b3121")
    private UUID carrierId;

    @Min(value = 0, message = "Should be non-negative")
    @Schema(description = "Trip time in minutes", example = "180")
    private Integer tripTimeInMinutes;
}
