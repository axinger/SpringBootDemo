package com.xing.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class BaseMongoEntity implements Serializable {

    @Id
    private String id;

    private Date createTime;

    private Date updateTime;

    private Integer isDeleted = 0;

    @Transient //被该注解标注的，将不会被录入到数据库中。只作为普通的javaBean属性
    private Map<String, Object> param = new HashMap<>();
}
