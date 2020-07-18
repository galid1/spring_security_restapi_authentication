package com.galid.study.security;

import com.galid.study.user.UserEntity;
import com.galid.study.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RestUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findFirstByName(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return new User(userEntity.getAuthId(), userEntity.getPassword(), getAuthorities(userEntity));
    }

    private List<SimpleGrantedAuthority> getAuthorities(UserEntity userEntity) {
        return userEntity.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
    }
}
