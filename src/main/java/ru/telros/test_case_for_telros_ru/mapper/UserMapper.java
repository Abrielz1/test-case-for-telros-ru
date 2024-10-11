package ru.telros.test_case_for_telros_ru.mapper;

import ru.telros.test_case_for_telros_ru.dto.update.UserResponseCredentialResponseDto;
import ru.telros.test_case_for_telros_ru.model.User;

public class UserMapper {

    public static UserResponseCredentialResponseDto toDto(User user) {

        new  UserResponseCredentialResponseDto();
        return UserResponseCredentialResponseDto.builder()


                .build();
    }

}
