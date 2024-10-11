package ru.telros.test_case_for_telros_ru.service;

import org.springframework.data.domain.PageRequest;
import ru.telros.test_case_for_telros_ru.dto.update.UserResponseCredentialResponseDto;
import ru.telros.test_case_for_telros_ru.dto.update.UserResponseCredentialShortDto;

import java.util.List;

public interface UserCredentialService {

    List<UserResponseCredentialShortDto> getListUserShortCredentials(PageRequest page);

    List<UserResponseCredentialResponseDto> getListUserFullCredentials(PageRequest page);

    UserResponseCredentialResponseDto getUserCredentialById(Long userId);

    UserResponseCredentialResponseDto updateUserCredentialById(Long userId);

    UserResponseCredentialResponseDto deleteUserCredentialsById(Long userId);
}
