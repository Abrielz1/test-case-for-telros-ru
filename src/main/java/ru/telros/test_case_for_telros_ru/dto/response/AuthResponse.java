package ru.telros.test_case_for_telros_ru.dto.response;

import java.util.List;

public record AuthResponse(
        Long id,

        String token,

        String refreshToken,

        String username,

        List<String>roles
) {

}
