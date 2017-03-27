package com.examples.oauth.facebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

//@EnableOAuth2Client
@SpringBootApplication
@RestController
public class Application extends WebSecurityConfigurerAdapter {
//    @RequestMapping("/auth")
//    public Principal user(Principal principal) {
//        return principal;
//    }

    @RequestMapping("/auth/login")
    public String user() {
        // TODO redirect to facebook oauth login
        return "Hello, world!";
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/auth/login").permitAll()
                .anyRequest().authenticated();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
