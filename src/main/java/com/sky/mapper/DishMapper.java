package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    
    Long countByCategoryId(Long categoryId);
    
    @Select("SELECT d.*, c.name as categoryName FROM dish d LEFT JOIN category c ON d.category_id = c.id " +
            "WHERE d.deleted = 0 ORDER BY d.create_time DESC")
    List<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);
}
