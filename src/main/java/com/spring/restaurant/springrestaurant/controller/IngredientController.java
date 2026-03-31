package com.spring.restaurant.springrestaurant.controller;


import com.spring.restaurant.springrestaurant.entity.Ingredient;
import com.spring.restaurant.springrestaurant.entity.StockMovement;
import com.spring.restaurant.springrestaurant.exception.ResourceNotFoundException;
import com.spring.restaurant.springrestaurant.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor

    public class IngredientController {

        private final IngredientService ingredientService;

        @GetMapping
        public ResponseEntity<List<Ingredient>> getAllIngredients() {
            List<Ingredient> ingredients = ingredientService.getAllIngredients();
            return ResponseEntity.ok(ingredients);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Ingredient> getIngredientById(@PathVariable Integer id)
                throws ResourceNotFoundException {
            Ingredient ingredient = ingredientService.getIngredientById(id);
            return ResponseEntity.ok(ingredient);
        }

        @GetMapping("/{id}/stock")
        public ResponseEntity<Double> getIngredientStock(
                @PathVariable Integer id,
                @RequestParam String at,
                @RequestParam String unit
        ) throws ResourceNotFoundException {
            Double stockValue = ingredientService.getStockValue(id, at, unit);
            return ResponseEntity.ok(stockValue);
        }

        @PostMapping
        public ResponseEntity<Void> createIngredient(@RequestBody Ingredient ingredient) {
            ingredientService.create(ingredient);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

    @GetMapping("/{id}/stockMovements")
    public List<StockMovement> getStockMovements(
            @PathVariable Integer id,
            @RequestParam Instant from,
            @RequestParam Instant to) throws ResourceNotFoundException {
        return ingredientService.getMovementsByIngredient(id, from, to);
    }

    @PostMapping("/{id}/stockMovements")
    public List<StockMovement> postStockMovements(
            @PathVariable Integer id,
            @RequestBody List<StockMovement> movements) throws ResourceNotFoundException {
        return ingredientService.addMovements(id, movements);
    }
}
