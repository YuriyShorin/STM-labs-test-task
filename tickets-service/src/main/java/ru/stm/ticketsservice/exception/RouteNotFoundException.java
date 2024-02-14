package ru.stm.ticketsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Route is not found")
public class RouteNotFoundException extends RuntimeException {

    public RouteNotFoundException() {
        super();
    }
}
