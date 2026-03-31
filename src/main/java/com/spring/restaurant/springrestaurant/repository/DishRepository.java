package com.spring.restaurant.springrestaurant.repository;


import com.spring.restaurant.springrestaurant.entity.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class DishRepository {
    private final JdbcTemplate jdbcTemplate;


    public List<Dish> findAll() {
        String sql = "SELECT id, name, selling_price, dish_type AS type FROM dish";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Dish.class));
    }

    public boolean existsById(Integer id) {
        String sql = "SELECT count(*) FROM dish WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
