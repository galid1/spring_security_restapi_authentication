package com.galid.study.user;

import com.galid.study.SignInRequest;
import com.galid.study.SignUpRequest;
import com.galid.study.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ApiTokenRepository apiTokenRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public Long signUp(SignUpRequest request) {
        UserEntity userEntity = new UserEntity(request.getName(), request.getPassword());
        return userEntity.getUserId();
    }

    @Transactional
    public Long signIn(SignInRequest request) {
        UserEntity userEntity = userRepository.findFirstByName(request.getName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 User 입니다."));

        userEntity.login(request.getName(), encoder.encode(request.getPassword()));
        issueToken(userEntity.getUserId());

        return userEntity.getUserId();
    }

    @Transactional
    public void issueToken(Long userId) {
        apiTokenRepository.removeByUserId(userId);

        ApiTokenEntity entity = ApiTokenEntity.builder()
                .userId(userId)
                .accessToken(jwtUtil.generateToken(userId, ApiTokenType.ACCESS_TOKEN))
                .refreshToken(jwtUtil.generateToken(userId, ApiTokenType.REFRESH_TOKEN))
                .build();

        apiTokenRepository.save(entity);
    }
}
