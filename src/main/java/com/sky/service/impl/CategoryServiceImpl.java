package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.constant.MessageConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        save(category);
    }

    @Override
    public Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        Page<Category> page = new Page<>(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(categoryPageQueryDTO.getName())) {
            wrapper.like(Category::getName, categoryPageQueryDTO.getName());
        }
        if (categoryPageQueryDTO.getType() != null) {
            wrapper.eq(Category::getType, categoryPageQueryDTO.getType());
        }
        wrapper.orderByAsc(Category::getSort);
        
        return page(page, wrapper);
    }

    @Override
    public void deleteById(Long id) {
        Long dishCount = dishMapper.countByCategoryId(id);
        if (dishCount > 0) {
            throw new DeletionException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        
        Long setmealCount = setmealMapper.countByCategoryId(id);
        if (setmealCount > 0) {
            throw new DeletionException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        
        removeById(id);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setUpdateTime(LocalDateTime.now());
        updateById(category);
    }

    @Override
    public java.util.List<Category> list(Integer type) {
        return list(new LambdaQueryWrapper<Category>()
                .eq(Category::getType, type)
                .orderByAsc(Category::getSort));
    }
}
