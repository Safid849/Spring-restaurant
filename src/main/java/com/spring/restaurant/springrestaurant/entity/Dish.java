package com.spring.restaurant.springrestaurant.entity;

import com.spring.restaurant.springrestaurant.entity.enums.DishTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    private Integer id;
    private String name;
    private DishTypeEnum dishType;
    private Double sellingPrice;
    private List<DishIngredient> ingredients;
}
