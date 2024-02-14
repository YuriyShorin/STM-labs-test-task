package ru.stm.kafkaconsumer.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stm.kafkaconsumer.model.TicketPayload;
import ru.stm.kafkaconsumer.repository.TicketsRepository;

@Service
@RequiredArgsConstructor
public class TicketsService {

    private final TicketsRepository ticketsRepository;

    public void createUserTicketRelation(TicketPayload ticketPayload) {
        ticketsRepository.createUserTicketRelation(ticketPayload);
    }
}
