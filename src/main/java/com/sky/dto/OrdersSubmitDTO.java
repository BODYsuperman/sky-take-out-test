package com.sky.dto;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrdersSubmitDTO implements Serializable {
    private Long addressBookId;
    private Integer payMethod;
    private String remark;
    private BigDecimal amount;
}
