package com.spring.restaurant.springrestaurant.service;

import com.spring.restaurant.springrestaurant.entity.Ingredient;
import com.spring.restaurant.springrestaurant.entity.StockMovement;
import com.spring.restaurant.springrestaurant.exception.ResourceNotFoundException;
import com.spring.restaurant.springrestaurant.repository.IngredientRepository;
import com.spring.restaurant.springrestaurant.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final StockMovementRepository stockMovementRepository;

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(Integer id) throws ResourceNotFoundException {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient.id=" + id + " is not found"));
    }

    public Double getStockValue(Integer id, String at, String unit) throws ResourceNotFoundException {
        this.getIngredientById(id);
        return stockMovementRepository.getStockQuantityAt(id, at, unit);
    }

    public void create(Ingredient ing) {
        ingredientRepository.save(ing);
    }

    public void update(Integer id, Ingredient ing) throws ResourceNotFoundException {
        this.getIngredientById(id);
        ingredientRepository.update(id, ing);
    }

    public void delete(Integer id) throws ResourceNotFoundException {
        this.getIngredientById(id);
        ingredientRepository.deleteById(id);
    }
    public List<StockMovement> getStockMovements(Integer id, Instant from, Instant to) throws ResourceNotFoundException {
        ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient.id=" + id + " is not found"));

        return stockMovementRepository.findByIngredientIdAndDateRange(id, from, to);
    }

    @Transactional
    public List<StockMovement> addStockMovements(Integer id, List<StockMovement> movements) throws ResourceNotFoundException {
        if (!ingredientRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Ingredient.id=" + id + " is not found");
        }

        for (StockMovement m : movements) {
            m.setIdIngredient(id);
            if (m.getCreationDatetime() == null) {
                m.setCreationDatetime(Instant.now());
            }
            stockMovementRepository.save(m);
        }
        return stockMovementRepository.findByIngredientIdAndDateRange(id, Instant.MIN, Instant.MAX);
    }

    public List<StockMovement> getMovementsByIngredient(Integer id, Instant from, Instant to) throws ResourceNotFoundException {
        // Rigueur : On vérifie si l'ingrédient existe
        if (ingredientRepository.findById(id) == null) {
            throw new ResourceNotFoundException("Ingredient.id=" + id + " is not found");
        }
        return stockMovementRepository.findByIngredientIdAndDateRange(id, from, to);
    }

    // Point (g)
    @Transactional
    public List<StockMovement> addMovements(Integer id, List<StockMovement> movements) throws ResourceNotFoundException {
        if (ingredientRepository.findById(id) == null) {
            throw new ResourceNotFoundException("Ingredient.id=" + id + " is not found");
        }

        for (StockMovement m : movements) {
            m.setIdIngredient(id);
            if (m.getCreationDatetime() == null) {
                m.setCreationDatetime(Instant.now());
            }
            stockMovementRepository.save(m);
        }

        return stockMovementRepository.findByIngredientIdAndDateRange(id, Instant.MIN, Instant.MAX);
    }
}
