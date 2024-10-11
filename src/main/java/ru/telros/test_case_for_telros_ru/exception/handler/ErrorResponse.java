package ru.telros.test_case_for_telros_ru.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private String error;

    private String description;
}
