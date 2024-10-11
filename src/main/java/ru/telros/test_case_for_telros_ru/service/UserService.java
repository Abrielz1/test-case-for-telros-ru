package ru.telros.test_case_for_telros_ru.service;

import org.springframework.data.domain.PageRequest;
import ru.telros.test_case_for_telros_ru.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getUserCredentialList(PageRequest page);

    UserResponseDto getUserCredentialById(Long userId);

    UserResponseDto updateUserById(Long userId);

    UserResponseDto deleteUsersAccountByUserId(Long userId);
}
