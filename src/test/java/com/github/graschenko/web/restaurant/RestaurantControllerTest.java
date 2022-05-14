package com.github.graschenko.web.restaurant;

import com.github.graschenko.util.RestaurantUtil;
import com.github.graschenko.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.github.graschenko.web.Dish.DishTestData.*;
import static com.github.graschenko.web.restaurant.RestaurantTestData.*;
import static com.github.graschenko.web.user.UserTestData.USER_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = USER_MAIL)
public class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.Rest_URL + '/';

    @Test
    void getByIdWithDishes() throws Exception {
        kfc.setDishes(kfc_menu2_dishes);
        perform(MockMvcRequestBuilders.get(REST_URL + KFC_ID + "/with-menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.createTo(kfc)));
    }

    @Test
    void getAllWithDishes() throws Exception {
        mc.setDishes(mc_menu2_dishes);
        kfc.setDishes(kfc_menu2_dishes);
        king.setDishes(king_menu2_dishes);
        perform(MockMvcRequestBuilders.get(REST_URL + "with-menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.getTos(List.of(king, kfc, mc))));
    }
}
