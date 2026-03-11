package com.sky.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    
    void saveWithDishes(SetmealDTO setmealDTO);
    
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
    
    void deleteBatch(List<Long> ids);
    
    SetmealVO getByIdWithDishes(Long id);
    
    void updateWithDishes(SetmealDTO setmealDTO);
    
    void startOrStop(Integer status, Long id);
    
    List<Setmeal> list(Long categoryId);
}
