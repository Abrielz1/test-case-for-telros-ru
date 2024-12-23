package ru.telros.test_case_for_telros_ru.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.telros.test_case_for_telros_ru.dto.update.UserShortResponseDto;
import ru.telros.test_case_for_telros_ru.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<UserShortResponseDto> getUserList(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                  @Positive @RequestParam(defaultValue = "10") Integer size) {

        log.info("\nlist of users were sent from users controller" + " time: " + LocalDateTime.now() + "\n");
        PageRequest page = PageRequest.of(from / size, size);

        return userService.getUserCredentialList(page);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserShortResponseDto getUserById(@Positive @PathVariable(name = "userId") Long userId) {

        log.info(("\nUser with id: %d" +
                " was sent via users controller at time: ").formatted(userId)
                + LocalDateTime.now() + "\n");

        return userService.getUserCredentialById(userId);
    }
}
