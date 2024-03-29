package ru.stm.ticketsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Ticket already bought")
public class TicketAlreadyBoughtException extends RuntimeException {

    public TicketAlreadyBoughtException() {
        super();
    }
}
