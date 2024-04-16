package com.example.pr5_4.Controllers;

import com.example.pr5_4.Models.Role;
import com.example.pr5_4.Models.User;
import com.example.pr5_4.Repositories.ArticleRepository;
import com.example.pr5_4.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegController {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/Registration")
    public String Registration(Model model) {
        return "Registration";
    }

    @PostMapping("/Registration")
    private String Reg(User user, Model model)
    {
        User user_from_db = userRepository.findUserByLogin(user.getLogin());
        if (user_from_db != null)
        {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "Registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/Authorization";
    }
}
