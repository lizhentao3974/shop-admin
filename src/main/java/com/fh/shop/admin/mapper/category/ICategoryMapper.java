package com.fh.shop.admin.mapper.category;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.po.category.Category;
import org.apache.ibatis.annotations.Param;

public interface ICategoryMapper extends BaseMapper<Category> {
    void updateCateName(@Param("oldCategoryName") String oldCategoryName, @Param("categoryName") String categoryName);
}
