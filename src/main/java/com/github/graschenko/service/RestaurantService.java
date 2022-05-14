package com.github.graschenko.service;

import com.github.graschenko.model.Restaurant;
import com.github.graschenko.repository.DishRepository;
import com.github.graschenko.repository.RestaurantRepository;
import com.github.graschenko.to.RestaurantTo;
import com.github.graschenko.util.ToMapper;
import com.github.graschenko.util.exception.IllegalRequestDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.github.graschenko.util.RepositoryUtil.findById;
import static com.github.graschenko.util.validation.ValidationUtil.*;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "restaurants")
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final DishRepository dishRepository;

    private final ToMapper toMapper;

    public Restaurant get(int id) {
        return findById(restaurantRepository, id);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void delete(int id) {
        findById(restaurantRepository, id);
        if (!dishRepository.getAll(id).stream()
                .allMatch(dish -> dish.getDate().equals(LocalDate.now()))) {
            throw new IllegalRequestDataException("Cannot delete a restaurant that already has a food history");
        }

        checkModification(restaurantRepository.delete(id), id);
    }

    @CacheEvict(allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

    public void update(Restaurant restaurant, int id) {
        findById(restaurantRepository, id);
        assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }

    public List<RestaurantTo> getAllWithDishes() {
        return toMapper.getRestaurantTos(restaurantRepository.getAllByDateWithDishes(LocalDate.now()));
    }

    public RestaurantTo getByIdWithDishes(int id) {
        return restaurantRepository.getByIdAndDateWithDishes(id, LocalDate.now()).map(toMapper::createRestaurantTo).orElseThrow(
                () -> new IllegalRequestDataException("Restaurant with id=\" + id + \" not found")
        );
    }
}
