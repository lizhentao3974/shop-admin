package com.fh.shop.admin.param.user;

import com.fh.shop.admin.param.PageUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UserSearchParam extends PageUtil {

    private String userName;

    private String realName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxBirthday;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minBirthday;

    private Long provincialId;

    private Long cityId;

    private Long countyId;

    public Long getProvincialId() {
        return provincialId;
    }

    public UserSearchParam setProvincialId(Long provincialId) {
        this.provincialId = provincialId;
        return this;
    }

    public Long getCityId() {
        return cityId;
    }

    public UserSearchParam setCityId(Long cityId) {
        this.cityId = cityId;
        return this;
    }

    public Long getCountyId() {
        return countyId;
    }

    public UserSearchParam setCountyId(Long countyId) {
        this.countyId = countyId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserSearchParam setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public UserSearchParam setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public Date getMaxBirthday() {
        return maxBirthday;
    }

    public UserSearchParam setMaxBirthday(Date maxBirthday) {
        this.maxBirthday = maxBirthday;
        return this;
    }

    public Date getMinBirthday() {
        return minBirthday;
    }

    public UserSearchParam setMinBirthday(Date minBirthday) {
        this.minBirthday = minBirthday;
        return this;
    }
}
