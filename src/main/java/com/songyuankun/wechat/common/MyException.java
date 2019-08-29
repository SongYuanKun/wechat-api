package com.songyuankun.wechat.common;

import com.songyuankun.wechat.enums.ErrorEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author songyuankun
 */

@Getter
@Setter
@ToString
public class MyException extends RuntimeException {

    private Integer code;
    private String message;


    public MyException(ErrorEnum eEnum, Throwable e) {
        super(eEnum.getMsg(), e);
        this.message = eEnum.getMsg();
        this.code = eEnum.getCode();
    }

    public MyException(ErrorEnum eEnum) {
        this.message = eEnum.getMsg();
        this.code = eEnum.getCode();
    }

    public MyException(String exception) {
        this.message = exception;
    }

}