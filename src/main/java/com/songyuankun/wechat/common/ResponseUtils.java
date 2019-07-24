package com.songyuankun.wechat.common;

/**
 * @author songyuankun
 */
public class ResponseUtils {

    public static <T> Response<T> success(T object) {
        return new Response<>(ResultEnums.SUCCESS.getCode(), ResultEnums.SUCCESS.getMessage(), object);
    }

    public static <T> Response<T> error(String message) {
        return new Response<>(ResultEnums.ERROR.getCode(), message, null);
    }
}