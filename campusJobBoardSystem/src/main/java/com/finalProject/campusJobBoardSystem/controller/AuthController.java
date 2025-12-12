package com.finalProject.campusJobBoardSystem.controller;

import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.repository.UserRepository;

@Controller
public class AuthController {
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;

    public AuthController(UserRepository repo, BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute  User user, BindingResult result){
        if(result.hasErrors()){
            return "register";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setStatus(User.Status.ACTIVE);
        repo.save(user);
        return "redirect:/login?registered";
    }
}
