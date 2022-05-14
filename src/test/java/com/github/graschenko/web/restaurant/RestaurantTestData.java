package com.github.graschenko.web.restaurant;

import com.github.graschenko.model.Restaurant;
import com.github.graschenko.to.RestaurantTo;
import com.github.graschenko.web.MatcherFactory;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);

    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class);

    public static final int KFC_ID = 1;
    public static final int MC_ID = 2;
    public static final int KING_ID = 3;
    public static final int NOT_FOUND = 100;

    public static final Restaurant kfc = new Restaurant(KFC_ID, "KFC");
    public static final Restaurant mc = new Restaurant(MC_ID, "McDonalds");
    public static final Restaurant king = new Restaurant(KING_ID, "BurgerKing");

    public static Restaurant getNew() {
        return new Restaurant(null, "New restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(KING_ID, "Updated BurgerKing");
    }
}
