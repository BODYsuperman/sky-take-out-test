package com.sky.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class DishFlavorDTO implements Serializable {
    private Long id;
    private Long dishId;
    private String name;
    private String value;
}
