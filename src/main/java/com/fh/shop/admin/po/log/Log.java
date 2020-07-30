package com.fh.shop.admin.po.log;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {

    private Long id;

    private String userName;

    private String realName;

    private String info;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date insertTime;

    private Integer stutas;

    private String content;

    private String paramInfo;

    public String getParamInfo() {
        return paramInfo;
    }

    public Log setParamInfo(String paramInfo) {
        this.paramInfo = paramInfo;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Log setContent(String content) {
        this.content = content;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Log setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Log setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public Log setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public Log setInfo(String info) {
        this.info = info;
        return this;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public Log setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
        return this;
    }

    public Integer getStutas() {
        return stutas;
    }

    public Log setStutas(Integer stutas) {
        this.stutas = stutas;
        return this;
    }
}
