package com.spring.restaurant.springrestaurant.repository;

import com.spring.restaurant.springrestaurant.entity.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IngredientRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Ingredient> findAll() {
        String sql = "SELECT id, name, category, price FROM ingredient";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Ingredient.class));
    }

    public Optional<Ingredient> findById(Integer id) {
        String sql = "SELECT * FROM ingredient WHERE id = ?";
        try {
            Ingredient ingredient = jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(Ingredient.class), id);
            return Optional.ofNullable(ingredient);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void save(Ingredient ing) {
        String sql = "INSERT INTO ingredient (name, category, price) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, ing.getName(), ing.getCategory().name(), ing.getPrice());
    }

    public void update(Integer id, Ingredient ing) {
        String sql = "UPDATE ingredient SET name = ?, category = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, ing.getName(), ing.getCategory().name(), ing.getPrice(), id);
    }

    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM dishingredient WHERE id_ingredient = ?", id);
        jdbcTemplate.update("DELETE FROM ingredient WHERE id = ?", id);
    }
}

