package com.spring.restaurant.springrestaurant.entity;

import com.spring.restaurant.springrestaurant.entity.enums.IngredientCagetoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    private Integer id;
    private String name;
    private Double price;
    private Integer quantity;
    private IngredientCagetoryEnum category;
}
