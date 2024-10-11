package ru.telros.test_case_for_telros_ru.dto.response;

import java.time.LocalDate;

public record UserCredentialsUpdateDto (Long userId,
                                        String firstName,
                                        String patronym,
                                        String lastName,
                                        String email,
                                        String phoneNumber,
                                        LocalDate birthDate) {

}
