package com.bol.game.kalah.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class PingController {
    @GetMapping("/ping")
    public String ping(Principal principal) {
        return "OK: " + principal.getName();
    }
}
