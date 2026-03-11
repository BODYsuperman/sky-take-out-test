package com.sky.interceptor;

import com.sky.constant.JwtClaimsConstant;
import com.sky.exception.JwtTokenInvalidException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            throw new JwtTokenInvalidException("令牌为空");
        }

        try {
            Claims claims = com.sky.utils.JwtUtil.parseJWT(token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            request.setAttribute("userId", userId);
        } catch (Exception e) {
            throw new JwtTokenInvalidException("令牌无效");
        }

        return true;
    }
}
