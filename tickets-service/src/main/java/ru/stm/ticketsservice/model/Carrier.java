package ru.stm.ticketsservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Carrier {

    private UUID id;

    private String name;

    private String phone;
}
