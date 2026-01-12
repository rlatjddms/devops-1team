package com.spicy.backend.user.application;

import com.spicy.backend.user.storage.UserRepository;
import com.spicy.backend.user.domain.User;
import com.spicy.backend.user.dto.request.UpdateMyInfoRequest;
import com.spicy.backend.user.dto.request.UpdatePasswordRequest;
import com.spicy.backend.user.dto.response.MyInfoResponse;
import com.spicy.backend.user.dto.response.UserHQViewResponse;
import com.spicy.backend.user.error.InvalidPasswordException;
import com.spicy.backend.user.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public MyInfoResponse getMe(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return MyInfoResponse.from(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserHQViewResponse getUser(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return UserHQViewResponse.from(user);
    }

    @Override
    public void updateMe(Long userId, UpdateMyInfoRequest request) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.updateInfo(request.username(), request.email());
    }

    @Override
    public void updatePassword(Long userId, UpdatePasswordRequest request) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        user.changePassword(passwordEncoder.encode(request.newPassword()));
    }

    @Override
    public void withdraw(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }
}
