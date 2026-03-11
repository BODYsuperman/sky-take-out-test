package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.UserNotFoundException;
import com.sky.mapper.UserMapper;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Value("${sky.wechat.appid}")
    private String appid;

    @Value("${sky.wechat.secret}")
    private String secret;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserLoginVO wxLogin(UserLoginDTO userLoginDTO) {
        String code = userLoginDTO.getCode();

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", appid);
        paramMap.put("secret", secret);
        paramMap.put("js_code", code);
        paramMap.put("grant_type", "authorization_code");

        String json = HttpClientUtil.doGet(WX_LOGIN, paramMap);
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");

        if (openid == null) {
            throw new UserNotFoundException(MessageConstant.USER_IS_NOT_EXIST);
        }

        User user = userMapper.getByOpenid(openid);

        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        String token = JwtUtil.createJWT(user.getId(), user.getName());

        return UserLoginVO.builder()
                .id(user.getId())
                .openid(openid)
                .token(token)
                .build();
    }

    @Override
    public User getById(Long id) {
        return getById(id);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        updateById(user);
    }
}
