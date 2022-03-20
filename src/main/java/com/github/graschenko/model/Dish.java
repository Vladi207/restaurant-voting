package com.github.graschenko.model;

import java.time.LocalDate;

public class Dish extends AbstractNamedEntity {
    private Integer price;

    private Restaurant restaurant;

    private LocalDate localDate;

    public Dish(Integer id, String name, Integer price, LocalDate localDate) {
        super(id, name);
        this.price = price;
        this.localDate = localDate;
    }
}
