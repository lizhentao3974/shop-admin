package com.fh.shop.admin.biz.member;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.mapper.member.IMemberMapper;
import com.fh.shop.admin.param.member.MemberSearchParam;
import com.fh.shop.admin.po.member.Member;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.vo.member.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("memberService")
public class IMemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberMapper memberMapper;

    @Override
    public DataTableResult findMemberPageList(MemberSearchParam memberSearchParam) {
        Long count = memberMapper.findMemberCount(memberSearchParam);

        List<Member> memberList = memberMapper.findMemberPageList(memberSearchParam);

        List<MemberVo> memberVoList = memberList.stream().map(x -> {
            MemberVo memberVo = new MemberVo();
            memberVo.setId(x.getId());
            memberVo.setMemberName(x.getMemberName());
            memberVo.setRealName(x.getRealName());
            memberVo.setAreaName(x.getAreaName());
            memberVo.setMail(x.getMail());
            memberVo.setPhone(x.getPhone());
            memberVo.setBirthday(DateUtil.date2str(x.getBirthday(), DateUtil.Y_M_D));

            return memberVo;

        }).collect(Collectors.toList());
        return new DataTableResult(memberSearchParam.getDraw(), count, count, memberVoList);
    }
}
