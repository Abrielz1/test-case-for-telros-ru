package ru.telros.test_case_for_telros_ru.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.telros.test_case_for_telros_ru.dto.update.UserResponseCredentialResponseDto;
import ru.telros.test_case_for_telros_ru.dto.update.UserResponseCredentialShortDto;
import ru.telros.test_case_for_telros_ru.repository.UserCredentialRepository;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCredentialServiceImpl implements UserCredentialService {

    private final UserCredentialRepository userCredentialRepository;

    @Override
    public List<UserResponseCredentialShortDto> getListUserShortCredentials(PageRequest page) {
        return null;
    }

    @Override
    public List<UserResponseCredentialResponseDto> getListUserFullCredentials(PageRequest page) {
        return null;
    }

    @Override
    public UserResponseCredentialResponseDto getUserCredentialById(Long userId) {
        return null;
    }

    @Override
    public UserResponseCredentialResponseDto updateUserCredentialById(Long userId) {
        return null;
    }

    @Override
    public UserResponseCredentialResponseDto deleteUserCredentialsById(Long userId) {
        return null;
    }
}
