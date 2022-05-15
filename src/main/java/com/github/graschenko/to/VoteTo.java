package com.github.graschenko.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@EqualsAndHashCode
@Value
@ToString(callSuper = true)
public class VoteTo {

    int restaurantId;
    LocalDate date;

    public VoteTo(int restaurantId, LocalDate date) {
        this.restaurantId = restaurantId;
        this.date = date;
    }
}
