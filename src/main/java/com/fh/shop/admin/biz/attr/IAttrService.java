package com.fh.shop.admin.biz.attr;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.attr.AttrUpdateParam;

public interface IAttrService {

    ServerResponse findAttr(Long id);

    ServerResponse updateAttr(AttrUpdateParam attrUpdateParam);
}
