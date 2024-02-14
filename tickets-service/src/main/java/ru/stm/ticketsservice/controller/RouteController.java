package ru.stm.ticketsservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.stm.ticketsservice.dto.*;
import ru.stm.ticketsservice.dto.route.RouteCreateRequest;
import ru.stm.ticketsservice.dto.route.RouteResponse;
import ru.stm.ticketsservice.dto.route.RouteUpdateRequest;
import ru.stm.ticketsservice.service.RouteService;

import java.util.UUID;

@Tag(name = "Routes controller", description = "Routes API")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/v1/routes")
public class RouteController {

    private final RouteService routeService;

    @Operation(summary = "Create route")
    @ApiResponse(
            responseCode = "201",
            description = "Route created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = IdResponse.class))})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdResponse createRoute(@RequestBody @Valid RouteCreateRequest routeCreateRequest) {
        return routeService.createRoute(routeCreateRequest);
    }

    @Operation(summary = "Get route by id")
    @ApiResponse(
            responseCode = "200",
            description = "Route found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RouteResponse.class))})
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public RouteResponse getRouteById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Route Id",
                    required = true,
                    schema = @Schema(example = "86ae734e-87d6-44f1-8e7d-991e308b3121"))
            @PathVariable UUID uuid) {
        return routeService.getRouteById(uuid);
    }

    @Operation(summary = "Update route")
    @ApiResponse(
            responseCode = "200",
            description = "Route updated")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateRoute(@RequestBody @Valid RouteUpdateRequest routeUpdateRequest) {
        routeService.updateRoute(routeUpdateRequest);
    }

    @Operation(summary = "Delete route by id")
    @ApiResponse(
            responseCode = "200",
            description = "Route deleted")
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRouteById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Route Id",
                    required = true,
                    schema = @Schema(example = "86ae734e-87d6-44f1-8e7d-991e308b3121"))
            @PathVariable UUID uuid) {
        routeService.deleteRouteById(uuid);
    }
}
