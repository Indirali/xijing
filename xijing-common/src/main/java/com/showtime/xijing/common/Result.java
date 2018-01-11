package com.showtime.xijing.common;


import java.util.HashMap;
import java.util.Map;

/**
 * service层返回给外部的model
 */
public class Result<T extends Object> {

    public static Code code_success = new Code(200);
    public static Code code_fail = new Code(400);
    private static Code code_noAuth = new Code(401);
    private static Code code_noPermission = new Code(402);
    private static Code code_information = new Code(403);

    public static Result<? extends Object> success(Object data) {
        return new Result<>(code_success, data);
    }

    public static MapDataResult success() {
        return new MapDataResult(code_success);
    }

    public static Result<?> fail(String msg) {
        return new Result<>(code_fail, null, msg);
    }

    public static Result<?> noAuth() {
        return new Result<>(code_noAuth, null, "请先进行实名认证!");
    }

    public static Result<?> noPermission() {
        return new Result<>(code_noPermission, null, "请授权或登录账号");
    }

    public static Result<?> noInformation() {
        return new Result<>(code_information, null, "请添加联系方式，方便通知结果");
    }

    /**
     * 结果数据，错误时通常此字段为空
     */
    private T data;
    /**
     * 结果信息，一般主要是用来说明错误原因
     */
    private String message;
    private Integer code;

    /**
     * 一般返回结果为错误时没有数据 code为null则抛出NullPointerExcetion
     *
     * @param code
     */
    public Result(Code code) {
        if (null == code) {
            throw new NullPointerException("code can not be null");
        }
        this.code = code.getValue();
    }


    /**
     * 一般返回结果为正确时用此带数据的构造函数
     *
     * @param code
     * @param data
     */
    public Result(Code code, T data) {
        this(code);
        this.data = data;
    }

    public Result(Code code, T data, String message) {
        this(code, data);
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public void setCodeObj(Code code) {
        this.code = code.getValue();
    }

    public boolean hasError() {
        return this.code.equals(code_fail.getValue());
    }

    public boolean isSuccess() {
        return this.code.equals(code_success.getValue());
    }


    public static class Code {
        private int value;

        public Code(int value) {
            super();
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static class MapDataResult extends Result<Map> {

        public MapDataResult(Code code) {
            super(code);
            super.data = new HashMap();
        }

        public MapDataResult data(String key, Object obj) {
            super.data.put(key, obj);
            return this;
        }

        public MapDataResult data(Map map) {
            super.data.putAll(map);
            return this;
        }
    }
}
