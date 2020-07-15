package com.galid.study.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "api_token")
@Getter
@NoArgsConstructor
public class ApiTokenEntity {
    @Id @GeneratedValue
    private Long apiTokenId;

    private Long userId;

    private String accessToken;
    private String refreshToken;

    @Builder
    public ApiTokenEntity(Long userId, String accessToken, String refreshToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
