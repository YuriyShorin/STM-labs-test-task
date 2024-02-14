package ru.stm.ticketsservice.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Schema(description = "Dto for ticket creation request")
@Data
@NoArgsConstructor
public class TicketCreateRequest {

    @Schema(description = "Route id", example = "86ae734e-87d6-44f1-8e7d-991e308b3121")
    @NotNull(message = "Route id cannot be null")
    private UUID routeId;

    @Schema(description = "Date and time of departure", example = "2024-05-02 22:05:00")
    @NotNull(message = "Date and time of departure cannot be null")
    private Timestamp date;

    @Schema(description = "Seat", example = "28A")
    @NotNull(message = "Seat cannot be null")
    private String seat;

    @Schema(description = "Price of the ticket", example = "17.35")
    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Must be non-negative")
    private Double price;

    @Schema(description = "Currency", example = "EUR")
    @NotNull(message = "Currency cannot be null")
    private String currency;

    @Schema(description = "Is that ticket available?", example = "true", defaultValue = "true")
    private Boolean isAvailable;
}
