package ru.stm.ticketsservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Ticket {

    private UUID id;

    private UUID routeId;

    private Timestamp date;

    private String seat;

    private Double price;

    private String currency;

    private Boolean isAvailable;

    private Route route;
}
