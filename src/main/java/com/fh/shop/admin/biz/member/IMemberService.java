package com.fh.shop.admin.biz.member;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.param.member.MemberSearchParam;

public interface IMemberService {

    public DataTableResult findMemberPageList(MemberSearchParam memberSearchParam);
}
