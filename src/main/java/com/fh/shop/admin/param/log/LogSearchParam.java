package com.fh.shop.admin.param.log;

import com.fh.shop.admin.param.PageUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class LogSearchParam extends PageUtil {
    private String userName;

    private String realName;

    private String info;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date minDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maxDate;

    private Integer stutas;

    public String getUserName() {
        return userName;
    }

    public LogSearchParam setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public LogSearchParam setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public LogSearchParam setInfo(String info) {
        this.info = info;
        return this;
    }

    public Date getMinDate() {
        return minDate;
    }

    public LogSearchParam setMinDate(Date minDate) {
        this.minDate = minDate;
        return this;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public LogSearchParam setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
        return this;
    }

    public Integer getStutas() {
        return stutas;
    }

    public LogSearchParam setStutas(Integer stutas) {
        this.stutas = stutas;
        return this;
    }
}
