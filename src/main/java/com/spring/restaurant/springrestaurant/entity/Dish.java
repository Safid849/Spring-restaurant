package com.spring.restaurant.springrestaurant.entity;

import com.spring.restaurant.springrestaurant.entity.enums.DishTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Dish {
    private Integer id;
    private Double price;
    private String name;
    private DishTypeEnum dishType;
    private List<DishIngredient> dishIngredients;

    public Dish() {
    }
}