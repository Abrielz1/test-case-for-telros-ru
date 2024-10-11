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
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCredentialServiceImpl implements UserCredentialService {

    private final UserCredentialRepository userCredentialRepository;

    private final UserRepository userRepository;

    @Override
    public List<UserResponseCredentialShortDto> getListUserShortCredentials(PageRequest page) {
        return userCredentialRepository.findAll(page).stream()
                .map(UserCredentialsMapper::toShortDto)
                .toList();
    }

    @Override
    public List<UserResponseCredentialResponseDto> getListUserFullCredentials(PageRequest page) {
        return userCredentialRepository.findAll(page).stream()
                .map(UserCredentialsMapper::toDto)
                .toList();
    }

    @Override
    public UserResponseCredentialResponseDto getUserCredentialById(Long userId) {

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

        userCredentialRepository.saveAndFlush(userCredential);

        return UserCredentialsMapper.toDto(userCredential);
    }

    @Override
    public void deleteUserCredentialsById(Long userId) {

        userCredentialRepository.deleteById(userId);
    }

    private User checkUserInDb(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> {
                log.info("User in Db is not present!");
            return new ObjectNotFoundException("User in Db not present!");
        });
    }

    private UserCredential checkUserCredentialInDb(Long userId) {

        return userCredentialRepository.findById(userId).orElseThrow(() -> {
            log.info("UserCredential in Db is not present!");
            return new ObjectNotFoundException("User in Db not present!");
        });
    }
}
