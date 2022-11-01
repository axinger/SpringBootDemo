package com.axing.common.doc.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "axing.doc-info")
public class DocInfoProperties {

    /**
     * 标题
     */
    private String title = "阿星项目";

    /**
     * 描述
     */
    private String description = "阿星springboot开发项目";

    /**
     * 版本
     */
    private String version = "v0.0.1";

    /**
     * 网站描述
     */
    private String websiteName = "项目地址";

    /**
     * 网站url
     */
    private String websiteUrl = "https://github.com/axinger";

}