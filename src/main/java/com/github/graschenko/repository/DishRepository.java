package com.github.graschenko.repository;

import com.github.graschenko.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.date DESC, d.price DESC")
    List<Dish> getAll(@Param("restaurantId") int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.date=:date ORDER BY d.price DESC")
    List<Dish> getAllByDate(@Param("restaurantId") int restaurantId, @Param("date") LocalDate date);

    @Query("SELECT d FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    Optional<Dish> get(@Param("id") int id, @Param("restaurantId") int restaurantId);

}
