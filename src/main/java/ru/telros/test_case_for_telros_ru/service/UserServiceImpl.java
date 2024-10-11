package ru.telros.test_case_for_telros_ru.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.telros.test_case_for_telros_ru.dto.update.UserResponseDto;
import ru.telros.test_case_for_telros_ru.repository.UserRepository;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponseDto> getUserCredentialList(PageRequest page) {
        return null;
    }

    @Override
    public UserResponseDto getUserCredentialById(Long userId) {
        return null;
    }

    @Override
    public UserResponseDto updateUserById(Long userId) {
        return null;
    }

    @Override
    public UserResponseDto deleteUsersAccountByUserId(Long userId) {
        return null;
    }
}
