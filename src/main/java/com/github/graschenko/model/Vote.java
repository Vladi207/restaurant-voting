package com.github.graschenko.model;

import java.time.LocalDate;

public class Vote extends AbstractBaseEntity {

    private User user;

    private Restaurant restaurant;

    private LocalDate localDate;

    public Vote() {
    }

    public Vote(Integer id, LocalDate localDate) {
        super(id);
        this.localDate = localDate;
    }
}
