package com.github.graschenko.web.restaurant;

import com.github.graschenko.service.RestaurantService;
import com.github.graschenko.to.RestaurantTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = RestaurantController.Rest_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    static final String Rest_URL = "api/restaurant";

    private final RestaurantService restaurantService;

    @GetMapping("/{id}/with-menu")
    public RestaurantTo getByIdWithDishes(@PathVariable int id) {
        log.info("get restaurant {} with menu", id);
        return restaurantService.getByIdWithDishes(id);
    }

    @GetMapping("/with-menu")
    public List<RestaurantTo> getAllWithDishes() {
        log.info("get all restaurant with menu");
        return restaurantService.getAllWithDishes();
    }
}
