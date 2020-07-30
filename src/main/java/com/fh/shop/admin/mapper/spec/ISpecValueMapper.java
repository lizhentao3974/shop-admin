package com.fh.shop.admin.mapper.spec;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.po.spec.SpecValue;

public interface ISpecValueMapper extends BaseMapper<SpecValue> {
    void addSpecValue(SpecValue specValue1);
}
