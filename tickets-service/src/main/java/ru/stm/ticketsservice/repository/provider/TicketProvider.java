package ru.stm.ticketsservice.repository.provider;

import org.apache.ibatis.jdbc.SQL;

import java.sql.Timestamp;

public class TicketProvider {

    @SuppressWarnings("unused")
    public static String findAllAvailableTickets(Timestamp date, String departure, String arrival,
                                                 String carrier, Integer offset, Integer size) {
        return new SQL() {{
            SELECT("*");
            FROM("Tickets t");
            JOIN("Routes r on t.route_id = r.id", "Carriers c on r.carrier_id = c.id");
            WHERE("t.is_available = true");
            if (date != null) {
                AND().WHERE("t.date <= #{date}");
            }
            if (departure != null) {
                AND().WHERE("r.departure ILIKE CONCAT(#{departure}, '%')");
            }
            if (arrival != null) {
                AND().WHERE("r.arrival ILIKE CONCAT(#{arrival}, '%')");
            }
            if (carrier != null) {
                AND().WHERE("c.name ILIKE CONCAT(#{carrier}, '%')");
            }
            ORDER_BY("t.date");
            OFFSET(offset).LIMIT(size);
        }}.toString();
    }
}