package com.github.graschenko.util;

import com.github.graschenko.model.Restaurant;
import com.github.graschenko.to.RestaurantTo;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface ToMapper {

    RestaurantTo createRestaurantTo(Restaurant restaurant);

    List<RestaurantTo> getRestaurantTos(Collection<Restaurant> restaurants);
}
