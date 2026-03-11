package com.sky.dto;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrdersDTO implements Serializable {
    private Long id;
    private String number;
    private Integer status;
    private Long userId;
    private Long addressBookId;
    private LocalDateTime orderTime;
    private LocalDateTime checkoutTime;
    private Integer payMethod;
    private Integer payStatus;
    private String remark;
    private BigDecimal amount;
}
