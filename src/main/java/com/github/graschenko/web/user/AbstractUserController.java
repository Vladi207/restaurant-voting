package com.github.graschenko.web.user;

import com.github.graschenko.model.User;
import com.github.graschenko.service.UserService;
import com.github.graschenko.to.UserTo;
import com.github.graschenko.util.UserUtil;
import com.github.graschenko.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.List;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    private UserService service;

    @Autowired
    private UniqueMailValidator mailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(mailValidator);
    }

    public ResponseEntity<User> get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public ResponseEntity<User> getByEmail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        service.enable(id, enabled);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        ValidationUtil.assureIdConsistent(user, id);
        service.update(user);
    }

    public void update(UserTo userTo, int id) {
        log.info("update {} with id={}", userTo, id);
        ValidationUtil.assureIdConsistent(userTo, id);
        service.update(userTo);
    }

    public User create(User user) {
        log.info("create {}", user);
        ValidationUtil.checkNew(user);
        return service.create(user);
    }

    public  User create(UserTo userTo) {
        log.info("create {}", userTo);
        ValidationUtil.checkNew(userTo);
        return service.create(UserUtil.createNewFromTo(userTo));
    }
}
