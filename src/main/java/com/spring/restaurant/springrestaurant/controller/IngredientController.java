package com.spring.restaurant.springrestaurant.controller;

import com.spring.restaurant.springrestaurant.entity.Ingredient;
import com.spring.restaurant.springrestaurant.entity.StockValue;
import com.spring.restaurant.springrestaurant.entity.enums.Unit;
import com.spring.restaurant.springrestaurant.exception.NotFoundException;
import com.spring.restaurant.springrestaurant.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredients")
    public ResponseEntity<?> getIngredients() {
        try {
            List<Ingredient> ingredients = ingredientService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(ingredients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable Integer id) {
        try {
            Ingredient ingredient = ingredientService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(ingredient);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/ingredients/{id}/stock")
    public ResponseEntity<?> getStockValue(
            @PathVariable Integer id,
            @RequestParam(required = false) Instant at,
            @RequestParam(required = false) Unit unit) {

        if (at == null || unit == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Either mandatory query parameter `at` or `unit` is not provided.");
        }

        try {
            StockValue stockValue = ingredientService.getStockValueAt(id, at, unit);
            return ResponseEntity.status(HttpStatus.OK).body(stockValue);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}