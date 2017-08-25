package com.imooc.exceptions;

import com.imooc.enums.ResultEnum;

/**
 * Created by CZ on 2017/8/25.
 */
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
