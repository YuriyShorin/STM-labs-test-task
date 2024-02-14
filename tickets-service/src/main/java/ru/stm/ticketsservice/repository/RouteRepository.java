package ru.stm.ticketsservice.repository;

import org.apache.ibatis.annotations.*;
import ru.stm.ticketsservice.model.Carrier;
import ru.stm.ticketsservice.model.Id;
import ru.stm.ticketsservice.model.Route;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface RouteRepository {

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "departure", column = "departure"),
            @Result(property = "arrival", column = "arrival"),
            @Result(property = "carrierId", column = "carrier_id"),
            @Result(property = "tripTimeInMinutes", column = "trip_time_in_minutes"),
            @Result(property = "carrier", column = "carrier_id", javaType = Carrier.class,
                    one = @One(select = "ru.stm.ticketsservice.repository.CarrierRepository.findCarrierById")),
    })
    @Select("SELECT * FROM Routes " +
            "WHERE id = '${id}';")
    Optional<Route> findRouteById(UUID id);

    @Results(value = {
            @Result(property = "id", column = "id")
    })
    @Select("INSERT INTO Routes (departure, arrival, carrier_id, trip_time_in_minutes) " +
            "VALUES (#{departure}, #{arrival}, '${carrierId}', #{tripTimeInMinutes}) " +
            "RETURNING id;")
    Id createRoute(Route route);

    @Update("UPDATE Routes " +
            "SET departure = #{departure}, arrival = #{arrival}, carrier_id = '${carrierId}',  trip_time_in_minutes = #{tripTimeInMinutes} " +
            "WHERE id = '${id}';")
    void updateRoute(Route route);

    @Delete("DELETE FROM Routes " +
            "WHERE id='${id}';")
    void deleteRouteById(UUID uuid);
}
