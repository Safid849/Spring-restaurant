package com.spring.restaurant.springrestaurant.service;



import com.spring.restaurant.springrestaurant.entity.Dish;
import com.spring.restaurant.springrestaurant.entity.DishIngredient;
import com.spring.restaurant.springrestaurant.entity.Ingredient;
import com.spring.restaurant.springrestaurant.exception.ResourceNotFoundException;
import com.spring.restaurant.springrestaurant.repository.DishIngredientRepository;
import com.spring.restaurant.springrestaurant.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final DishIngredientRepository dishIngredientRepository;
    private final IngredientService ingredientService;

    public List<Dish> getAllDishes() throws ResourceNotFoundException {
        List<Dish> dishes = dishRepository.findAll();

        for (Dish dish : dishes) {
            List<DishIngredient> components = dishIngredientRepository.findByDishId(dish.getId());

            for (DishIngredient comp : components) {
                Ingredient detail = ingredientService.getIngredientById(comp.getIdIngredient());
                comp.setIngredient(detail);
            }
            dish.setIngredients(components);
        }
        return dishes;
    }

    @Transactional
    public void updateDishIngredients(Integer dishId, List<DishIngredient> newComponents)
            throws ResourceNotFoundException {

        if (!dishRepository.existsById(dishId)) {
            throw new ResourceNotFoundException("Le plat " + dishId + " n'existe pas.");
        }
        dishIngredientRepository.deleteByDishId(dishId);

        if (newComponents != null) {
            for (DishIngredient comp : newComponents) {
                comp.setIdDish(dishId);
                dishIngredientRepository.save(comp);
            }
        }
    }
}
