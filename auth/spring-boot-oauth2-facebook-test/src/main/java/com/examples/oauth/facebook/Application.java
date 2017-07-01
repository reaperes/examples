package com.examples.oauth.facebook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.security.Principal;

//@EnableOAuth2Client
@Slf4j
@SpringBootApplication
@EnableWebMvc
@Controller
public class Application extends WebSecurityConfigurerAdapter {

    @RequestMapping(value={"", "/"})
    public String index() {
        return "index";     // TODO !important when status change to connected, request to server to get JWT token
    }

//    @RequestMapping("/auth/login")
//    public String user() {
//        // TODO redirect to facebook oauth login
//        return "Hello, world!";
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();

        //        http.authorizeRequests()
//                .antMatchers("/auth/login").permitAll()
//                .anyRequest().authenticated();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
