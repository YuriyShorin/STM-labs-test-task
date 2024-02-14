package ru.stm.ticketsservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stm.ticketsservice.dto.IdResponse;
import ru.stm.ticketsservice.dto.ticket.TicketCreateRequest;
import ru.stm.ticketsservice.dto.ticket.TicketPayload;
import ru.stm.ticketsservice.dto.ticket.TicketResponse;
import ru.stm.ticketsservice.dto.ticket.TicketUpdateRequest;
import ru.stm.ticketsservice.exception.RouteNotFoundException;
import ru.stm.ticketsservice.exception.TicketAlreadyBoughtException;
import ru.stm.ticketsservice.exception.TicketNotBoughtException;
import ru.stm.ticketsservice.exception.TicketNotFoundException;
import ru.stm.ticketsservice.mapper.TicketMapper;
import ru.stm.ticketsservice.model.Id;
import ru.stm.ticketsservice.model.Ticket;
import ru.stm.ticketsservice.repository.RouteRepository;
import ru.stm.ticketsservice.repository.TicketRepository;
import ru.stm.ticketsservice.security.model.JwtAuthentication;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class TicketsService {

    private final TicketRepository ticketRepository;

    private final RouteRepository routeRepository;

    private final TicketMapper ticketMapper;

    private final KafkaTemplate<String, TicketPayload> kafkaTemplate;

    @Value(value = "${default.pagination.size}")
    private int DEFAULT_PAGINATION_SIZE;

    @Value(value = "${spring.kafka.topic}")
    private String topic;

    public List<TicketResponse> getAllAvailableTickets(Timestamp date, String departure, String arrival, String carrier, Integer page, Integer size) {
        if (size == null) {
            size = DEFAULT_PAGINATION_SIZE;
        }

        List<Ticket> tickets = ticketRepository.findAllAvailableTickets(date, departure, arrival, carrier, page * size, size);

        return ticketMapper.ticketsToTicketsResponses(tickets);
    }

    @Transactional
    public void buyTicket(UUID ticketId) {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        Ticket ticket = ticketRepository.findTicketById(ticketId).orElseThrow(TicketNotFoundException::new);

        if (!ticket.getIsAvailable()) {
            throw new TicketAlreadyBoughtException();
        }

        ticket.setIsAvailable(false);
        ticketRepository.updateTicket(ticket);
        ticketRepository.createTicketUserRelation(UUID.fromString(authentication.getUsername()), ticketId);

        TicketPayload ticketPayload = new TicketPayload(UUID.fromString(authentication.getUsername()), ticketId);
        kafkaTemplate.send(topic, ticketPayload);
    }

    @Transactional
    public void returnTicket(UUID ticketId) {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        Ticket ticket = ticketRepository.findTicketById(ticketId).orElseThrow(TicketNotFoundException::new);

        if (ticket.getIsAvailable()) {
            throw new TicketNotBoughtException();
        }

        ticket.setIsAvailable(true);
        ticketRepository.updateTicket(ticket);
        ticketRepository.deleteTicketUserRelation(UUID.fromString(authentication.getUsername()), ticketId);
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> getAllBoughtTicketsForCurrentUser() {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        List<Ticket> tickets = ticketRepository.findTicketsByUserId(UUID.fromString(authentication.getUsername()));

        return ticketMapper.ticketsToTicketsResponses(tickets);
    }

    @Transactional(readOnly = true)
    public TicketResponse getTicketById(UUID ticketId) {
        Ticket ticket = ticketRepository.findTicketById(ticketId).orElseThrow(TicketNotFoundException::new);

        return ticketMapper.ticketToTicketResponse(ticket);
    }

    @Transactional
    public IdResponse createTicket(TicketCreateRequest ticketCreateRequest) {
        routeRepository.findRouteById(ticketCreateRequest.getRouteId()).orElseThrow(RouteNotFoundException::new);

        Ticket ticket = ticketMapper.ticketCreateRequestToTicket(ticketCreateRequest);
        Id id = ticketRepository.createTicket(ticket);

        return new IdResponse(id.getId());
    }

    @Transactional
    public void updateTicket(TicketUpdateRequest ticketUpdateRequest) {
        routeRepository.findRouteById(ticketUpdateRequest.getRouteId()).orElseThrow(RouteNotFoundException::new);
        ticketRepository.findTicketById(ticketUpdateRequest.getId()).orElseThrow(TicketNotFoundException::new);
        Ticket ticket = ticketMapper.ticketUpdateRequestToTicket(ticketUpdateRequest);
        ticketRepository.updateTicket(ticket);
    }

    @Transactional
    public void deleteTicketById(UUID ticketId) {
        ticketRepository.findTicketById(ticketId).orElseThrow(TicketNotFoundException::new);
        ticketRepository.deleteTicketById(ticketId);
    }
}