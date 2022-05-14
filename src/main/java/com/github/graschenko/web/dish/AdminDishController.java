package com.github.graschenko.web.dish;

import com.github.graschenko.model.Dish;
import com.github.graschenko.service.DishService;
import com.github.graschenko.to.DishTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {

    public static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";

    private final DishService dishService;

    @GetMapping
    public List<Dish> getAllByRestaurant(@PathVariable int restaurantId) {
        log.info("get all dishes for restaurant {}", restaurantId);
        return dishService.getAllByRestaurant(restaurantId);
    }

    @GetMapping("/by-date")
    public List<DishTo> getAllByRestaurantAndDate(@PathVariable int restaurantId, @RequestParam LocalDate date) {
        log.info("get All dishes for restaurant {} by date {}", restaurantId, date);
        return dishService.getAllByRestaurantAndDate(restaurantId, date);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id,@PathVariable int restaurantId) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return dishService.get(id, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody DishTo dishTo, @PathVariable int restaurantId) {
        log.info("create dish {} to restaurant {}", dishTo, restaurantId);
        Dish created = dishService.create(dishTo, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update dish {} for restaurant {}", id, restaurantId);
        dishService.update(dishTo, id, restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete dish {} for restaurant {}", id, restaurantId);
        dishService.delete(id,restaurantId);
    }
}
