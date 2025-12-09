package com.finalProject.campusJobBoardSystem.controller;

import com.finalProject.campusJobBoardSystem.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
//    private final UserRepository repo;
//    private final BCryptPasswordEncoder encoder;
//
//    public AuthController(UserRepository repo, BCryptPasswordEncoder encoder) {
//        this.repo = repo;
//        this.encoder = encoder;
//    }
//
//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }
//
//    @GetMapping("/register")
//    public String register(Model model){
//        model.addAttribute("user", new User());
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute User user){
//        user.setPassword(encoder.encode(user.getPassword()));
//        user.setRole("ROLE_USER");
//        user.setEnabled(true);
//        repo.save(user);
//        return "redirect:/login?registered";
//    }
}
