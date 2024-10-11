package ru.telros.test_case_for_telros_ru.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.telros.test_case_for_telros_ru.dto.response.UserCredentialsUpdateDto;
import ru.telros.test_case_for_telros_ru.dto.update.UserResponseCredentialResponseDto;
import ru.telros.test_case_for_telros_ru.dto.update.UserResponseCredentialShortDto;
import ru.telros.test_case_for_telros_ru.exception.exceptions.ObjectNotFoundException;
import ru.telros.test_case_for_telros_ru.mapper.UserCredentialsMapper;
import ru.telros.test_case_for_telros_ru.model.User;
import ru.telros.test_case_for_telros_ru.model.UserCredential;
import ru.telros.test_case_for_telros_ru.repository.jpa.UserCredentialRepository;
import ru.telros.test_case_for_telros_ru.repository.jpa.UserRepository;
import ru.telros.test_case_for_telros_ru.security.model.RoleType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCredentialServiceImpl implements UserCredentialService {

    private final UserCredentialRepository userCredentialRepository;

    private final UserRepository userRepository;

    @Override
    public List<UserResponseCredentialShortDto> getListUserShortCredentials(PageRequest page) {

        log.info("\nAll users short accounts dto's list were sent via users service at time: "
                + LocalDateTime.now() + "\n");

        return userCredentialRepository.findAll(page).stream()
                .map(UserCredentialsMapper::toShortDto)
                .toList();
    }

    @Override
    public List<UserResponseCredentialResponseDto> getListUserFullCredentials(PageRequest page) {

        log.info("\nAll users full accounts dto's  list were sent via users service at time: "
                + LocalDateTime.now() + "\n");

        return userCredentialRepository.findAll(page).stream()
                .map(UserCredentialsMapper::toDto)
                .toList();
    }

    @Override
    public UserResponseCredentialResponseDto getUserCredentialById(Long userId) {

        log.info("%nUser account was sent with id: %d via users service at time: ".formatted(userId)
                + LocalDateTime.now() + "\n");

        return UserCredentialsMapper.toDto(this.checkUserCredentialInDb(userId));
    }

    @Override
    public UserResponseCredentialResponseDto updateUserCredentialById(Long userId,
                                                                      UserCredentialsUpdateDto updateDto) {

        UserCredential userCredential = this.checkUserCredentialInDb(userId);
        User user = this.checkUserInDb(updateDto.userId());

        if (updateDto.firstName() != null) {
            userCredential.setFirstName(updateDto.firstName());
        }

        if (updateDto.patronym() != null) {
            userCredential.setPatronym(updateDto.patronym());
        }

        if (updateDto.lastName() != null) {
            userCredential.setLastName(updateDto.lastName());
        }

        if (updateDto.email() != null) {
            userCredential.setEmail(updateDto.email());
        }

        if (updateDto.username() != null) {
            user.setUserName(updateDto.username());
            userCredential.setUser(user);
        }

        if (updateDto.password() != null) {
            user.setPassword(updateDto.password());
            userCredential.setUser(user);
        }

        if (updateDto.birthDate() != null &&
                updateDto.birthDate().isAfter(LocalDate.now())) {
            userCredential.setBirthDate(updateDto.birthDate());
        }

        if (updateDto.role() != null) {
            List<RoleType> roleTypes = userCredential.getUser().getRoles();
            if (!roleTypes.contains(updateDto.role())) {
                roleTypes.add(updateDto.role());
                userCredential.getUser().setRoles(roleTypes);
            }
        }


        log.info(("\nUser account with userId: %d" +
                " was updated via users service at time: ").formatted(userId) +
                LocalDateTime.now() + "\n");


        return UserCredentialsMapper.toDto(
                userCredentialRepository.saveAndFlush(userCredential));
    }

    @Override
    public void deleteUserCredentialsById(Long userId) {

        log.info("%nUser account with id: %d was deleted via users service at time: ".formatted(userId)
                + LocalDateTime.now() + "\n");

        userCredentialRepository.deleteById(userId);
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

    private UserCredential checkUserCredentialInDb(Long userId) {

        log.info("UserCredential with id: %d was fond in db".formatted(userId)
                + LocalDateTime.now() + "\n");

        return userCredentialRepository.findById(userId).orElseThrow(() -> {

            log.info("UserCredential with id: %d was not fond in db".formatted(userId)
                    + LocalDateTime.now() + "\n");

            return new ObjectNotFoundException("User with id: %d was not present in Db".formatted(userId));
        });
    }
}
