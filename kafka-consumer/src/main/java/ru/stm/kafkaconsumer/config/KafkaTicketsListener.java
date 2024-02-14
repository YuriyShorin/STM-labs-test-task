package ru.stm.kafkaconsumer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.stm.kafkaconsumer.model.TicketPayload;
import ru.stm.kafkaconsumer.service.TicketsService;

@Component
@RequiredArgsConstructor
class KafkaTicketsListener {

    private final TicketsService ticketsService;

    @KafkaListener(topics = "tickets",
            containerFactory = "ticketKafkaListenerContainerFactory")
    void listener(TicketPayload ticketPayload) {
        ticketsService.createUserTicketRelation(ticketPayload);
    }
}
