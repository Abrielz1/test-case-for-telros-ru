package ru.telros.test_case_for_telros_ru.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.telros.test_case_for_telros_ru.dto.response.UserCredentialsUpdateDto;
import ru.telros.test_case_for_telros_ru.dto.update.UserResponseCredentialResponseDto;
import ru.telros.test_case_for_telros_ru.dto.update.UserResponseCredentialShortDto;
import ru.telros.test_case_for_telros_ru.exception.exceptions.ObjectNotFoundException;
import ru.telros.test_case_for_telros_ru.mapper.UserCredentialsMapper;
import ru.telros.test_case_for_telros_ru.model.User;
import ru.telros.test_case_for_telros_ru.model.UserCredential;
import ru.telros.test_case_for_telros_ru.repository.jpa.UserCredentialRepository;
import ru.telros.test_case_for_telros_ru.repository.jpa.UserRepository;
import ru.telros.test_case_for_telros_ru.security.model.RoleType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCredentialServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCredentialRepository credentialRepository;

    @InjectMocks
    private UserCredentialServiceImpl service;

    private UserCredential userCredential;

    private User user;

    private UserResponseCredentialShortDto credentialShortDto;

    private UserResponseCredentialResponseDto credentialResponseDto;

    private LocalDate vasyasBirthDate = LocalDate.of(1990, 10, 05);

    @BeforeEach
    void setUp() {

        user = User.builder()
                .id(1L)
                .userName("vasayn")
                .password("123")
                .roles(List.of(RoleType.ROLE_USER, RoleType.ROLE_ADMIN))
                .build();

        userRepository.save(user);

        userCredential = UserCredential.builder()
                .id(1L)
                .firstName("Vasayn")
                .patronym("Petrovich")
                .lastName("Sidorov")
                .email("etst@mail.com")
                .phoneNumber("+7(931)8525552")
                .birthDate(vasyasBirthDate)
                .user(user)
                .build();

        credentialRepository.save(userCredential);


        credentialShortDto = UserCredentialsMapper.toShortDto(userCredential);

        credentialResponseDto = UserCredentialsMapper.toDto(userCredential);
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    void When_GetListUserFullCredentials_ThenGetListOfResponseCredentialShortDto() {

        List<UserCredential> list = new ArrayList<>();
        list.add(userCredential);

        PageRequest p = PageRequest.of(0, 20);

        when(credentialRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<UserCredential>(list));

        List<UserResponseCredentialShortDto> userDtoList = this.service.getListUserShortCredentials(p);

        assertEquals(1L, userDtoList.size());
        assertEquals(credentialShortDto.getFirstName(), userDtoList.get(0).getFirstName());
        assertEquals(credentialShortDto, userDtoList.get(0));
    }

    @Test
    void When_GetListUserFullCredentials_ThenGetListOfUserResponseCredentialResponseDto() {

        List<UserCredential> list = new ArrayList<>();
        list.add(userCredential);

        PageRequest p = PageRequest.of(0, 20);

        when(credentialRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<UserCredential>(list));

        List<UserResponseCredentialResponseDto> userDtoList = this.service.getListUserFullCredentials(p);

        assertEquals(1L, userDtoList.size());
        assertEquals(credentialResponseDto.getFirstName(), userDtoList.get(0).getFirstName());
        assertEquals(credentialResponseDto, userDtoList.get(0));
    }

    @Test
    void WhenGetUserCredentialById_ThenGetUserResponseCredentialResponseDto() {

        when(credentialRepository.findById(anyLong()))
                .thenReturn(Optional
                        .ofNullable(userCredential));

        assertEquals(credentialResponseDto, service.getUserCredentialById(userCredential.getId()));
    }
    @Test
    void When_updateUserWithIdNoUserAssociateWith_ThenThrowsObjectNotFoundException() {
        when(credentialRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        UserCredentialsUpdateDto userDto = new UserCredentialsUpdateDto(
                1L,
                null,
                "Sidorivich",
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        ObjectNotFoundException exc = assertThrows(ObjectNotFoundException.class,
                () -> service.updateUserCredentialById(100L, userDto)
        );

        assertEquals("User with id: 100 was not present in Db", exc.getMessage());
    }

    @Test
    void When_DeleteUserCredentialsById_The_ThrowsObjectNotFoundException() {

        when(credentialRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        service.deleteUserCredentialsById(userCredential.getId());

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                ()-> service.getUserCredentialById(userCredential.getId()));

        assertEquals("User with id: 1 was not present in Db", exception.getMessage());
    }
}