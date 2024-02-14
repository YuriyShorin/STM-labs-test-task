package ru.stm.ticketsservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stm.ticketsservice.dto.*;
import ru.stm.ticketsservice.dto.route.RouteCreateRequest;
import ru.stm.ticketsservice.dto.route.RouteResponse;
import ru.stm.ticketsservice.dto.route.RouteUpdateRequest;
import ru.stm.ticketsservice.exception.CarrierNotFoundException;
import ru.stm.ticketsservice.exception.RouteNotFoundException;
import ru.stm.ticketsservice.mapper.RouteMapper;
import ru.stm.ticketsservice.model.Id;
import ru.stm.ticketsservice.model.Route;
import ru.stm.ticketsservice.repository.CarrierRepository;
import ru.stm.ticketsservice.repository.RouteRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;

    private final CarrierRepository carrierRepository;

    private final RouteMapper routeMapper;

    @Transactional
    public IdResponse createRoute(RouteCreateRequest routeCreateRequest) {
        carrierRepository.findCarrierById(routeCreateRequest.getCarrierId()).orElseThrow(CarrierNotFoundException::new);

        Route route = routeMapper.routeCreateRequestToRoute(routeCreateRequest);
        Id id = routeRepository.createRoute(route);

        return new IdResponse(id.getId());
    }

    @Transactional(readOnly = true)
    public RouteResponse getRouteById(UUID uuid) {
        Route route = routeRepository.findRouteById(uuid).orElseThrow(RouteNotFoundException::new);

        return routeMapper.roureToRouteResponse(route);
    }

    @Transactional
    public void updateRoute(RouteUpdateRequest routeUpdateRequest) {
        carrierRepository.findCarrierById(routeUpdateRequest.getCarrierId()).orElseThrow(CarrierNotFoundException::new);
        routeRepository.findRouteById(routeUpdateRequest.getId()).orElseThrow(RouteNotFoundException::new);
        Route route = routeMapper.routeUpdateRequestToRoute(routeUpdateRequest);
        routeRepository.updateRoute(route);
    }

    @Transactional
    public void deleteRouteById(UUID uuid) {
        routeRepository.findRouteById(uuid).orElseThrow(RouteNotFoundException::new);
        routeRepository.deleteRouteById(uuid);
    }
}
