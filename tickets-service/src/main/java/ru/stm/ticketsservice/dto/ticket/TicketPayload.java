package ru.stm.ticketsservice.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketPayload {

    private UUID userId;

    private UUID ticketId;
}
