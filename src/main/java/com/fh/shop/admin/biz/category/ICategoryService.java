package com.fh.shop.admin.biz.category;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.category.UpdateCateParam;
import com.fh.shop.admin.po.category.Category;

public interface ICategoryService {

    ServerResponse findCateList();

    ServerResponse delCate(Long[] ids);

    ServerResponse addCate(Category category);

    ServerResponse find(Long id);

    ServerResponse update(UpdateCateParam cateParam);

    ServerResponse findCateById(Long id);
}
