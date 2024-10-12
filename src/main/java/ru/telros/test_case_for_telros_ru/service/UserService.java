package ru.telros.test_case_for_telros_ru.service;

import org.springframework.data.domain.PageRequest;
import ru.telros.test_case_for_telros_ru.dto.update.UserShortResponseDto;

import java.util.List;

public interface UserService {

    List<UserShortResponseDto> getUserCredentialList(PageRequest page);

    UserShortResponseDto getUserCredentialById(Long userId);
}
