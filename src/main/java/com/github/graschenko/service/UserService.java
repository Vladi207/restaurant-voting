package com.github.graschenko.service;

import com.github.graschenko.model.User;
import com.github.graschenko.repository.UserRepository;
import com.github.graschenko.to.UserTo;
import com.github.graschenko.util.UserUtil;
import com.github.graschenko.util.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<User> get(int id) {
        return ResponseEntity.of(userRepository.findById(id));
    }

    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(userRepository.delete(id) != 0, id);
    }

    public User prepareAndSave(User user) {
        return userRepository.save(UserUtil.prepareToSave(user));
    }

    public List<User> getAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

    public ResponseEntity<User> getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return ValidationUtil.checkNotFound(ResponseEntity.of(userRepository.getByEmail(email)), "email=" + email);
    }

    @Transactional
    public void enable(int id, boolean enabled) {
        User user = userRepository.getById(id);
        user.setEnabled(enabled);
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        prepareAndSave(user);
    }

    @Transactional
    public void update(UserTo userTo) {
        User user = userRepository.getById(userTo.id());
        prepareAndSave(UserUtil.updateFromTo(user, userTo));
    }

    public User create(User user) {
        Assert.notNull(user,"user must not be null");
        return prepareAndSave(user);
    }
}
