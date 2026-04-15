package com.spring.restaurant.springrestaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.restaurant.springrestaurant.entity.enums.CategoryEnum;
import com.spring.restaurant.springrestaurant.entity.enums.MovementTypeEnum;
import com.spring.restaurant.springrestaurant.entity.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;


@Data
@AllArgsConstructor
public class Ingredient {
    private Integer id;
    private String name;
    private CategoryEnum category;
    private Double price;
    private List<StockMovement> stockMovementList;

    public Ingredient() {
    }

    @JsonIgnore
    public List<StockMovement> getStockMovementList() { return stockMovementList; }
    public void setStockMovementList(List<StockMovement> stockMovementList) { this.stockMovementList = stockMovementList; }

    public StockValue getStockValueAt(Instant t, Unit unit) {
        if (stockMovementList == null || stockMovementList.isEmpty()) {
            return new StockValue(0.0, unit);
        }

        Map<Unit, List<StockMovement>> unitSet = stockMovementList.stream()
                .collect(Collectors.groupingBy(sm -> sm.getValue().getUnit()));

        if (unitSet.keySet().size() > 1) {
            throw new RuntimeException("Multiple units found and not handled for conversion");
        }

        List<StockMovement> stockMovements = stockMovementList.stream()
                .filter(sm -> !sm.getCreationDatetime().isAfter(t))
                .toList();

        double movementIn = stockMovements.stream()
                .filter(sm -> sm.getType().equals(MovementTypeEnum.IN))
                .flatMapToDouble(sm -> DoubleStream.of(sm.getValue().getQuantity()))
                .sum();

        double movementOut = stockMovements.stream()
                .filter(sm -> sm.getType().equals(MovementTypeEnum.OUT))
                .flatMapToDouble(sm -> DoubleStream.of(sm.getValue().getQuantity()))
                .sum();

        StockValue stockValue = new StockValue();
        stockValue.setQuantity(movementIn - movementOut);

        Unit detectedUnit = unitSet.keySet().stream().findFirst().orElse(unit);
        stockValue.setUnit(detectedUnit);

        return stockValue;
    }
}