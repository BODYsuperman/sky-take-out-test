package com.sky.controller;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关接口")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    @ApiOperation("新增套餐")
    public Result<String> save(@RequestBody SetmealDTO setmealDTO) {
        setmealService.saveWithDishes(setmealDTO);
        
        String key = "setmeal_" + setmealDTO.getCategoryId();
        redisTemplate.delete(key);
        
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult<SetmealVO>> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        // TODO: 实现分页查询
        return Result.success(new PageResult<>(0L, null));
    }

    @DeleteMapping
    @ApiOperation("套餐批量删除")
    public Result<String> delete(@RequestParam List<Long> ids) {
        setmealService.deleteBatch(ids);
        redisTemplate.delete("setmeal_*");
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据 id 查询套餐和套餐菜品关系")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        SetmealVO setmealVO = setmealService.getByIdWithDishes(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation("修改套餐")
    public Result<String> update(@RequestBody SetmealDTO setmealDTO) {
        setmealService.updateWithDishes(setmealDTO);
        redisTemplate.delete("setmeal_*");
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售停售")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        setmealService.startOrStop(status, id);
        redisTemplate.delete("setmeal_*");
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据分类 id 查询套餐列表")
    public Result<List<Setmeal>> list(Long categoryId) {
        List<Setmeal> list = setmealService.list(categoryId);
        return Result.success(list);
    }
}
