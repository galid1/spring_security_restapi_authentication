package com.galid.study.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "authority")
@NoArgsConstructor
@Getter
public class AuthorityEntity {
    @Id @GeneratedValue
    private Long authorityId;

    @Enumerated(value = EnumType.STRING)
    private Authority authority;

    public AuthorityEntity(Authority authority) {
        this.authority = authority;
    }
}
