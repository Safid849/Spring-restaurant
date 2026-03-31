package com.spring.restaurant.springrestaurant.controller;

import com.spring.restaurant.springrestaurant.entity.Dish;
import com.spring.restaurant.springrestaurant.entity.DishIngredient;
import com.spring.restaurant.springrestaurant.exception.ResourceNotFoundException;
import com.spring.restaurant.springrestaurant.service.DishService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }
    @GetMapping
    public ResponseEntity<List<Dish>> getAllDishes() throws ResourceNotFoundException {
        List<Dish> dishes = dishService.getAllDishes();
        return ResponseEntity.ok(dishes);
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<Void> updateDishIngredients(
            @PathVariable Integer id,
            @RequestBody List<DishIngredient> ingredients
    ) throws ResourceNotFoundException {
        dishService.updateDishIngredients(id, ingredients);
        return ResponseEntity.noContent().build();
    }
}
