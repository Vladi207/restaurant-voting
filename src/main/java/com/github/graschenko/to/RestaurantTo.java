package com.github.graschenko.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Value
@ToString(callSuper = true)
public class RestaurantTo extends NamedTo {

    List<DishTo> dishes;

    public RestaurantTo(Integer id, String name, List<DishTo> dishes) {
        super(id, name);
        this.dishes = dishes;
    }
}
