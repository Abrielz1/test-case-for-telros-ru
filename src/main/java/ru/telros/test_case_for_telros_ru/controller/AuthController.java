package ru.telros.test_case_for_telros_ru.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.telros.test_case_for_telros_ru.dto.create.UserCredentialsNewDto;
import ru.telros.test_case_for_telros_ru.dto.update.UserResponseDto;
import ru.telros.test_case_for_telros_ru.exception.exceptions.AlreadyExistsException;
import ru.telros.test_case_for_telros_ru.repository.UserRepository;
import ru.telros.test_case_for_telros_ru.security.service.SecurityService;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    private final SecurityService securityService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto registerUser(@RequestBody UserCredentialsNewDto request) {

        if (Boolean.TRUE.equals(userRepository.existsByUserName(request.userName()))) {
            throw new AlreadyExistsException("Username: %s already taken! at time "
                    .formatted(request.userName())
                    + LocalDateTime.now());

        }

        UserResponseDto userResponseDto = securityService.register(request);


        log.info("%nUser registration in AuthController was successes with username: %s"
                .formatted(request.userName())
                + " at time: " + LocalDateTime.now() + "\n");

        return userResponseDto;
    }
}
