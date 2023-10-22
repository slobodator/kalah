package com.bol.game.kalah.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "players")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public class Player implements UserDetails {
    @SuppressWarnings("unused")
    @Id
    @SequenceGenerator(name = "players_seq", sequenceName = "players_seq")
    @GeneratedValue(generator = "players_seq", strategy = GenerationType.SEQUENCE)
    @ToString.Include
    private Long id;

    private String email;

    private String password;

    @ToString.Include
    private String nickname;

    private Player(String email, String password, String nickname) {
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
        this.nickname = Objects.requireNonNull(nickname);
    }

    public Player(String email, String nickname) {
        this(email, "no password", nickname);
    }

    public static Player newWithPassword(String email, String password, String nickname) {
        return new Player(email, password, nickname);
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
