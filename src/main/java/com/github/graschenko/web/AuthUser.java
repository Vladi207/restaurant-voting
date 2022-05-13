package com.github.graschenko.web;

import com.github.graschenko.model.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.io.Serial;

@Getter
@ToString(of = "user")
public class AuthUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }
}
