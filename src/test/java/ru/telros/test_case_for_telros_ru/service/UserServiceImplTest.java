package ru.telros.test_case_for_telros_ru.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.telros.test_case_for_telros_ru.dto.update.UserShortResponseDto;
import ru.telros.test_case_for_telros_ru.exception.exceptions.ObjectNotFoundException;
import ru.telros.test_case_for_telros_ru.mapper.UserMapper;
import ru.telros.test_case_for_telros_ru.model.User;
import ru.telros.test_case_for_telros_ru.repository.jpa.UserRepository;
import ru.telros.test_case_for_telros_ru.security.model.RoleType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private UserShortResponseDto userDto;


    @BeforeEach
    void setUp() {

        user = User.builder()
                .id(1L)
                .userName("vasyan")
                .password("123")
                .roles(List.of(RoleType.ROLE_USER, RoleType.ROLE_ADMIN))
                .build();

        repository.saveAndFlush(user);

        userDto = UserMapper.toDto(user);
    }

    @Test
    void whenUserServiceCalledGetUserCredentialList_ThenReceivedUserWithNameVasyan() {

        List<User> list = new ArrayList<>();

        list.add(user);

        PageRequest p = PageRequest.of(0, 20);

        when(repository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<User>(list));

        List<UserShortResponseDto> userDtoList = this.userService.getUserCredentialList(p);

        assertEquals(1L, userDtoList.size());
        assertEquals("vasyan", userDtoList.get(0).getUserName());
        assertEquals(userDto, userDtoList.get(0));
    }

    @Test
    void whenThroughUserServiceCalledGetUserCredentialById_ThenReceivedUserWithNameVasyan() {

        when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(user));

        userDto = userService.getUserCredentialById(user.getId());

        assertThat(userDto.getUserName()).isEqualTo("vasyan");
    }

    @Test
    void whenThroughUserServiceCalledGetUserCredentialByIdWithWrongID_Then_ObjectNotFoundExceptionThrown() {

        when(repository.findById(anyLong()))
                .thenReturn(Optional.empty());


        ObjectNotFoundException exc = assertThrows(ObjectNotFoundException.class,
                () -> userService.getUserCredentialById(1000L)
        );

        assertEquals("User with id: 1000 was not present in Db", exc.getMessage());
    }
}