package ru.telros.test_case_for_telros_ru.dto.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseCredentialShortDto {

    private String firstName;

    private String patronym;

    private String lastName;

    private LocalDate birthDate;
}
