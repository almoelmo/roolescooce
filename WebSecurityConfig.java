package com.example.roles.config;

import com.example.roles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new SuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().disable()
            .csrf().disable()
            .httpBasic()
            .and()
            .authorizeRequests().antMatchers("/register**").permitAll()
                                .antMatchers("/setCookie**").permitAll()
                                .antMatchers("/getCookie**").permitAll()
                                .antMatchers("/setSessionAttribute**").permitAll()
                                .antMatchers("/getSessionAttribute**").permitAll()
                                .antMatchers("/tasks").hasAnyRole("USER", "ADMIN")
                                .antMatchers("/users**").hasRole("ADMIN")
                                .antMatchers("/tasks/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .formLogin()
                .permitAll()
                .successHandler(successHandler())
            .and()
                .logout()
                .permitAll();
    }
}