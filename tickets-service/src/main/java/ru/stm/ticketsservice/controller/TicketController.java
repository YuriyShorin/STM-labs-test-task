package ru.stm.ticketsservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.stm.ticketsservice.dto.IdResponse;
import ru.stm.ticketsservice.dto.ticket.TicketCreateRequest;
import ru.stm.ticketsservice.dto.ticket.TicketResponse;
import ru.stm.ticketsservice.dto.ticket.TicketUpdateRequest;
import ru.stm.ticketsservice.service.TicketsService;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Tag(name = "Tickets controller", description = "Tickets API")
@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketsService ticketsService;

    @Operation(summary = "Get all available tickets")
    @ApiResponse(
            responseCode = "200",
            description = "Found tickets",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TicketResponse.class)))})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TicketResponse> getAllAvailableTickets(
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "To filter tickets by date",
                    schema = @Schema(example = "2024-05-02 22:05:00"))
            @RequestParam(name = "date", required = false) Timestamp date,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "To filter tickets by departure place",
                    schema = @Schema(example = "London"))
            @RequestParam(name = "departure", required = false) String departure,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "To filter tickets by arrival place",
                    schema = @Schema(example = "Liverpool"))
            @RequestParam(name = "arrival", required = false) String arrival,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "To filter ticket by carrier",
                    schema = @Schema(example = "Lufthansa"))
            @RequestParam(name = "carrier", required = false) String carrier,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "The number of page you want to get",
                    schema = @Schema(type = "integer", minimum = "0"))
            @RequestParam(name = "page") Integer page,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "The number of tickets per page",
                    schema = @Schema(type = "integer", defaultValue = "10"))
            @RequestParam(name = "size", required = false) Integer size) {
        return ticketsService.getAllAvailableTickets(date, departure, arrival, carrier, page, size);
    }

    @Operation(summary = "Buy ticket")
    @ApiResponse(
            responseCode = "200",
            description = "Ticket bought")
    @PatchMapping("/{ticketId}/buy")
    @ResponseStatus(HttpStatus.OK)
    public void buyTicket(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Ticket Id",
                    required = true,
                    schema = @Schema(example = "86ae734e-87d6-44f1-8e7d-991e308b3121"))
            @PathVariable UUID ticketId) {
        ticketsService.buyTicket(ticketId);
    }

    @Operation(summary = "Return ticket")
    @ApiResponse(
            responseCode = "200",
            description = "Ticket returned")
    @PatchMapping("/{ticketId}/return")
    @ResponseStatus(HttpStatus.OK)
    public void returnTicket(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Ticket Id",
                    required = true,
                    schema = @Schema(example = "86ae734e-87d6-44f1-8e7d-991e308b3121"))
            @PathVariable UUID ticketId) {
        ticketsService.returnTicket(ticketId);
    }

    @Operation(summary = "Get all bought tickets")
    @ApiResponse(
            responseCode = "200",
            description = "Found bought tickets",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TicketResponse.class)))})
    @GetMapping("/bought")
    @ResponseStatus(HttpStatus.OK)
    public List<TicketResponse> getAllBoughtTicketsForCurrentUser() {
        return ticketsService.getAllBoughtTicketsForCurrentUser();
    }

    @Operation(summary = "Get ticket by id")
    @ApiResponse(
            responseCode = "200",
            description = "Ticket found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponse.class))})
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public TicketResponse getTicketById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Ticket IdResponse",
                    required = true,
                    schema = @Schema(example = "86ae734e-87d6-44f1-8e7d-991e308b3121"))
            @PathVariable UUID uuid) {
        return ticketsService.getTicketById(uuid);
    }

    @Operation(summary = "Create ticket")
    @ApiResponse(
            responseCode = "201",
            description = "Ticket created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = IdResponse.class))})
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdResponse createTicket(@RequestBody @Valid TicketCreateRequest ticketCreateRequest) {
        return ticketsService.createTicket(ticketCreateRequest);
    }

    @Operation(summary = "Update ticket")
    @ApiResponse(
            responseCode = "200",
            description = "Ticket updated")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateTicket(@RequestBody @Valid TicketUpdateRequest ticketUpdateRequest) {
        ticketsService.updateTicket(ticketUpdateRequest);
    }

    @Operation(summary = "Delete ticket by id")
    @ApiResponse(
            responseCode = "200",
            description = "Ticket deleted")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTicketById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Ticket IdResponse",
                    required = true,
                    schema = @Schema(example = "86ae734e-87d6-44f1-8e7d-991e308b3121"))
            @PathVariable UUID uuid) {
        ticketsService.deleteTicketById(uuid);
    }
}
