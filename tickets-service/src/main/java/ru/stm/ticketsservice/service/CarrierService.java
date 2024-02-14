package ru.stm.ticketsservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stm.ticketsservice.dto.carrier.CarrierCreateRequest;
import ru.stm.ticketsservice.dto.carrier.CarrierResponse;
import ru.stm.ticketsservice.dto.carrier.CarrierUpdateRequest;
import ru.stm.ticketsservice.dto.IdResponse;
import ru.stm.ticketsservice.exception.CarrierNotFoundException;
import ru.stm.ticketsservice.mapper.CarrierMapper;
import ru.stm.ticketsservice.model.Carrier;
import ru.stm.ticketsservice.model.Id;
import ru.stm.ticketsservice.repository.CarrierRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarrierService {

    private final CarrierRepository carrierRepository;

    private final CarrierMapper carrierMapper;

    @Transactional
    public IdResponse createCarrier(CarrierCreateRequest carrierCreateRequest) {
        Carrier carrier = carrierMapper.carrierCreateRequestToCarrier(carrierCreateRequest);
        Id id = carrierRepository.createCarrier(carrier);

        return new IdResponse(id.getId());
    }

    @Transactional(readOnly = true)
    public CarrierResponse getCarrierById(UUID uuid) {
        Carrier carrier = carrierRepository.findCarrierById(uuid).orElseThrow(CarrierNotFoundException::new);

        return carrierMapper.carrierToCarrierResponse(carrier);
    }

    @Transactional
    public void updateCarrier(CarrierUpdateRequest carrierUpdateRequest) {
        carrierRepository.findCarrierById(carrierUpdateRequest.getId()).orElseThrow(CarrierNotFoundException::new);
        Carrier carrier = carrierMapper.carrierUpdateRequestToCarrier(carrierUpdateRequest);

        carrierRepository.updateCarrier(carrier);
    }

    @Transactional
    public void deleteCarrierById(UUID uuid) {
        carrierRepository.findCarrierById(uuid).orElseThrow(CarrierNotFoundException::new);
        carrierRepository.deleteCarrierById(uuid);
    }
}
