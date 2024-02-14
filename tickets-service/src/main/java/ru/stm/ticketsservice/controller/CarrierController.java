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
import ru.stm.ticketsservice.dto.carrier.CarrierCreateRequest;
import ru.stm.ticketsservice.dto.carrier.CarrierResponse;
import ru.stm.ticketsservice.dto.carrier.CarrierUpdateRequest;
import ru.stm.ticketsservice.dto.IdResponse;
import ru.stm.ticketsservice.service.CarrierService;

import java.util.UUID;

@Tag(name = "Carrier controller", description = "Carrier API")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/v1/carriers")
public class CarrierController {

    private final CarrierService carrierService;

    @Operation(summary = "Create carrier")
    @ApiResponse(
            responseCode = "201",
            description = "Carrier created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = IdResponse.class))})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdResponse createCarrier(@RequestBody @Valid CarrierCreateRequest carrierCreateRequest) {
        return carrierService.createCarrier(carrierCreateRequest);
    }

    @Operation(summary = "Get carrier by id")
    @ApiResponse(
            responseCode = "200",
            description = "Carrier found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarrierResponse.class))})
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public CarrierResponse getCarrierById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Carrier Id",
                    required = true,
                    schema = @Schema(example = "86ae734e-87d6-44f1-8e7d-991e308b3121"))
            @PathVariable UUID uuid) {
        return carrierService.getCarrierById(uuid);
    }

    @Operation(summary = "Update carrier")
    @ApiResponse(
            responseCode = "200",
            description = "Carrier updated")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateCarrier(@RequestBody @Valid CarrierUpdateRequest carrierUpdateRequest) {
        carrierService.updateCarrier(carrierUpdateRequest);
    }

    @Operation(summary = "Delete carrier by id")
    @ApiResponse(
            responseCode = "200",
            description = "Carrier deleted")
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCarrierById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Carrier Id",
                    required = true,
                    schema = @Schema(example = "86ae734e-87d6-44f1-8e7d-991e308b3121"))
            @PathVariable UUID uuid) {
        carrierService.deleteCarrierById(uuid);
    }
}
