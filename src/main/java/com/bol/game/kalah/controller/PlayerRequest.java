package com.bol.game.kalah.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlayerRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private String nickname;
}
