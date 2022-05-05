package com.github.graschenko.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.graschenko.AuthUser;
import com.github.graschenko.model.Role;
import com.github.graschenko.model.User;
import com.github.graschenko.repository.UserRepository;
import com.github.graschenko.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static com.github.graschenko.util.UserUtil.PASSWORD_ENCODER;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    @Autowired
    private void setMapper(ObjectMapper objectMapper) {
        JsonUtil.setMapper(objectMapper);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(email -> {
                    log.debug("Authentication {}", email);
                    Optional<User> optionalUser = userRepository.getByEmail(email);
                    return new AuthUser(optionalUser.orElseThrow(
                            () -> new UsernameNotFoundException("User '" + email + "' was not found")));
                })
                .passwordEncoder(PASSWORD_ENCODER);
    }

    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/profile").anonymous()
                .antMatchers("/api/**").authenticated()
                .and().formLogin()
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }

}
