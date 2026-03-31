package com.spring.restaurant.springrestaurant.service;

import com.spring.restaurant.springrestaurant.entity.Ingredient;
import com.spring.restaurant.springrestaurant.exception.RessourceNotFoundException;
import com.spring.restaurant.springrestaurant.repository.IngredientRepository;
import com.spring.restaurant.springrestaurant.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final StockMovementRepository stockMovementRepository;

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(Integer id) throws RessourceNotFoundException {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Ingredient.id=" + id + " is not found"));
    }

    public Double getStockValue(Integer id, String at, String unit) throws RessourceNotFoundException {
        this.getIngredientById(id);
        return stockMovementRepository.getStockQuantityAt(id, at, unit);
    }

    public void create(Ingredient ing) {
        ingredientRepository.save(ing);
    }

    public void update(Integer id, Ingredient ing) throws RessourceNotFoundException {
        this.getIngredientById(id);
        ingredientRepository.update(id, ing);
    }

    public void delete(Integer id) throws RessourceNotFoundException {
        this.getIngredientById(id);
        ingredientRepository.deleteById(id);
    }
}
