package com.spring.restaurant.springrestaurant.repository;

import com.spring.restaurant.springrestaurant.entity.StockMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

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

    public List<StockMovement> findByIngredientIdAndDateRange(Integer idIngredient, Instant from, Instant to) {
        String sql = "SELECT id, id_ingredient, creation_datetime, quantity, type, unit " +
                "FROM stockmovement WHERE id_ingredient = ? " +
                "AND creation_datetime BETWEEN ? AND ? ORDER BY creation_datetime DESC";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StockMovement.class),
                idIngredient,
                Timestamp.from(from),
                Timestamp.from(to)            );
    }
    public void save(StockMovement m) {
        String sql = "INSERT INTO stockmovement (id_ingredient, creation_datetime, quantity, unit, type) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                m.getIdIngredient(),
                Timestamp.from(m.getCreationDatetime()),                 m.getQuantity(),
                m.getUnit(),
                m.getType().name()
        );
    }

    public StockMovement findLastInsertedByIngredient(Integer idIngredient) {
        String sql = "SELECT * FROM stockmovement WHERE id_ingredient = ? ORDER BY id DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(StockMovement.class), idIngredient);
    }
}
