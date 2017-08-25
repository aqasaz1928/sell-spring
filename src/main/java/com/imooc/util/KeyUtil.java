package com.imooc.util;

import java.util.Random;

/**
 * Created by CZ on 2017/8/25.
 */
public class KeyUtil {

    /**
     * 生成唯一主键
     * 格式 时间+随机数
     */
    public static synchronized String createUniqueKey(){
        Random random = new Random();
        Integer randomInt = random.nextInt(900000)+100000;
        return  String.valueOf(System.currentTimeMillis()) + randomInt.toString();
    }
}
