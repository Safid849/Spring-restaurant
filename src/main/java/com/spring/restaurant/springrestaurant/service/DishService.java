package com.spring.restaurant.springrestaurant.service;

import com.spring.restaurant.springrestaurant.entity.Dish;
import com.spring.restaurant.springrestaurant.entity.Ingredient;
import com.spring.restaurant.springrestaurant.exception.NotFoundException;
import com.spring.restaurant.springrestaurant.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    public Dish getById(Integer id) throws NotFoundException {
        Optional<Dish> optionalDish = dishRepository.findById(id);
        if (optionalDish.isEmpty()) {
            throw new NotFoundException("Dish.id=" + id + " is not found");
        }
        return optionalDish.get();
    }
    public void updateIngredients(Integer dishId, List<Ingredient> newIngredients) throws NotFoundException {
        this.getById(dishId);

        dishRepository.updateIngredients(dishId, newIngredients);
    }
}