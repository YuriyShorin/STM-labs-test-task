package ru.stm.kafkaconsumer.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import ru.stm.kafkaconsumer.model.TicketPayload;

@Mapper
public interface TicketsRepository {

    @Insert("INSERT INTO Users_Tickets (user_id, ticket_id)" +
            "VALUES('${userId}', '${ticketId}');")
    void createUserTicketRelation(TicketPayload ticketPayload);
}
