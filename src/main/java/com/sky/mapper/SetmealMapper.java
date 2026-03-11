package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.Setmeal;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
    
    Long countByCategoryId(Long categoryId);
    
    @Select("SELECT s.*, c.name as categoryName FROM setmeal s LEFT JOIN category c ON s.category_id = c.id " +
            "WHERE s.deleted = 0 ORDER BY s.create_time DESC")
    List<SetmealVO> pageQuery();
}
