package ru.telros.test_case_for_telros_ru.dto.update;

import java.time.LocalDate;

public record UserResponseDto(String userName,
                              String password,
                              String email,
                              String firstname,
                              String patronym,
                              String lastName,
                              String phoneNumber,
                              LocalDate birthDat) {
}
