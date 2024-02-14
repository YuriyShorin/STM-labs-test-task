package ru.stm.ticketsservice.repository;

import org.apache.ibatis.annotations.*;
import ru.stm.ticketsservice.model.Carrier;
import ru.stm.ticketsservice.model.Id;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface CarrierRepository {

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "phone", column = "phone")
    })
    @Select("SELECT * FROM Carriers " +
            "WHERE id = '${id}';")
    Optional<Carrier> findCarrierById(UUID id);

    @Results(value = {
            @Result(property = "id", column = "id")
    })
    @Select("INSERT INTO Carriers (name, phone) " +
            "VALUES (#{name}, #{phone}) " +
            "RETURNING id;")
    Id createCarrier(Carrier carrier);


    @Update("UPDATE Carriers " +
            "SET name = #{name}, phone = #{phone} " +
            "WHERE id = '${id}';")
    void updateCarrier(Carrier carrier);

    @Delete("DELETE FROM Carriers " +
            "WHERE id='${id}';")
    void deleteCarrierById(UUID id);
}
