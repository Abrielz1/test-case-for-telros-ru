package ru.telros.test_case_for_telros_ru.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.telros.test_case_for_telros_ru.dto.create.UserCredentialsNewDto;
import ru.telros.test_case_for_telros_ru.dto.update.UserResponseDto;
import ru.telros.test_case_for_telros_ru.model.User;
import ru.telros.test_case_for_telros_ru.model.UserCredential;
import ru.telros.test_case_for_telros_ru.repository.UserCredentialRepository;
import ru.telros.test_case_for_telros_ru.repository.UserRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    private final UserCredentialRepository credentialRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserCredentialsNewDto createUserRequest) {

        User user = User.builder()
                .userName(createUserRequest.userName())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .build();

        user.setRoles(createUserRequest.roles());

        userRepository.saveAndFlush(user);

        UserCredential credential = UserCredential.builder()
                .firstName(createUserRequest.firstName())
                .patronym(createUserRequest.patronym())
                .lastName(createUserRequest.lastName())
                .email(createUserRequest.email())
                .phoneNumber(createUserRequest.phoneNumber())
                .birthDate(createUserRequest.birthDate())
                .user(user)
                .build();

        credentialRepository.saveAndFlush(credential);

        return new UserResponseDto(
                user.getUserName(),
                user.getPassword(),
                credential.getEmail(),
                createUserRequest.firstName(),
                credential.getPatronym(),
                createUserRequest.lastName(),
                credential.getPhoneNumber(),
                credential.getBirthDate()
                );
    }

    // TODO: логин и рефреш токен и логаут

    @Configuration
    public static class Encoder {

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
}
