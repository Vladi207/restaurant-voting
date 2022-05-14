package com.github.graschenko.util;

import com.github.graschenko.model.Restaurant;
import com.github.graschenko.to.RestaurantTo;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;

@UtilityClass
public class RestaurantUtil {

    public static RestaurantTo createTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.id(), restaurant.getName(), DishUtil.getTos(restaurant.getDishes()));
    }

    public static List<RestaurantTo> getTos(Collection<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantUtil::createTo)
                .toList();
    }
}
