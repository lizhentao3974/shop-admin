package com.fh.shop.admin.mapper.type;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.type.TypeSearchParam;
import com.fh.shop.admin.po.type.Type;

import java.util.List;

public interface ITypeMapper extends BaseMapper<Type> {
    Long findCount(TypeSearchParam typeSearchParam);

    List<Type> queryPageList(TypeSearchParam typeSearchParam);
}
