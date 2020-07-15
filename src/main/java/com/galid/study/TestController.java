package com.galid.study;

import com.galid.study.user.ApiTokenEntity;
import com.galid.study.user.ApiTokenRepository;
import com.galid.study.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final UserService service;
    private final ApiTokenRepository apiTokenRepository;

    @PostMapping("/signUp")
    public Long signUp(@RequestBody SignUpRequest signUpRequest) {
        return service.signUp(signUpRequest);
    }

    @PostMapping("/signIn")
    public SignInResponse signIn(@RequestBody SignInRequest signInRequest) {
        Long userId = service.signIn(signInRequest);
        ApiTokenEntity apiTokenEntity = apiTokenRepository.findFirstByUserId(userId).get();

        return new SignInResponse(userId, apiTokenEntity.getAccessToken(), apiTokenEntity.getRefreshToken());
    }

    @GetMapping("/contents")
    public String contents() {
        return "u logged in !!! here you are!";
    }
}

