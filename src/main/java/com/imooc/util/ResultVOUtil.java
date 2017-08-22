package com.imooc.util;

import com.imooc.vo.ResultVO;

/**
 * Created by CZ on 2017/8/22.
 */
public class ResultVOUtil {
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("请求成功");
        resultVO.setData(object);
        return resultVO;
    }
    public static ResultVO success() {
        return success(null);
    }
    public static ResultVO error(Integer code, String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(null);
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return  resultVO;
    }
}

