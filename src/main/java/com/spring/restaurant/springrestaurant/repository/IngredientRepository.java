package com.spring.restaurant.springrestaurant.repository;

import com.spring.restaurant.springrestaurant.config.PostgresConfig;
import com.spring.restaurant.springrestaurant.entity.*;
import com.spring.restaurant.springrestaurant.entity.enums.CategoryEnum;
import com.spring.restaurant.springrestaurant.entity.enums.MovementTypeEnum;
import com.spring.restaurant.springrestaurant.entity.enums.Unit;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class IngredientRepository {
    private final PostgresConfig postgresConfig;

    public IngredientRepository(PostgresConfig postgresConfig) {
        this.postgresConfig = postgresConfig;
    }

    public List<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "SELECT * FROM ingredient";
        try (Connection conn = postgresConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ingredients.add(map(rs, conn));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du findAll: " + e.getMessage(), e);
        }
        return ingredients;
    }

    public Optional<Ingredient> findById(Integer id) {
        String sql = "SELECT * FROM ingredient WHERE id = ?";
        try (Connection conn = postgresConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs, conn));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du findById: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    private Ingredient map(ResultSet rs, Connection conn) throws SQLException {
        Ingredient ing = new Ingredient();
        ing.setId(rs.getInt("id"));
        ing.setName(rs.getString("name"));
        ing.setPrice(rs.getDouble("price"));
        ing.setCategory(CategoryEnum.valueOf(rs.getString("category").toUpperCase()));

        ing.setStockMovementList(findMovementsByIngredientId(ing.getId(), conn));
        return ing;
    }

    private List<StockMovement> findMovementsByIngredientId(Integer id, Connection conn) throws SQLException {
        List<StockMovement> movements = new ArrayList<>();
        String sql = "SELECT * FROM stockmovement WHERE id_ingredient = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StockMovement sm = new StockMovement();
                    sm.setId(rs.getInt("id"));
                    sm.setType(MovementTypeEnum.valueOf(rs.getString("type").toUpperCase()));
                    sm.setCreationDatetime(rs.getTimestamp("creation_datetime").toInstant());
                    sm.setValue(new StockValue(rs.getDouble("quantity"), Unit.valueOf(rs.getString("unit").toUpperCase())));
                    movements.add(sm);
                }
            }
        }
        return movements;
    }
}