package com.hanghae.hanghae_spring_prac.controller;

import com.hanghae.hanghae_spring_prac.user.User;
import com.hanghae.hanghae_spring_prac.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "login.html";
    }

    @GetMapping("/joinForm")
    public String joinForm() {

        return "join.html";
    }

    @PostMapping("/join")
    public String join(User user) {
        user.setRole("ROLE_USER");

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        user.setPassword(encPassword);

        userRepository.save(user);
        return "redirect:/loginForm";
    }
}
