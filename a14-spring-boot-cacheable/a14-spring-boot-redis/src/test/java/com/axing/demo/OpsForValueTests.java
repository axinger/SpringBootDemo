package com.axing.demo;

import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.StrUtil;
import com.axing.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
public class OpsForValueTests {
    @Autowired
    private RedisTemplate<String, User> redisTemplateUser;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private User getUser(Integer id) {
        final User.Book book = User.Book.builder()
                .id(id)
                .name("海底两万里")
                .build();

        final User user = User.builder()
                .id(id)
                .name("jim")
                .age(21)
                .date(new Date())
                // .localDateTime(LocalDateTime.now())
                .books(List.of(book))
                .build();

        return user;
    }

    private String getUserKey(Integer id) {
        return StrUtil.format("test::value::{}::User", id);
    }

    @Test
    void opsForValue_set() {
        // redisTemplateUser.setValueSerializer(RedisSerializer.java());
        // redisTemplateUser.setValueSerializer(RedisSerializer.json());
        redisTemplateUser.opsForValue().set(getUserKey(1), getUser(1), 10, TimeUnit.MINUTES);
        System.out.println("opsForValue");
    }

    @Test
    void opsForValue_get() {
        // redisTemplateUser.setValueSerializer(RedisSerializer.java());
        // redisTemplateUser.setValueSerializer(RedisSerializer.json());
        User user = redisTemplateUser.opsForValue().get(getUserKey(1));
        System.out.println("user = " + user);
    }

    @Test
    void setIfAbsent() {
        // 当前key不存在，写入值, 并返回true; 当前key已经存在，不处理, 返回false;  Absent: 缺少的，
        Boolean ifAbsent = redisTemplateUser.opsForValue().setIfAbsent(getUserKey(1), getUser(1));
        System.out.println("ifAbsent = " + ifAbsent);
    }

    @Test
    void setIfPresent() {
        // 当前key已经存在，写入值, 并返回true; 当前key不存在，不处理, 返回false;  ;Present: 存在的
        Boolean ifPresent = redisTemplateUser.opsForValue().setIfPresent(getUserKey(1), getUser(1));
        System.out.println("ifPresent = " + ifPresent);
    }

    @Test
    void getAndSet() {
        // 获取原来key的value, 再将新的value写入
        User andSet = redisTemplateUser.opsForValue().getAndSet(getUserKey(1), getUser(1));
        log.info("andSet = {}", andSet);
    }


    @Test
    void hash(){
        String id = "jim";
        System.out.println("HashUtil.apHash(id) = " + HashUtil.apHash(id));
        System.out.println("HashUtil.additiveHash(id,1) = " + HashUtil.additiveHash(id, 1));
        System.out.println("HashUtil.elfHash(id) = " + HashUtil.elfHash(id));
        System.out.println("HashUtil.fnvHash(id) = " + HashUtil.fnvHash(id));
    }

    @Test
    void setBit(){
        String key = "bitmap";
        redisTemplate.opsForValue().setBit(key,0,true);
        redisTemplate.opsForValue().setBit(key,1,true);
        redisTemplate.opsForValue().setBit(key,4,true);
        redisTemplate.opsForValue().setBit(key,2,true);
        redisTemplate.opsForValue().setBit(key,5,true);

        System.out.println(redisTemplate.opsForValue().getBit(key,2));
        System.out.println(redisTemplate.opsForValue().getBit(key,3));
        System.out.println(redisTemplate.opsForValue().getBit(key,5));
    }

    @Test
    void setBit_has(){
        String key = "sign:"+LocalDate.now();
        String id = "jim";
        int offset = HashUtil.fnvHash(id);
        redisTemplate.opsForValue().setBit(key,offset,true);
        System.out.println(redisTemplate.opsForValue().getBit(key,offset));
    }

    @Test
    void setBit_has_get(){
        String key = "sign:"+LocalDate.now();
        String id = "jim";
        int offset = HashUtil.fnvHash(id);
        System.out.println(redisTemplate.opsForValue().getBit(key,offset));
    }
}
