package com.sky.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "地址簿相关接口")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/list")
    @ApiOperation("查询所有地址")
    public Result<List<AddressBook>> list(AddressBook addressBook) {
        List<AddressBook> list = addressBookService.list(addressBook);
        return Result.success(list);
    }

    @PostMapping
    @ApiOperation("新增地址")
    public Result<String> save(@RequestBody AddressBook addressBook) {
        addressBookService.save(addressBook);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据 id 查询地址")
    public Result<AddressBook> getById(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    @PutMapping
    @ApiOperation("修改地址")
    public Result<String> update(@RequestBody AddressBook addressBook) {
        addressBookService.update(addressBook);
        return Result.success();
    }

    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result<String> setDefault(@RequestBody AddressBook addressBook) {
        addressBookService.setDefault(addressBook);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("删除地址")
    public Result<String> deleteById(@RequestParam Long id) {
        addressBookService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> getDefault() {
        Long userId = 1L;
        AddressBook addressBook = getOne(new LambdaQueryWrapper<AddressBook>()
                .eq(AddressBook::getUserId, userId)
                .eq(AddressBook::getIsDefault, 1));
        return Result.success(addressBook);
    }
}
