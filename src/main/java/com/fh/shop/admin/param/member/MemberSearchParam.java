package com.fh.shop.admin.param.member;

import com.fh.shop.admin.param.PageUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class MemberSearchParam extends PageUtil implements Serializable {

    private String memberName;

    private String realName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startBirthday;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endBirthday;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getStartBirthday() {
        return startBirthday;
    }

    public void setStartBirthday(Date startBirthday) {
        this.startBirthday = startBirthday;
    }

    public Date getEndBirthday() {
        return endBirthday;
    }

    public void setEndBirthday(Date endBirthday) {
        this.endBirthday = endBirthday;
    }
}
