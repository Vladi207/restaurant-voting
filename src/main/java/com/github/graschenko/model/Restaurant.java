package com.github.graschenko.model;

import java.util.List;

public class Restaurant extends AbstractNamedEntity {

    private List<Dish> dishes;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
