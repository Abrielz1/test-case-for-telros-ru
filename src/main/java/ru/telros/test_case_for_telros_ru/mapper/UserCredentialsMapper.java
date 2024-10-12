package ru.telros.test_case_for_telros_ru.mapper;

import ru.telros.test_case_for_telros_ru.dto.update.UserResponseCredentialResponseDto;
import ru.telros.test_case_for_telros_ru.dto.update.UserResponseCredentialShortDto;
import ru.telros.test_case_for_telros_ru.model.UserCredential;

public class UserCredentialsMapper {

    public static UserResponseCredentialResponseDto toDto(UserCredential userCredential) {

        new UserResponseCredentialResponseDto();
        return UserResponseCredentialResponseDto.builder()
                .firstName(userCredential.getFirstName())
                .patronym(userCredential.getPatronym())
                .lastName(userCredential.getLastName())
                .email(userCredential.getEmail())
                .phoneNumber(userCredential.getPhoneNumber())
                .birthDate(userCredential.getBirthDate())
                .build();
    }

    public static UserResponseCredentialShortDto toShortDto(UserCredential userCredential) {

        new UserResponseCredentialShortDto();
        return UserResponseCredentialShortDto.builder()
                .firstName(userCredential.getFirstName())
                .patronym(userCredential.getPatronym())
                .lastName(userCredential.getLastName())
                .birthDate(userCredential.getBirthDate())
                .build();
    }
}
