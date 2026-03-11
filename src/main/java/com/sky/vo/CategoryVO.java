package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVO implements Serializable {
    private Long id;
    private Integer type;
    private String name;
    private Integer sort;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
