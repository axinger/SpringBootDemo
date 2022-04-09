package com.ax.test.hutool;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xing
 * @version 1.0.0
 * @ClassName TestHutool.java
 * @description TODO
 * @createTime 2022年04月10日 02:34:00
 */

public class TestHutool {

    @Test
    void test_http(){


        String url = "https://xxx/xx";//指定URL
        Map<String, Object> map = new HashMap<>();//存放参数
        map.put("A", 100);
        map.put("B", 200);
        HashMap<String, String> headers = new HashMap<>();//存放请求头，可以存放多个请求头
        headers.put("xxx", "xxx");
//发送get请求并接收响应数据

        HttpUtil.createGet(url).addHeaders(headers).form(map).execute().body();

    }

    @Test
    void test_uuid(){

        System.out.println("IdUtil.simpleUUID() = " + IdUtil.simpleUUID());

        System.out.println("IdUtil.fastUUID() = " + IdUtil.fastUUID());
    }
}
