package com.hanghae.hanghae_spring_prac.user;

import com.hanghae.hanghae_spring_prac.domain.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Getter @Setter
@Entity
public class User extends Timestamped {

    @Builder
    public User(String username, String password, String email, String provider, String providerId, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String provider;

    private String providerId;

    private String role; // ROLE_USER, ROLE_ADMIN
}
