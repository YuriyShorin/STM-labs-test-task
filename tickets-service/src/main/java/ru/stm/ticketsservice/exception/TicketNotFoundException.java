package ru.stm.ticketsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Ticket not found")
public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException() {
        super();
    }
}
