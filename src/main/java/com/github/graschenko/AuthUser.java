package com.github.graschenko;

import com.github.graschenko.model.User;
import lombok.Getter;
import lombok.NonNull;

import java.io.Serial;

@Getter
public class AuthUser extends org.springframework.security.core.userdetails.User {

    @Serial
    private static final Long serialVersionUID = 1L;

    @NonNull
    private final User user;

    public AuthUser(User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int getId() {
        return user.id();
    }
}
