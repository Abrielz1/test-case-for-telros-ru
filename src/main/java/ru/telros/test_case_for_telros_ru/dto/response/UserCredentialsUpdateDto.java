package ru.telros.test_case_for_telros_ru.dto.response;

import ru.telros.test_case_for_telros_ru.security.model.RoleType;

import java.time.LocalDate;

public record UserCredentialsUpdateDto (Long userId,
                                        String firstName,
                                        String patronym,
                                        String lastName,
                                        String email,

                                        String username,

                                        String password,
                                        String phoneNumber,
                                        LocalDate birthDate,
                                        RoleType role) {

}
