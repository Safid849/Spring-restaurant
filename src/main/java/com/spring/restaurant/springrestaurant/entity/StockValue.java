package com.spring.restaurant.springrestaurant.entity;

import com.spring.restaurant.springrestaurant.entity.enums.Unit;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class StockValue {
    private Double quantity;
    private Unit unit;

    public StockValue() {
    }
}