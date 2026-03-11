package com.sky.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    
    void saveWithFlavor(DishDTO dishDTO);
    
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);
    
    void deleteBatch(List<Long> ids);
    
    DishVO getByIdWithFlavor(Long id);
    
    void updateWithFlavor(DishDTO dishDTO);
    
    void startOrStop(Integer status, Long id);
    
    List<Dish> list(Long categoryId);
}
