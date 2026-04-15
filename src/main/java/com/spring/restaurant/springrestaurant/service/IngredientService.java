package com.spring.restaurant.springrestaurant.service;

import com.spring.restaurant.springrestaurant.entity.Ingredient;
import com.spring.restaurant.springrestaurant.entity.StockValue;
import com.spring.restaurant.springrestaurant.entity.enums.Unit;
import com.spring.restaurant.springrestaurant.exception.NotFoundException;
import com.spring.restaurant.springrestaurant.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient getById(Integer id) throws NotFoundException {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
        if (optionalIngredient.isEmpty()) {
            throw new NotFoundException("Ingredient.id=" + id + " is not found");
        }
        return optionalIngredient.get();
    }

    public StockValue getStockValueAt(Integer ingredientId, Instant temporal, Unit unit) throws NotFoundException {
        Ingredient ingredient = getById(ingredientId);
        return ingredient.getStockValueAt(temporal, unit);
    }
}