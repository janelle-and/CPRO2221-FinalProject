package com.finalProject.campusJobBoardSystem.controller;

import com.finalProject.campusJobBoardSystem.model.User;
import com.finalProject.campusJobBoardSystem.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/register/student")
    public String registerStudent(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/register/employer")
    public String registerEmployer(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register/student")
    public String studentRegister(@ModelAttribute User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(User.Role.STUDENT);
        user.setStatus(User.Status.ACTIVE);
        repo.save(user);
        return "redirect:/login?registered";
    }
    @PostMapping("/register/employer")
    public String employerRegister(@ModelAttribute User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(User.Role.EMPLOYER);
        user.setStatus(User.Status.ACTIVE);
        repo.save(user);
        return "redirect:/login?registered";
    }
}
