package ru.stm.ticketsservice.repository;

import org.apache.ibatis.annotations.*;
import ru.stm.ticketsservice.model.User;

import java.util.Optional;

@Mapper
public interface UserRepository {

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "role", column = "role"),
    })
    @Select("SELECT * FROM Users " +
            "WHERE email = #{email};")
    Optional<User> findUserByEmail(String email);

    @Insert("INSERT INTO Users(email, password, nickname, role) " +
            "VALUES (#{email}, #{password}, #{nickname}, #{role});")
    void createUser(User user);

    @Update("UPDATE Users " +
            "SET refresh_token = #{token} " +
            "WHERE email = #{email};")
    void updateRefreshToken(String email, String token);

    @Select("SELECT refresh_token FROM Users " +
            "WHERE email = #{email};")
    String findRefreshTokenByEmail(String email);
}
