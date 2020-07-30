package com.fh.shop.admin.vo.log;

public class LogVo {

    private Long id;

    private String userName;

    private String realName;

    private String info;

    private String insertTime;

    private Integer stutas;

    private String content;

    private String paramInfo;

    public String getContent() {
        return content;
    }

    public LogVo setContent(String content) {
        this.content = content;
        return this;
    }

    public String getParamInfo() {
        return paramInfo;
    }

    public LogVo setParamInfo(String paramInfo) {
        this.paramInfo = paramInfo;
        return this;
    }

    public Long getId() {
        return id;
    }

    public LogVo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public LogVo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public LogVo setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public LogVo setInfo(String info) {
        this.info = info;
        return this;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public LogVo setInsertTime(String insertTime) {
        this.insertTime = insertTime;
        return this;
    }

    public Integer getStutas() {
        return stutas;
    }

    public LogVo setStutas(Integer stutas) {
        this.stutas = stutas;
        return this;
    }
}
