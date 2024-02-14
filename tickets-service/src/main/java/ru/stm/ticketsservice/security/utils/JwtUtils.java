package ru.stm.ticketsservice.security.utils;

import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Configuration;
import ru.stm.ticketsservice.model.Role;
import ru.stm.ticketsservice.security.model.JwtAuthentication;

import java.util.Set;

@Configuration
public class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(Set.of(Role.valueOf(claims.get("role", String.class))));
        jwtInfoToken.setNickname(claims.get("nickname", String.class));
        jwtInfoToken.setUsername(claims.get("id", String.class));

        return jwtInfoToken;
    }
}
