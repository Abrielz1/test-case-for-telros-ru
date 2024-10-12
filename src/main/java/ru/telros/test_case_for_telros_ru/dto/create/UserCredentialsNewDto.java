package ru.telros.test_case_for_telros_ru.dto.create;

import ru.telros.test_case_for_telros_ru.security.model.RoleType;
import java.time.LocalDate;
import java.util.List;

public record UserCredentialsNewDto(String firstName,
                                    String patronym,
                                    String lastName,
                                    String email,
                                    String phoneNumber,
                                    String userName,
                                    String password,
                                    LocalDate birthDate,
                                    List<RoleType> roles) {
}
