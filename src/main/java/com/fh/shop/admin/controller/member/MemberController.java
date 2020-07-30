package com.fh.shop.admin.controller.member;

import com.fh.shop.admin.biz.member.IMemberService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.param.member.MemberSearchParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/member")
@CrossOrigin("*")
public class MemberController {

    @Resource(name = "memberService")
    private IMemberService memberService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "member/list";
    }

    @GetMapping(value = "/findMemberPageList")
    @ResponseBody
    public DataTableResult findMemberPageList(MemberSearchParam memberSearchParam) {
        return memberService.findMemberPageList(memberSearchParam);
    }
}
