package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.constant.MessageConstant;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        
        Long userId = getCurrentUserId();
        shoppingCart.setUserId(userId);
        
        Long dishId = shoppingCartDTO.getDishId();
        if (dishId != null) {
            Dish dish = dishMapper.selectById(dishId);
            shoppingCart.setName(dish.getName());
            shoppingCart.setAmount(dish.getPrice());
            shoppingCart.setImage(dish.getImage());
        } else {
            Long setmealId = shoppingCartDTO.getSetmealId();
            Setmeal setmeal = setmealMapper.selectById(setmealId);
            shoppingCart.setName(setmeal.getName());
            shoppingCart.setAmount(setmeal.getPrice());
            shoppingCart.setImage(setmeal.getImage());
        }
        
        ShoppingCart exist = getOne(new LambdaQueryWrapper<ShoppingCart>()
                .eq(ShoppingCart::getUserId, userId)
                .eq(ShoppingCart::getDishId, dishId)
                .eq(ShoppingCart::getSetmealId, setmealId)
                .eq(ShoppingCart::getDishFlavor, shoppingCartDTO.getDishFlavor()));
        
        if (exist != null) {
            exist.setNumber(exist.getNumber() + 1);
            updateById(exist);
        } else {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            save(shoppingCart);
        }
    }

    @Override
    public java.util.List<ShoppingCart> showShoppingCart() {
        Long userId = getCurrentUserId();
        return list(new LambdaQueryWrapper<ShoppingCart>()
                .eq(ShoppingCart::getUserId, userId)
                .orderByDesc(ShoppingCart::getCreateTime));
    }

    @Override
    public void cleanShoppingCart() {
        Long userId = getCurrentUserId();
        remove(new LambdaQueryWrapper<ShoppingCart>()
                .eq(ShoppingCart::getUserId, userId));
    }

    @Override
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        Long userId = getCurrentUserId();
        ShoppingCart exist = getOne(new LambdaQueryWrapper<ShoppingCart>()
                .eq(ShoppingCart::getUserId, userId)
                .eq(ShoppingCart::getDishId, shoppingCartDTO.getDishId())
                .eq(ShoppingCart::getSetmealId, shoppingCartDTO.getSetmealId())
                .eq(ShoppingCart::getDishFlavor, shoppingCartDTO.getDishFlavor()));
        
        if (exist != null && exist.getNumber() > 1) {
            exist.setNumber(exist.getNumber() - 1);
            updateById(exist);
        } else if (exist != null && exist.getNumber() == 1) {
            removeById(exist.getId());
        }
    }

    private Long getCurrentUserId() {
        return 1L;
    }
}
