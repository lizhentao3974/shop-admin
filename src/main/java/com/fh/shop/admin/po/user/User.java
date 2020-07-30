package com.fh.shop.admin.po.user;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Long id;

    private String userName;

    private String password;

    private String realName;

    private String userImage;

    private Long sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String telePhone;

    private String oldImgFile;

    private String email;

    private Long provincial;

    private Long city;

    private Long county;

    private String provincialName;

    private String cityName;

    private String countyName;

    private Boolean isLock;

    private Integer loginCount;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lockDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;

    public Boolean getLock() {
        return isLock;
    }

    public User setLock(Boolean lock) {
        isLock = lock;
        return this;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public User setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
        return this;
    }

    public Date getLockDate() {
        return lockDate;
    }

    public User setLockDate(Date lockDate) {
        this.lockDate = lockDate;
        return this;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public User setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
        return this;
    }

    public String getProvincialName() {
        return provincialName;
    }

    public User setProvincialName(String provincialName) {
        this.provincialName = provincialName;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public User setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getCountyName() {
        return countyName;
    }

    public User setCountyName(String countyName) {
        this.countyName = countyName;
        return this;
    }

    public Long getProvincial() {
        return provincial;
    }

    public User setProvincial(Long provincial) {
        this.provincial = provincial;
        return this;
    }

    public Long getCity() {
        return city;
    }

    public User setCity(Long city) {
        this.city = city;
        return this;
    }

    public Long getCounty() {
        return county;
    }

    public User setCounty(Long county) {
        this.county = county;
        return this;
    }

    public String getOldImgFile() {
        return oldImgFile;
    }

    public User setOldImgFile(String oldImgFile) {
        this.oldImgFile = oldImgFile;
        return this;
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public User setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getUserImage() {
        return userImage;
    }

    public User setUserImage(String userImage) {
        this.userImage = userImage;
        return this;
    }

    public Long getSex() {
        return sex;
    }

    public User setSex(Long sex) {
        this.sex = sex;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public User setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public User setTelePhone(String telePhone) {
        this.telePhone = telePhone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
}
