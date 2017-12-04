package com.showtime.xijing.common.exception;


public class BaseException extends RuntimeException {

    Integer code = 0;

    public BaseException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public BaseException(String msg) {
        super(msg);
    }

    public Integer getCode() {
        return code;
    }
}
