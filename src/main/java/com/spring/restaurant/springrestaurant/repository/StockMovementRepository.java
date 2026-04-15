package com.spring.restaurant.springrestaurant.repository;

import com.spring.restaurant.springrestaurant.config.PostgresConfig;
import com.spring.restaurant.springrestaurant.entity.enums.MovementTypeEnum;
import com.spring.restaurant.springrestaurant.entity.StockMovement;
import com.spring.restaurant.springrestaurant.entity.StockValue;
import com.spring.restaurant.springrestaurant.entity.enums.Unit;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StockMovementRepository {
    private final PostgresConfig postgresConfig;

    public StockMovementRepository(PostgresConfig postgresConfig) {
        this.postgresConfig = postgresConfig;
    }

    public List<StockMovement> findAllByIngredientId(Integer id) {
        List<StockMovement> stockMovementList = new ArrayList<>();
        String sql = """
                    SELECT id, quantity, unit, type, creation_datetime
                    FROM stockmovement
                    WHERE id_ingredient = ?;
                    """;

        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    StockMovement stockMovement = new StockMovement();
                    stockMovement.setId(resultSet.getInt("id"));

                    stockMovement.setType(MovementTypeEnum.valueOf(resultSet.getString("type").toUpperCase()));
                    stockMovement.setCreationDatetime(resultSet.getTimestamp("creation_datetime").toInstant());

                    StockValue stockValue = new StockValue();
                    stockValue.setQuantity(resultSet.getDouble("quantity"));
                    stockValue.setUnit(Unit.valueOf(resultSet.getString("unit").toUpperCase()));
                    stockMovement.setValue(stockValue);

                    stockMovementList.add(stockMovement);
                }
            }
            return stockMovementList;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des mouvements pour l'ingrédient " + id, e);
        }
    }
}