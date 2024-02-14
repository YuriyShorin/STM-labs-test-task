package ru.stm.ticketsservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.stm.ticketsservice.dto.route.RouteResponse;
import ru.stm.ticketsservice.dto.ticket.TicketCreateRequest;
import ru.stm.ticketsservice.dto.ticket.TicketResponse;
import ru.stm.ticketsservice.dto.ticket.TicketUpdateRequest;
import ru.stm.ticketsservice.model.Ticket;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketMapper {

    private final RouteMapper routeMapper;

    public Ticket ticketCreateRequestToTicket(TicketCreateRequest ticketCreateRequest) {
        Ticket ticket = new Ticket();
        ticket.setRouteId(ticketCreateRequest.getRouteId());
        ticket.setDate(ticketCreateRequest.getDate());
        ticket.setSeat(ticketCreateRequest.getSeat());
        ticket.setPrice(ticketCreateRequest.getPrice());
        ticket.setCurrency(ticketCreateRequest.getCurrency());
        ticket.setIsAvailable(ticketCreateRequest.getIsAvailable());

        return ticket;
    }

    public Ticket ticketUpdateRequestToTicket(TicketUpdateRequest ticketUpdateRequest) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketUpdateRequest.getId());
        ticket.setRouteId(ticketUpdateRequest.getRouteId());
        ticket.setDate(ticketUpdateRequest.getDate());
        ticket.setSeat(ticketUpdateRequest.getSeat());
        ticket.setPrice(ticketUpdateRequest.getPrice());
        ticket.setCurrency(ticketUpdateRequest.getCurrency());
        ticket.setIsAvailable(ticketUpdateRequest.getIsAvailable());

        return ticket;
    }

    public TicketResponse ticketToTicketResponse(Ticket ticket) {
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setId(ticket.getId());
        ticketResponse.setDate(ticket.getDate());
        ticketResponse.setSeat(ticket.getSeat());
        ticketResponse.setPrice(ticket.getPrice());
        ticketResponse.setCurrency(ticket.getCurrency());
        ticketResponse.setIsAvailable(ticket.getIsAvailable());

        RouteResponse routeResponse = routeMapper.roureToRouteResponse(ticket.getRoute());
        ticketResponse.setRoute(routeResponse);

        return ticketResponse;
    }

    public List<TicketResponse> ticketsToTicketsResponses(List<Ticket> tickets) {
        List<TicketResponse> ticketResponses = new ArrayList<>();
        for (Ticket ticket : tickets) {
            ticketResponses.add(ticketToTicketResponse(ticket));
        }

        return ticketResponses;
    }
}
