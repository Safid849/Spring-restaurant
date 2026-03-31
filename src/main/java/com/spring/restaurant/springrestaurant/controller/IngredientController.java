package com.spring.restaurant.springrestaurant.controller;


import com.spring.restaurant.springrestaurant.entity.Ingredient;
import com.spring.restaurant.springrestaurant.exception.RessourceNotFoundException;
import com.spring.restaurant.springrestaurant.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                throws RessourceNotFoundException {
            Ingredient ingredient = ingredientService.getIngredientById(id);
            return ResponseEntity.ok(ingredient);
        }

        @GetMapping("/{id}/stock")
        public ResponseEntity<Double> getIngredientStock(
                @PathVariable Integer id,
                @RequestParam String at,
                @RequestParam String unit
        ) throws RessourceNotFoundException {
            Double stockValue = ingredientService.getStockValue(id, at, unit);
            return ResponseEntity.ok(stockValue);
        }

        @PostMapping
        public ResponseEntity<Void> createIngredient(@RequestBody Ingredient ingredient) {
            ingredientService.create(ingredient);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

}
