package ru.telros.test_case_for_telros_ru.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.telros.test_case_for_telros_ru.service.UserCredentialService;

@RestController
@RequestMapping("/user/details")
@RequiredArgsConstructor
public class UserCredentialController {

    private final UserCredentialService userCredentialService;

    // todo GetAll

    // todo getById

    // todo Update

    // todo Delete
}
