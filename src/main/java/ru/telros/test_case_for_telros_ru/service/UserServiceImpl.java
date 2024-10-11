package ru.telros.test_case_for_telros_ru.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.telros.test_case_for_telros_ru.dto.update.UserShortResponseDto;
import ru.telros.test_case_for_telros_ru.exception.exceptions.ObjectNotFoundException;
import ru.telros.test_case_for_telros_ru.mapper.UserMapper;
import ru.telros.test_case_for_telros_ru.model.User;
import ru.telros.test_case_for_telros_ru.repository.jpa.UserRepository;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserShortResponseDto> getUserCredentialList(PageRequest page) {
        return userRepository.findAll(page).stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public UserShortResponseDto getUserCredentialById(Long userId) {
        return UserMapper.toDto(this.checkUserInDb(userId));
    }

    private User checkUserInDb(Long userId) {

        log.info("User with id: %d was fond in db".formatted(userId)
                + LocalDateTime.now() + "\n");

        return userRepository.findById(userId).orElseThrow(() -> {

            log.info("UserCredential with id: %d was not fond in db".formatted(userId)
                    + LocalDateTime.now() + "\n");

            return new ObjectNotFoundException("User with id: %d was not present in Db".formatted(userId));
        });
    }
}
