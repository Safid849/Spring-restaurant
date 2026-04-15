package com.spring.restaurant.springrestaurant.entity;

import com.spring.restaurant.springrestaurant.entity.enums.MovementTypeEnum;
import lombok.Data;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Data
@AllArgsConstructor
public class StockMovement {
    private Integer id;
    private MovementTypeEnum type;
    private Instant creationDatetime;
    private StockValue value;

    public StockMovement() {
    }
}