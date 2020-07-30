package com.fh.shop.admin.mapper.spec;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.spec.SpecSearchParam;
import com.fh.shop.admin.po.spec.SpecName;

import java.util.List;

public interface ISpecNameMapper extends BaseMapper<SpecName> {
    void addSpecName(SpecName specName1);

    Long findCount(SpecSearchParam specSearchParam);

    List<SpecName> queryPageList(SpecSearchParam specSearchParam);
}
