package ru.telros.test_case_for_telros_ru.security.service;

import ru.telros.test_case_for_telros_ru.dto.create.LoginRequest;
import ru.telros.test_case_for_telros_ru.dto.create.RefreshTokenRequestDto;
import ru.telros.test_case_for_telros_ru.dto.create.UserCredentialsNewDto;
import ru.telros.test_case_for_telros_ru.dto.response.AuthResponse;
import ru.telros.test_case_for_telros_ru.dto.response.RefreshTokenResponseDto;
import ru.telros.test_case_for_telros_ru.dto.update.UserCredentialsResponseDto;

public interface SecurityService {

    UserCredentialsResponseDto register(UserCredentialsNewDto createUserRequest);

    AuthResponse authenticationUser(LoginRequest loginRequest);

    void logout();

    RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto request);
}
