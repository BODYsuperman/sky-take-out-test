package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionException;
import com.sky.mapper.OrdersMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    @Transactional
    public void saveWithDishes(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setCreateTime(LocalDateTime.now());
        setmeal.setUpdateTime(LocalDateTime.now());
        setmeal.setStatus(StatusConstant.ENABLE);
        save(setmeal);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(dish -> dish.setSetmealId(setmeal.getId()));
            setmealDishMapper.saveBatch(setmealDishes);
        }
    }

    @Override
    public Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        Page<SetmealVO> page = new Page<>(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        // TODO: 实现复杂查询
        return page;
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            Long count = ordersMapper.selectCount(new LambdaQueryWrapper<com.sky.entity.Orders>()
                    .eq(com.sky.entity.Orders::getSetmealId, id));
            if (count > 0) {
                throw new DeletionException(MessageConstant.SETMEAL_BE_RELATED_BY_ORDER);
            }
        }
        
        removeBatchByIds(ids);
        
        ids.forEach(id -> setmealDishMapper.delete(new LambdaQueryWrapper<SetmealDish>()
                .eq(SetmealDish::getSetmealId, id)));
    }

    @Override
    public SetmealVO getByIdWithDishes(Long id) {
        Setmeal setmeal = getById(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        
        List<SetmealDish> setmealDishes = setmealDishMapper.selectList(new LambdaQueryWrapper<SetmealDish>()
                .eq(SetmealDish::getSetmealId, id));
        setmealVO.setSetmealDishes(setmealDishes);
        
        return setmealVO;
    }

    @Override
    @Transactional
    public void updateWithDishes(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setUpdateTime(LocalDateTime.now());
        updateById(setmeal);

        setmealDishMapper.delete(new LambdaQueryWrapper<SetmealDish>()
                .eq(SetmealDish::getSetmealId, setmealDTO.getId()));

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(dish -> dish.setSetmealId(setmealDTO.getId()));
            setmealDishMapper.saveBatch(setmealDishes);
        }
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Setmeal setmeal = getById(id);
        setmeal.setStatus(status);
        setmeal.setUpdateTime(LocalDateTime.now());
        updateById(setmeal);
    }

    @Override
    public List<Setmeal> list(Long categoryId) {
        return list(new LambdaQueryWrapper<Setmeal>()
                .eq(Setmeal::getCategoryId, categoryId)
                .eq(Setmeal::getStatus, StatusConstant.ENABLE)
                .orderByAsc(Setmeal::getCreateTime));
    }
}
