package ru.stm.ticketsservice.mapper;

import org.springframework.stereotype.Component;
import ru.stm.ticketsservice.dto.carrier.CarrierCreateRequest;
import ru.stm.ticketsservice.dto.carrier.CarrierResponse;
import ru.stm.ticketsservice.dto.carrier.CarrierUpdateRequest;
import ru.stm.ticketsservice.model.Carrier;

@Component
public class CarrierMapper {

    public Carrier carrierCreateRequestToCarrier(CarrierCreateRequest carrierCreateRequest) {
        Carrier carrier = new Carrier();
        carrier.setName(carrierCreateRequest.getName());
        carrier.setPhone(carrierCreateRequest.getPhone());

        return carrier;
    }

    public Carrier carrierUpdateRequestToCarrier(CarrierUpdateRequest carrierUpdateRequest) {
        Carrier carrier = new Carrier();
        carrier.setId(carrierUpdateRequest.getId());
        carrier.setName(carrierUpdateRequest.getName());
        carrier.setPhone(carrierUpdateRequest.getPhone());

        return carrier;
    }

    public CarrierResponse carrierToCarrierResponse(Carrier carrier) {
        CarrierResponse carrierResponse = new CarrierResponse();
        carrierResponse.setId(carrier.getId());
        carrierResponse.setName(carrier.getName());
        carrierResponse.setPhone(carrier.getPhone());

        return carrierResponse;
    }
}
