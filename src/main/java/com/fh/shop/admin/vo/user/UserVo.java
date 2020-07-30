package com.fh.shop.admin.vo.user;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UserVo {

    private Long id;

    private String userName;

    private String password;

    private String realName;

    private String userImage;

    private Long sex;

    private String birthday;

    private String telePhone;

    private String email;

    private String oldImgFile;

    private String areaName;

    private Long provincial;

    private Long city;

    private Long county;

    public Long getProvincial() {
        return provincial;
    }

    public UserVo setProvincial(Long provincial) {
        this.provincial = provincial;
        return this;
    }

    public Long getCity() {
        return city;
    }

    public UserVo setCity(Long city) {
        this.city = city;
        return this;
    }

    public Long getCounty() {
        return county;
    }

    public UserVo setCounty(Long county) {
        this.county = county;
        return this;
    }

    public String getAreaName() {
        return areaName;
    }

    public UserVo setAreaName(String areaName) {
        this.areaName = areaName;
        return this;
    }

    public String getOldImgFile() {
        return oldImgFile;
    }

    public UserVo setOldImgFile(String oldImgFile) {
        this.oldImgFile = oldImgFile;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserVo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserVo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserVo setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public UserVo setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getUserImage() {
        return userImage;
    }

    public UserVo setUserImage(String userImage) {
        this.userImage = userImage;
        return this;
    }

    public Long getSex() {
        return sex;
    }

    public UserVo setSex(Long sex) {
        this.sex = sex;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public UserVo setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public UserVo setTelePhone(String telePhone) {
        this.telePhone = telePhone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserVo setEmail(String email) {
        this.email = email;
        return this;
    }
}
