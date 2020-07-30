package com.fh.shop.admin.biz.spec;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.spec.SpecAndValue;
import com.fh.shop.admin.param.spec.SpecSearchParam;
import com.fh.shop.admin.param.spec.SpecUpdateParam;
import com.fh.shop.admin.po.spec.SpecName;
import com.fh.shop.admin.po.spec.SpecValue;

public interface ISpecService {
    void addSpecName(SpecName specName1);

    void addSpecValue(SpecValue specValue1);

    ServerResponse addSpec(SpecAndValue specAndValue);

    DataTableResult queryPageList(SpecSearchParam specSearchParam);

    ServerResponse deleteSpec(Long id);

    ServerResponse findSpecById(Long id);

    ServerResponse updateSpec(SpecUpdateParam specUpdateParam);

    ServerResponse findSpecList();
}
