package ru.telros.test_case_for_telros_ru.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.telros.test_case_for_telros_ru.dto.response.UserCredentialsUpdateDto;
import ru.telros.test_case_for_telros_ru.dto.update.UserResponseCredentialResponseDto;
import ru.telros.test_case_for_telros_ru.dto.update.UserResponseCredentialShortDto;
import ru.telros.test_case_for_telros_ru.service.UserCredentialService;
import ru.telros.test_case_for_telros_ru.util.Update;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/user/details")
@RequiredArgsConstructor
public class UserCredentialController {

    private final UserCredentialService userCredentialService;

    @GetMapping("/short")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseCredentialShortDto> getListUserShortCredentials(@PositiveOrZero
                                                                            @RequestParam(defaultValue = "0")
                                                                                Integer from,
                                                                            @Positive
                                                                            @RequestParam(defaultValue = "10")
                                                                                Integer size) {

        log.info("\nlist of users short credentials were sent from users controller" +
                " time: " + LocalDateTime.now() + "\n");

        PageRequest page = PageRequest.of(from / size, size);

        return userCredentialService.getListUserShortCredentials(page);
    }

    @GetMapping("/full")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseCredentialResponseDto> getListUserFullCredentials(@PositiveOrZero
                                                                              @RequestParam(defaultValue = "0")
                                                                              Integer from,
                                                                              @Positive
                                                                              @RequestParam(defaultValue = "10")
                                                                              Integer size) {

        log.info("\nlist of users full credentials were sent from users controller" +
                " time: " + LocalDateTime.now() + "\n");

        PageRequest page = PageRequest.of(from / size, size);

        return userCredentialService.getListUserFullCredentials(page);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseCredentialResponseDto getUserCredentialById(@Positive
                                                           @PathVariable(name = "userId")
                                                           Long userId) {

        log.info(("\nUser credentials with id: %d" +
                " was sent via users controller at time: ").formatted(userId)
                + LocalDateTime.now() + "\n");

       return userCredentialService.getUserCredentialById(userId);
    }

    @PutMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseCredentialResponseDto updateUserCredentialById(@Positive
                                                                      @PathVariable(name = "userId")
                                                                      Long userId,
                                                                      @Validated(Update.class)
                                                                      @RequestBody
                                                                      UserCredentialsUpdateDto updateDto) {
        log.info(("\nUser credentials with id: %d" +
                " was updated via users controller at time: ").formatted(userId)
                + LocalDateTime.now() + "\n");

        return userCredentialService.updateUserCredentialById(userId, updateDto);
    }

    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserCredentialsById(@Positive @PathVariable(name = "userId") Long userId) {

        log.info(("\nUser credentials with id: %d" +
                " was deleted via users controller at time: ").formatted(userId)
                + LocalDateTime.now() + "\n");

        userCredentialService.deleteUserCredentialsById(userId);
    }
}
