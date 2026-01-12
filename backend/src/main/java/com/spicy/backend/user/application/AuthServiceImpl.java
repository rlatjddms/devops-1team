package com.spicy.backend.user.application;

import com.spicy.backend.global.jwt.JwtProvider;
import com.spicy.backend.user.domain.RefreshToken;
import com.spicy.backend.user.domain.User;
import com.spicy.backend.user.dto.request.LoginRequest;
import com.spicy.backend.user.dto.request.LogoutRequest;
import com.spicy.backend.user.dto.request.SignUpRequest;
import com.spicy.backend.user.dto.response.LoginResponse;
import com.spicy.backend.user.enums.UserRole;
import com.spicy.backend.user.error.DuplicateLoginIdException;
import com.spicy.backend.user.error.InvalidLoginException;
import com.spicy.backend.user.storage.RefreshTokenRepository;
import com.spicy.backend.user.storage.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Override
    public void signup(SignUpRequest request) {

        if (userRepository.existsByLoginId(request.loginId())) {
            throw new DuplicateLoginIdException();
        }

        User user = User.builder()
                .loginId(request.loginId())
                .password(passwordEncoder.encode(request.password()))
                .username(request.username())
                .email(request.email())
                .userRole(UserRole.FRANCHISE)
                .build();

        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByLoginId(request.loginId()).orElseThrow(InvalidLoginException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidLoginException();
        }

        String accessToken = jwtProvider.createAccessToken(user);
        String refreshToken = jwtProvider.createRefreshToken(user);

        refreshTokenRepository.deleteByUserId(user.getId());

        LocalDateTime expiredAt = jwtProvider.getRefreshTokenExpiredAt();
        refreshTokenRepository.save(new RefreshToken(user.getId(),refreshToken, expiredAt));

        return new LoginResponse(accessToken, refreshToken);
    }

    @Override
    public void logout(LogoutRequest request) {
        refreshTokenRepository.deleteByToken(request.refreshToken());
    }
}
