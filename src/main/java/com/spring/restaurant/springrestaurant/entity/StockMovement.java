package com.spring.restaurant.springrestaurant.entity;

import com.spring.restaurant.springrestaurant.entity.enums.MovementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StockMovement {
    private Integer id;
    private Integer idIngredient;
    private Ingredient ingredient;
    private Double quantity;
    private MovementType type;
    private Instant creationDatetime;
    private String unit;
}
