package ru.stm.ticketsservice.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Schema(description = "Dto for ticket update")
@Data
public class TicketUpdateRequest {

    @Schema(description = "Ticket id", example = "86ae734e-87d6-44f1-8e7d-991e308b3121")
    private UUID id;

    @Schema(description = "Route id", example = "86ae734e-87d6-44f1-8e7d-991e308b3121")
    private UUID routeId;

    @Schema(description = "Date and time of departure", example = "2024-05-02 22:05:00")
    private Timestamp date;

    @Schema(description = "Seat", example = "28A")
    private String seat;

    @Schema(description = "Price of the ticket", example = "17.35")
    @Min(value = 0, message = "Must be non-negative")
    private Double price;

    @Schema(description = "Currency", example = "EUR")
    private String currency;

    @Schema(description = "Is that ticket available?", example = "true")
    private Boolean isAvailable;
}
