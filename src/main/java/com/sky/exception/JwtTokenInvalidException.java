package com.sky.exception;

public class JwtTokenInvalidException extends BaseException {
    public JwtTokenInvalidException(String msg) {
        super(msg);
    }
}
