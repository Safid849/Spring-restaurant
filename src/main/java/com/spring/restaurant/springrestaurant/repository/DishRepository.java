package com.spring.restaurant.springrestaurant.repository;

import com.spring.restaurant.springrestaurant.config.PostgresConfig;
import com.spring.restaurant.springrestaurant.entity.*;
import com.spring.restaurant.springrestaurant.entity.enums.CategoryEnum;
import com.spring.restaurant.springrestaurant.entity.enums.DishTypeEnum;
import com.spring.restaurant.springrestaurant.entity.enums.Unit;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DishRepository {
    private final PostgresConfig postgresConfig;

    public DishRepository(PostgresConfig postgresConfig) {
        this.postgresConfig = postgresConfig;
    }

    public List<Dish> findAll() {
        List<Dish> dishes = new ArrayList<>();
        try (Connection conn = postgresConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM dish")) {
            while (rs.next()) {
                dishes.add(map(rs, conn));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return dishes;
    }

    public Optional<Dish> findById(Integer id) {
        try (Connection conn = postgresConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM dish WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs, conn));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return Optional.empty();
    }

    private Dish map(ResultSet rs, Connection conn) throws SQLException {
        Dish d = new Dish();
        d.setId(rs.getInt("id"));
        d.setName(rs.getString("name"));
        d.setPrice(rs.getDouble("selling_price"));
        d.setDishType(DishTypeEnum.valueOf(rs.getString("dish_type").toUpperCase()));
        d.setDishIngredients(findIngredientsForDish(d, conn));
        return d;
    }

    private List<DishIngredient> findIngredientsForDish(Dish dish, Connection conn) throws SQLException {
        List<DishIngredient> list = new ArrayList<>();
        String sql = "SELECT di.*, i.name as ing_name, i.price as ing_price, i.category as ing_cat " +
                "FROM dishingredient di JOIN ingredient i ON di.id_ingredient = i.id WHERE di.id_dish = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dish.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ingredient ing = new Ingredient();
                    ing.setId(rs.getInt("id_ingredient"));
                    ing.setName(rs.getString("ing_name"));
                    ing.setPrice(rs.getDouble("ing_price"));
                    ing.setCategory(CategoryEnum.valueOf(rs.getString("ing_cat").toUpperCase()));

                    list.add(new DishIngredient(dish, ing, rs.getDouble("quantity_required"),
                            Unit.valueOf(rs.getString("unit").toUpperCase())));
                }
            }
        }
        return list;
    }
    public void updateIngredients(Integer dishId, List<Ingredient> newIngredients) {
        String deleteSql = "DELETE FROM dishingredient WHERE id_dish = ?";
        String insertSql = "INSERT INTO dishingredient (id_dish, id_ingredient, quantity_required, unit) VALUES (?, ?, ?, ?::unit_type)";

        try (Connection conn = postgresConfig.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // 1. Suppression
                try (PreparedStatement deletePs = conn.prepareStatement(deleteSql)) {
                    deletePs.setInt(1, dishId);
                    deletePs.executeUpdate();
                }
                try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                    for (Ingredient ing : newIngredients) {
                        insertPs.setInt(1, dishId);
                        insertPs.setInt(2, ing.getId());
                        insertPs.setDouble(3, 1.0);

                        insertPs.setString(4, "unit");

                        insertPs.addBatch();
                    }
                    insertPs.executeBatch();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur SQL lors du PUT : " + e.getMessage(), e);
        }
    }
}