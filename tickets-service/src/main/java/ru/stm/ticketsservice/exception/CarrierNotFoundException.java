package ru.stm.ticketsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Carrier is not found")
public class CarrierNotFoundException extends RuntimeException {

    public CarrierNotFoundException() {
        super();
    }
}
