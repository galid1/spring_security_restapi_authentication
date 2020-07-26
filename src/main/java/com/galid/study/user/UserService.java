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
        UserEntity userEntity = userRepository.findFirstByAuthId(request.getAuthId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 User 입니다."));

        userEntity.login(request.getAuthId(), encoder.encode(request.getPassword()));
        issueToken(userEntity.getUserId(), userEntity.getAuthId());

        return userEntity.getUserId();
    }

    private void issueToken(Long userId, String authId) {
        apiTokenRepository.removeByUserId(userId);

        ApiTokenEntity entity = ApiTokenEntity.builder()
                .userId(userId)
                .accessToken(jwtUtil.generateToken(authId, ApiTokenType.ACCESS_TOKEN))
                .refreshToken(jwtUtil.generateToken(authId, ApiTokenType.REFRESH_TOKEN))
                .build();

        apiTokenRepository.save(entity);
    }
}
