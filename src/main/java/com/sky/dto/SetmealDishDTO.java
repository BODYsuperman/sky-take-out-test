package com.sky.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class SetmealDishDTO implements Serializable {
    private Long id;
    private Long setmealId;
    private Long dishId;
    private String name;
    private Integer copies;
}
