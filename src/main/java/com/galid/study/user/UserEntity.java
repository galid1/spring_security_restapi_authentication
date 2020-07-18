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

    private String name;
    private String password;

    @ElementCollection
    @CollectionTable(
            name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Authority> authorities = new TreeSet<>();

    public UserEntity(String name, String password, Authority ...authorityList){
        this.name = name;
        this.password = password;
        this.authorities.addAll(Arrays.asList(authorityList));
    }

    public void login(String name, String password) {
        if(!this.name.equals(name) ||
           !this.password.equals(password))
            throw new IllegalArgumentException("로그인 정보가 일치하지 않습니다.");
    }
}
