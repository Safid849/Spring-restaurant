package com.spring.restaurant.springrestaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishIngredient {
    private Integer idDish;
    private Integer idIngredient;
    private Double requiredQuantity;
    private String unit;
    private Ingredient ingredient;
}
