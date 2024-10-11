package ru.telros.test_case_for_telros_ru.security.service;

import ru.telros.test_case_for_telros_ru.dto.create.UserCredentialsNewDto;
import ru.telros.test_case_for_telros_ru.dto.update.UserCredentialsResponseDto;

public interface SecurityService {

    UserCredentialsResponseDto register(UserCredentialsNewDto createUserRequest);
}
