package com.bol.game.kalah.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "players")
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Player implements UserDetails {
    @SuppressWarnings("unused")
    @Id
    @SequenceGenerator(name = "players_seq", sequenceName = "players_seq")
    @GeneratedValue(generator = "players_seq", strategy = GenerationType.SEQUENCE)
    @ToString.Include
    private Long id;

    private String email;

    @Getter
    private String password;

    @Nullable
    private String nickname;

    public Player(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
