package com.spring.restaurant.springrestaurant.entity;

import com.spring.restaurant.springrestaurant.entity.enums.MovementType;

import java.time.LocalDateTime;

public class StockMovement {
    private Integer id;
    private Integer idIngredient;
    private Ingredient ingredient;
    private Double quantity;
    private MovementType type;
    private LocalDateTime creationDatetime;
    private String unit;
}
