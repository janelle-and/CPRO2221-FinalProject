package com.finalProject.campusJobBoardSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.finalProject.campusJobBoardSystem.service.UserService;

@Configuration
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService us) {
        this.userService = us;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/home", "/login", "/register", "/error").permitAll()
                        .requestMatchers("/jobList/**", "/jobDetails/**", "/apply/**").hasRole("STUDENT")
                        // ** is used to have access to all the sub files (like view application and then search by id)
                        .requestMatchers("/myJobs/**", "/addJob", "/editJob/**", "/viewApplicants/**").hasRole("EMPLOYER")
                        .requestMatchers("/jobApproval/**", "/userManagement/**").hasRole("ADMIN")
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        //where to set the page to once logged in
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()

                );

        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
