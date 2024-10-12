package ru.telros.test_case_for_telros_ru.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.telros.test_case_for_telros_ru.dto.create.LoginRequest;
import ru.telros.test_case_for_telros_ru.dto.create.RefreshTokenRequestDto;
import ru.telros.test_case_for_telros_ru.dto.create.UserCredentialsNewDto;
import ru.telros.test_case_for_telros_ru.dto.response.AuthResponse;
import ru.telros.test_case_for_telros_ru.dto.response.RefreshTokenResponseDto;
import ru.telros.test_case_for_telros_ru.dto.update.UserCredentialsResponseDto;
import ru.telros.test_case_for_telros_ru.exception.exceptions.RefreshTokenException;
import ru.telros.test_case_for_telros_ru.model.User;
import ru.telros.test_case_for_telros_ru.model.UserCredential;
import ru.telros.test_case_for_telros_ru.repository.jpa.UserCredentialRepository;
import ru.telros.test_case_for_telros_ru.repository.jpa.UserRepository;
import ru.telros.test_case_for_telros_ru.security.entity.RefreshToken;
import ru.telros.test_case_for_telros_ru.security.jwt.util.JwtUtils;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtUtils jwtUtils;

    private final UserCredentialRepository credentialRepository;

    private final RefreshTokenService refreshTokenService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserCredentialsResponseDto register(UserCredentialsNewDto createUserRequest) {

        User user = User.builder()
                .userName(createUserRequest.userName())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .build();

        user.setRoles(createUserRequest.roles());

        userRepository.saveAndFlush(user);

        UserCredential credential = UserCredential.builder()
                .firstName(createUserRequest.firstName())
                .patronym(createUserRequest.patronym())
                .lastName(createUserRequest.lastName())
                .email(createUserRequest.email())
                .phoneNumber(createUserRequest.phoneNumber())
                .birthDate(createUserRequest.birthDate())
                .user(user)
                .build();

        credentialRepository.saveAndFlush(credential);

        return new UserCredentialsResponseDto(
                user.getUserName(),
                credential.getEmail(),
                createUserRequest.firstName(),
                credential.getPatronym(),
                createUserRequest.lastName(),
                credential.getPhoneNumber(),
                credential.getBirthDate()
                );
    }

    @Override
    public AuthResponse authenticationUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.userName(),
                        loginRequest.password()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        RefreshToken refreshToken = refreshTokenService.create(userDetails.getId());

        return new AuthResponse(
                userDetails.getId(),
                jwtUtils.generateJwtToken(userDetails),
                refreshToken.getToken(),
                userDetails.getUsername(),
                roles);
    }

    @Override
    public void logout() {
        var currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentPrincipal instanceof AppUserDetails userDetails) {
            refreshTokenService.deleteByUserId(userDetails.getId());
        }
    }

    @Override
    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto request) {

        String requestTokenRefresh = request.refreshToken();

        return refreshTokenService.getByRefreshToken(requestTokenRefresh)
                .map(refreshTokenService::checkRefreshToken)
                .map(RefreshToken::getId)
                .map(userId -> {
                    User user = userRepository.findById(userId).orElseThrow(() ->
                            new RefreshTokenException("Exception for userId: " + userId));

                    String token = jwtUtils.generateTokenFromUserName(user.getUserName());
                    return new RefreshTokenResponseDto(token, refreshTokenService.create(userId).getToken());
                }).orElseThrow(() -> new RefreshTokenException("RefreshToken is not found!"));
    }
}
