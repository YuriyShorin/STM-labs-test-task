package ru.stm.ticketsservice.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.stm.ticketsservice.exception.UserAlreadyExistsException;
import ru.stm.ticketsservice.exception.UserNotFoundException;
import ru.stm.ticketsservice.exception.WrongPasswordException;
import ru.stm.ticketsservice.exception.WrongRefreshTokenException;
import ru.stm.ticketsservice.model.Role;
import ru.stm.ticketsservice.model.User;
import ru.stm.ticketsservice.repository.UserRepository;
import ru.stm.ticketsservice.security.dto.JwtRequest;
import ru.stm.ticketsservice.security.dto.LoginRequest;
import ru.stm.ticketsservice.security.dto.LoginResponse;
import ru.stm.ticketsservice.security.dto.SignupRequest;
import ru.stm.ticketsservice.security.utils.JwtProvider;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    public void signup(SignupRequest signupRequest) {
        if (userRepository.findUserByEmail(signupRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User user = new User(
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getNickname(),
                Role.BUYER
        );

        userRepository.createUser(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        final User user = userRepository.findUserByEmail(loginRequest.getEmail()).orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }

        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);

        userRepository.updateRefreshToken(user.getEmail(), refreshToken);

        return new LoginResponse(accessToken, refreshToken);
    }

    public LoginResponse getAccessToken(JwtRequest jwtRequest) {
        if (jwtProvider.validateRefreshToken(jwtRequest.getToken())) {
            final Claims claims = jwtProvider.getRefreshClaims(jwtRequest.getToken());
            final String email = claims.getSubject();
            final String saveRefreshToken = userRepository.findRefreshTokenByEmail(email);

            if (saveRefreshToken != null && saveRefreshToken.equals(jwtRequest.getToken())) {
                final User user = userRepository.findUserByEmail(email).orElseThrow(UserAlreadyExistsException::new);
                final String accessToken = jwtProvider.generateAccessToken(user);

                return new LoginResponse(accessToken, null);
            }
        }

        return new LoginResponse(null, null);
    }

    public LoginResponse getRefreshToken(JwtRequest jwtRequest) {
        if (jwtProvider.validateRefreshToken(jwtRequest.getToken())) {
            final Claims claims = jwtProvider.getRefreshClaims(jwtRequest.getToken());
            final String email = claims.getSubject();
            final String saveRefreshToken = userRepository.findRefreshTokenByEmail(email);

            if (saveRefreshToken != null && saveRefreshToken.equals(jwtRequest.getToken())) {
                final User user = userRepository.findUserByEmail(email).orElseThrow(UserAlreadyExistsException::new);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                userRepository.updateRefreshToken(email, newRefreshToken);

                return new LoginResponse(accessToken, newRefreshToken);
            }
        }

        throw new WrongRefreshTokenException();
    }
}
