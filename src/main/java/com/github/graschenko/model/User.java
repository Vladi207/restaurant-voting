package com.github.graschenko.model;

import java.util.Date;
import java.util.Set;

public class User extends AbstractNamedEntity {
    private String email;

    private String password;

    private boolean enabled = true;

    private Date registered = new Date();

    private Set<Role> Roles;

    public User() {

    }

    public User(Integer id, String name, String email, String password, boolean enabled, Date registered, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    public void setRoles(Set<Role> roles) {
        Roles = roles;
    }
}
