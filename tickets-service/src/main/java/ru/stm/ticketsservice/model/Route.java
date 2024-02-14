package ru.stm.ticketsservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Route {

    private UUID id;

    private String departure;

    private String arrival;

    private UUID carrierId;

    private Integer tripTimeInMinutes;

    private Carrier carrier;
}
