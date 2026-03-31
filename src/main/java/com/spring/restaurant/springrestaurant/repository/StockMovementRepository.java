package com.spring.restaurant.springrestaurant.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StockMovementRepository {
    private final JdbcTemplate jdbcTemplate;

    public Double getStockQuantityAt(Integer ingredientId, String at, String unit) {
        String sql = "SELECT SUM(CASE WHEN type = 'IN' THEN quantity ELSE -quantity END) " +
                "FROM stockmovement " +
                "WHERE id_ingredient = ? AND creation_datetime <= CAST(? AS TIMESTAMP) AND unit = ?";

        Double result = jdbcTemplate.queryForObject(sql, Double.class, ingredientId, at, unit);
        return (result != null) ? result : 0.0;
    }

    public void addMovement(Integer ingId, Double qty, String type, String unit) {
        String sql = "INSERT INTO stockmovement (id_ingredient, quantity, type, unit, creation_datetime) VALUES (?, ?, ?, ?, NOW())";
        jdbcTemplate.update(sql, ingId, qty, type, unit);
    }
}
