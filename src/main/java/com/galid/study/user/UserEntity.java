package com.galid.study.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class UserEntity {
    @Id @GeneratedValue
    private Long userId;

    private String authId;
    private String password;

    @ElementCollection
    @CollectionTable(
            name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Authority> authorities = new TreeSet<>();

    public UserEntity(String authId, String password, Authority ...authorityList){
        this.authId = authId;
        this.password = password;
        this.authorities.addAll(Arrays.asList(authorityList));
    }

    public void login(String authId, String password) {
        if(!this.authId.equals(authId) ||
           !this.password.equals(password))
            throw new IllegalArgumentException("로그인 정보가 일치하지 않습니다.");
    }
}
