package com.github.graschenko.service;

import com.github.graschenko.model.Dish;
import com.github.graschenko.repository.DishRepository;
import com.github.graschenko.repository.RestaurantRepository;
import com.github.graschenko.to.DishTo;
import com.github.graschenko.util.DishUtil;
import com.github.graschenko.util.exception.IllegalRequestDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.github.graschenko.util.validation.ValidationUtil.*;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "restaurants")
public class DishService {

    private final DishRepository dishRepository;

    private final RestaurantRepository restaurantRepository;

    public Dish get(int id, int restaurantId) {
        return dishRepository.get(id, restaurantId).orElseThrow(
                () -> new IllegalRequestDataException("Dish id=" + id + " doesn't belong to Restaurant id=" + restaurantId)
        );
    }

    public List<Dish> getAllByRestaurant(int restaurantId) {
        return dishRepository.getAll(restaurantId);
    }

    public List<DishTo> getAllByRestaurantAndDate(int restaurantId, @Nullable LocalDate date) {
        return DishUtil.getTos(dishRepository.getAllByDate(restaurantId, date == null ? LocalDate.now() : date));
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public Dish create(DishTo dishTo, int restaurantId) {
        checkNew(dishTo);
        Dish dish = DishUtil.createNewFromTo(dishTo);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        dish.setDate(LocalDate.now());
        return dishRepository.save(dish);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void update(DishTo dishTo, int id, int restaurantId) {
        assureIdConsistent(dishTo, id);
        dishRepository.get(id, restaurantId).orElseThrow(
                () -> new IllegalRequestDataException("Dish id=" + id + " doesn't belong to Restaurant id=" + restaurantId));
        Dish dish = DishUtil.createNewFromTo(dishTo);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        dish.setDate(LocalDate.now());
        dishRepository.save(dish);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id, int restaurantId) {
        Dish dish = dishRepository.get(id, restaurantId).orElseThrow(
                () -> new IllegalRequestDataException("Dish id=" + id + " doesn't belong to Restaurant id=" + restaurantId));
        if (!dish.getDate().equals(LocalDate.now())) {
            throw new IllegalRequestDataException("Can't delete dishes from past days");
        }
        checkModification(dishRepository.delete(id), id);
    }
}
