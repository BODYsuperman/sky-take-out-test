package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

    @Override
    public List<AddressBook> list(AddressBook addressBook) {
        Long userId = getCurrentUserId();
        return list(new LambdaQueryWrapper<AddressBook>()
                .eq(AddressBook::getUserId, userId)
                .eq(addressBook.getPhone() != null, AddressBook::getPhone, addressBook.getPhone())
                .orderByDesc(AddressBook::getCreateTime));
    }

    @Override
    public void save(AddressBook addressBook) {
        Long userId = getCurrentUserId();
        addressBook.setUserId(userId);
        addressBook.setCreateTime(LocalDateTime.now());
        addressBook.setUpdateTime(LocalDateTime.now());
        save(addressBook);
    }

    @Override
    public AddressBook getById(Long id) {
        return getById(id);
    }

    @Override
    public void update(AddressBook addressBook) {
        addressBook.setUpdateTime(LocalDateTime.now());
        updateById(addressBook);
    }

    @Override
    @Transactional
    public void setDefault(AddressBook addressBook) {
        Long userId = getCurrentUserId();
        
        update(new LambdaQueryWrapper<AddressBook>()
                .set(AddressBook::getIsDefault, 0)
                .eq(AddressBook::getUserId, userId));
        
        update(new LambdaQueryWrapper<AddressBook>()
                .set(AddressBook::getIsDefault, 1)
                .eq(AddressBook::getId, addressBook.getId()));
    }

    @Override
    public void deleteById(Long id) {
        removeById(id);
    }

    private Long getCurrentUserId() {
        return 1L;
    }
}
