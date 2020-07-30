package com.fh.shop.admin.common;

public enum ResponseEnum {
    SPEC_NAME_VALUE_SORT_NULL(2000, "规格值为空"),

    SKU_INFO_IS_Empty(3000, "sku的规格值信息为空"),

    USER_PASSWORD_IS_NOT(203, "密码错误"),

    LOGIN_ERROR_NOT_COUNT(204, "密码错误超过三次，账号锁定"),

    USER_IS_NOT(202, "用户不存在"),

    USERNAME_PASSWORD_IS_NULL(201, "用户名或者密码不能为空");


    private int code;

    private String msg;

    private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
