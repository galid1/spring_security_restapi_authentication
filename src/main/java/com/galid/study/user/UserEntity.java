package com.galid.study.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class UserEntity {
    @Id @GeneratedValue
    private Long userId;

    private String name;
    private String password;

    public UserEntity(String name, String password){
        this.name = name;
        this.password = password;
    }

    public void login(String name, String password) {
        if(!this.name.equals(name) ||
           !this.password.equals(password))
            throw new IllegalArgumentException("로그인 정보가 일치하지 않습니다.");
    }
}
