package com.ax.order;

import com.ax.common.util.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xing
 * @version 1.0.0
 * @ClassName OrderController.java
 * @description TODO
 * @createTime 2022年04月01日 19:03:00
 */

@RestController
@RequestMapping("/order")
public class OrderController {


    @GetMapping("/{id}")
    public Map test(@PathVariable String id) {

        Map map = new HashMap(2);
        map.put("orderId", id);
        return map;
    }
}
