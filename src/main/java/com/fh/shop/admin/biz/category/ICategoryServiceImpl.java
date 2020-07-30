package com.fh.shop.admin.biz.category;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.common.SystemConstant;
import com.fh.shop.admin.mapper.category.ICategoryMapper;
import com.fh.shop.admin.param.category.UpdateCateParam;
import com.fh.shop.admin.po.category.Category;
import com.fh.shop.admin.util.RedisUtil;
import net.sf.json.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("categoryService")
public class ICategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryMapper categoryMapper;

    @Override
    public ServerResponse findCateList() {
        //先查询缓存中是否有数据
        String cateListJson = RedisUtil.get(SystemConstant.CATELIST_KEY);

        //如果有数据直接返回数据
        if (StringUtils.isNotEmpty(cateListJson)) {
            List<Category> categoryList = JSONObject.parseArray(cateListJson, Category.class);
            RedisUtil.expire(SystemConstant.CATELIST_KEY, SystemConstant.CATELIST_KEY_EXPIRE);
            return ServerResponse.success(categoryList);
        }

        //如果没有数据，就查询数据库，放入缓存中一份，然后返回数据
        List<Category> categories = categoryMapper.selectList(null);
        String cateJson = JSONObject.toJSONString(categories);
        RedisUtil.set(SystemConstant.CATELIST_KEY, cateJson);
        RedisUtil.setEx(SystemConstant.CATELIST_KEY, cateJson, SystemConstant.CATELIST_KEY_EXPIRE);
        return ServerResponse.success(categories);
    }

    @Override
    public ServerResponse delCate(Long[] ids) {
        RedisUtil.delete(SystemConstant.CATELIST_KEY);
        List<Long> longs = Arrays.asList(ids);
        categoryMapper.deleteBatchIds(longs);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse addCate(Category category) {
        RedisUtil.delete(SystemConstant.CATELIST_KEY);
        categoryMapper.insert(category);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse find(Long id) {
        Category category = categoryMapper.selectById(id);
        return ServerResponse.success(category);
    }

    @Override
    public ServerResponse update(UpdateCateParam cateParam) {
        RedisUtil.delete(SystemConstant.CATELIST_KEY);

        Category category = cateParam.getCategory();
        categoryMapper.updateById(category);


        //更新冗余字段
        String categoryName = category.getCategoryName();
        String oldCategoryName = category.getOldCategoryName();
        if (!categoryName.equals(oldCategoryName)) {
            categoryMapper.updateCateName(oldCategoryName, categoryName);
        }

        if (cateParam.getRelateFlag() == SystemConstant.RELATE_FLAG) {
            String ids = cateParam.getIds();
            if (StringUtils.isNotEmpty(ids)) {
                Category cate = new Category();
                cate.setTypeName(category.getTypeName());
                cate.setTypeId(category.getTypeId());

                QueryWrapper<Category> updateWrapper = new QueryWrapper<>();
                String[] idsArr = ids.split(",");
                List<Long> idsList = Arrays.stream(idsArr).map(x -> Long.parseLong(x)).collect(Collectors.toList());
                updateWrapper.in("id", idsList);
                categoryMapper.update(cate, updateWrapper);
            }

        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findCateById(Long id) {
        /*QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("fatherId",id);
        List<Category> categoryList = categoryMapper.selectList(categoryQueryWrapper);
        return ServerResponse.success(categoryList);*/

        String cateListJson = RedisUtil.get(SystemConstant.CATELIST_KEY);
        List<Category> categoryList = new ArrayList<>();
        if (StringUtils.isEmpty(cateListJson)) {
            //如果没有数据，就查询数据库，放入缓存中一份，然后返回数据
            categoryList = categoryMapper.selectList(null);
            //将java对象转为json字符串
            String cateJson = JSONObject.toJSONString(categoryList);
            //放入缓存一份
            RedisUtil.set(SystemConstant.CATELIST_KEY, cateJson);
            //设置过期时间
            RedisUtil.setEx(SystemConstant.CATELIST_KEY, cateJson, SystemConstant.CATELIST_KEY_EXPIRE);
        } else {
            categoryList = JSONObject.parseArray(cateListJson, Category.class);
            //续命
            RedisUtil.expire(SystemConstant.CATELIST_KEY, SystemConstant.CATELIST_KEY_EXPIRE);
        }

        //过滤数据
        List<Category> categories = categoryList.stream().filter(x -> x.getFatherId() == id).collect(Collectors.toList());
        return ServerResponse.success(categories);
    }
}
