package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setCreateTime(LocalDateTime.now());
        dish.setUpdateTime(LocalDateTime.now());
        dish.setStatus(StatusConstant.ENABLE);
        save(dish);

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(flavor -> flavor.setDishId(dish.getId()));
            dishFlavorMapper.saveBatch(flavors);
        }
    }

    @Override
    public Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        Page<DishVO> page = new Page<>(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        // TODO: 实现复杂查询
        return page;
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            Long count = setmealDishMapper.selectCount(new LambdaQueryWrapper<com.sky.entity.SetmealDish>()
                    .eq(com.sky.entity.SetmealDish::getDishId, id));
            if (count > 0) {
                throw new DeletionException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }
        }
        
        removeBatchByIds(ids);
        
        ids.forEach(id -> dishFlavorMapper.delete(new LambdaQueryWrapper<DishFlavor>()
                .eq(DishFlavor::getDishId, id)));
    }

    @Override
    public DishVO getByIdWithFlavor(Long id) {
        Dish dish = getById(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        
        List<DishFlavor> flavors = dishFlavorMapper.selectList(new LambdaQueryWrapper<DishFlavor>()
                .eq(DishFlavor::getDishId, id));
        dishVO.setFlavors(flavors);
        
        return dishVO;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setUpdateTime(LocalDateTime.now());
        updateById(dish);

        dishFlavorMapper.delete(new LambdaQueryWrapper<DishFlavor>()
                .eq(DishFlavor::getDishId, dishDTO.getId()));

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(flavor -> flavor.setDishId(dishDTO.getId()));
            dishFlavorMapper.saveBatch(flavors);
        }
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Dish dish = getById(id);
        dish.setStatus(status);
        dish.setUpdateTime(LocalDateTime.now());
        updateById(dish);
    }

    @Override
    public List<Dish> list(Long categoryId) {
        return list(new LambdaQueryWrapper<Dish>()
                .eq(Dish::getCategoryId, categoryId)
                .eq(Dish::getStatus, StatusConstant.ENABLE)
                .orderByAsc(Dish::getCreateTime));
    }
}
