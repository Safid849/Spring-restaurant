package com.spring.restaurant.springrestaurant.repository;

import com.spring.restaurant.springrestaurant.entity.DishIngredient;
import com.spring.restaurant.springrestaurant.entity.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DishIngredientRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<DishIngredient> findByDishId(Integer dishId) {
        String sql = "SELECT id_dish, id_ingredient, quantity_required, unit " +
                "FROM dishingredient WHERE id_dish = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DishIngredient.class), dishId);
    }

    public void deleteByDishId(Integer dishId) {
        String sql = "DELETE FROM dishingredient WHERE id_dish = ?";
        jdbcTemplate.update(sql, dishId);
    }

    public void save(DishIngredient component) {
        String sql = "INSERT INTO dishingredient (id_dish, id_ingredient, quantity_required, unit) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                component.getIdDish(),
                component.getIdIngredient(),
                component.getRequiredQuantity(),
                component.getUnit()
        );
    }
}
