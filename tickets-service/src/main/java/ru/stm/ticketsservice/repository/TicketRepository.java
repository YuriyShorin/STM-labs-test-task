package ru.stm.ticketsservice.repository;

import org.apache.ibatis.annotations.*;
import ru.stm.ticketsservice.model.Id;
import ru.stm.ticketsservice.model.Route;
import ru.stm.ticketsservice.model.Ticket;
import ru.stm.ticketsservice.repository.provider.TicketProvider;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface TicketRepository {

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "routeId", column = "route_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "seat", column = "seat"),
            @Result(property = "price", column = "price"),
            @Result(property = "currency", column = "currency"),
            @Result(property = "isAvailable", column = "is_available"),
            @Result(property = "route", column = "route_id", javaType = Route.class,
                    one = @One(select = "ru.stm.ticketsservice.repository.RouteRepository.findRouteById"))
    })
    @Select("SELECT * FROM Tickets " +
            "WHERE id = '${id}';")
    Optional<Ticket> findTicketById(UUID id);

    @Results(value = {
            @Result(property = "id", column = "id")
    })
    @Select("INSERT INTO Tickets (route_id, date, seat, price, currency, is_available) " +
            "VALUES ('${routeId}', #{date}, #{seat}, #{price}, #{currency}, #{isAvailable}) " +
            "RETURNING id;")
    Id createTicket(Ticket ticket);

    @Update("UPDATE Tickets " +
            "SET route_id = '${routeId}', date = #{date}, seat = #{seat}, price = #{price}, currency = #{currency}, is_available = #{isAvailable} " +
            "WHERE id = '${id}';")
    void updateTicket(Ticket ticket);

    @Delete("DELETE FROM Tickets " +
            "WHERE id='${id}';")
    void deleteTicketById(UUID id);

    @Insert("INSERT INTO Users_Tickets (user_id, ticket_id)" +
            "VALUES('${userId}', '${ticketId}');")
    void createTicketUserRelation(UUID userId, UUID ticketId);

    @Delete("DELETE FROM Users_Tickets " +
            "WHERE user_id = '${userId}' AND ticket_id = '${ticketId}';")
    void deleteTicketUserRelation(UUID userId, UUID ticketId);


    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "seat", column = "seat"),
            @Result(property = "price", column = "price"),
            @Result(property = "currency", column = "currency"),
            @Result(property = "isAvailable", column = "is_available"),
            @Result(property = "route.id", column = "route_id"),
            @Result(property = "route.departure", column = "departure"),
            @Result(property = "route.arrival", column = "arrival"),
            @Result(property = "route.tripTimeInMinutes", column = "trip_time_in_minutes"),
            @Result(property = "route.carrier.id", column = "carrier_id"),
            @Result(property = "route.carrier.name", column = "name"),
            @Result(property = "route.carrier.phone", column = "phone")
    })
    @Select("""
            SELECT * FROM users_tickets
            JOIN Tickets t on users_tickets.ticket_id = t.id
            JOIN Routes r on t.route_id = r.id
            JOIN Carriers c on r.carrier_id = c.id
            WHERE users_tickets.user_id = '${uuid}';
            """)
    List<Ticket> findTicketsByUserId(UUID userId);

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "seat", column = "seat"),
            @Result(property = "price", column = "price"),
            @Result(property = "currency", column = "currency"),
            @Result(property = "isAvailable", column = "is_available"),
            @Result(property = "route.id", column = "route_id"),
            @Result(property = "route.departure", column = "departure"),
            @Result(property = "route.arrival", column = "arrival"),
            @Result(property = "route.tripTimeInMinutes", column = "trip_time_in_minutes"),
            @Result(property = "route.carrier.id", column = "carrier_id"),
            @Result(property = "route.carrier.name", column = "name"),
            @Result(property = "route.carrier.phone", column = "phone")
    })
    @SelectProvider(type = TicketProvider.class, method = "findAllAvailableTickets")
    List<Ticket> findAllAvailableTickets(Timestamp date, String departure, String arrival,
                                                 String carrier, Integer offset, Integer size);
}