package com.fh.shop.admin.biz.type;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.type.TypeParam;
import com.fh.shop.admin.param.type.TypeSearchParam;
import com.fh.shop.admin.param.type.TypeUpdateParam;

public interface ITypeService {
    ServerResponse addType(TypeParam typeParam);

    DataTableResult queryPageList(TypeSearchParam typeSearchParam);

    ServerResponse deleteType(Long id);

    ServerResponse findTypeById(Long id);

    ServerResponse updateType(TypeUpdateParam typeUpdateParam);

    ServerResponse findTypeAll();

    ServerResponse findTypeRelate(Long typeId);
}
