package ru.stm.ticketsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Ticket is not bought")
public class TicketNotBoughtException extends RuntimeException {

    public TicketNotBoughtException() {
        super();
    }
}
