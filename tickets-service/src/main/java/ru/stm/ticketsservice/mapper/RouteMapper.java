package ru.stm.ticketsservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.stm.ticketsservice.dto.carrier.CarrierResponse;
import ru.stm.ticketsservice.dto.route.RouteCreateRequest;
import ru.stm.ticketsservice.dto.route.RouteResponse;
import ru.stm.ticketsservice.dto.route.RouteUpdateRequest;
import ru.stm.ticketsservice.model.Route;

@Component
@RequiredArgsConstructor
public class RouteMapper {

    private final CarrierMapper carrierMapper;

    public Route routeCreateRequestToRoute(RouteCreateRequest routeCreateRequest) {
        Route route = new Route();
        route.setDeparture(routeCreateRequest.getDeparture());
        route.setArrival(routeCreateRequest.getArrival());
        route.setCarrierId(routeCreateRequest.getCarrierId());
        route.setTripTimeInMinutes(routeCreateRequest.getTripTimeInMinutes());

        return route;
    }

    public Route routeUpdateRequestToRoute(RouteUpdateRequest routeUpdateRequest) {
        Route route = new Route();
        route.setId(routeUpdateRequest.getId());
        route.setDeparture(routeUpdateRequest.getDeparture());
        route.setArrival(routeUpdateRequest.getArrival());
        route.setCarrierId(routeUpdateRequest.getCarrierId());
        route.setTripTimeInMinutes(routeUpdateRequest.getTripTimeInMinutes());

        return route;
    }

    public RouteResponse roureToRouteResponse(Route route) {
        RouteResponse routeResponse = new RouteResponse();
        routeResponse.setId(route.getId());
        routeResponse.setDeparture(route.getDeparture());
        routeResponse.setArrival(route.getArrival());
        routeResponse.setTripTimeInMinutes(route.getTripTimeInMinutes());

        CarrierResponse carrierResponse = carrierMapper.carrierToCarrierResponse(route.getCarrier());
        routeResponse.setCarrier(carrierResponse);

        return routeResponse;
    }
}
