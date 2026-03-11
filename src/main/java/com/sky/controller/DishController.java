package com.sky.controller;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result<String> save(@RequestBody DishDTO dishDTO) {
        dishService.saveWithFlavor(dishDTO);
        
        String key = "dish_" + dishDTO.getCategoryId();
        redisTemplate.delete(key);
        
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult<DishVO>> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        // TODO: 实现分页查询
        return Result.success(new PageResult<>(0L, null));
    }

    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result<String> delete(@RequestParam List<Long> ids) {
        dishService.deleteBatch(ids);
        redisTemplate.delete("dish_*");
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据 id 查询菜品和口味")
    public Result<DishVO> getById(@PathVariable Long id) {
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("修改菜品")
    public Result<String> update(@RequestBody DishDTO dishDTO) {
        dishService.updateWithFlavor(dishDTO);
        redisTemplate.delete("dish_*");
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售停售")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        dishService.startOrStop(status, id);
        redisTemplate.delete("dish_*");
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据分类 id 查询菜品列表")
    public Result<List<Dish>> list(Long categoryId) {
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }
}
