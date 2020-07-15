package com.galid.study;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignInResponse {
    private Long userId;
    private String accessToken;
    private String refreshToken;
}
