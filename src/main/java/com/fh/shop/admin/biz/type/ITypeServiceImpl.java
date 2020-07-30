package com.fh.shop.admin.biz.type;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.common.SystemConstant;
import com.fh.shop.admin.mapper.attr.IAttrMapper;
import com.fh.shop.admin.mapper.attr.IAttrValueMapper;
import com.fh.shop.admin.mapper.brand.IBrandMapper;
import com.fh.shop.admin.mapper.category.ICategoryMapper;
import com.fh.shop.admin.mapper.spec.ISpecNameMapper;
import com.fh.shop.admin.mapper.spec.ISpecValueMapper;
import com.fh.shop.admin.mapper.type.ITypeBrandMapper;
import com.fh.shop.admin.mapper.type.ITypeMapper;
import com.fh.shop.admin.mapper.type.ITypeSpecMapper;
import com.fh.shop.admin.param.type.TypeParam;
import com.fh.shop.admin.param.type.TypeSearchParam;
import com.fh.shop.admin.param.type.TypeUpdateParam;
import com.fh.shop.admin.po.attr.Attr;
import com.fh.shop.admin.po.attr.AttrValue;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.po.category.Category;
import com.fh.shop.admin.po.spec.SpecName;
import com.fh.shop.admin.po.spec.SpecValue;
import com.fh.shop.admin.po.type.Type;
import com.fh.shop.admin.po.type.TypeBrand;
import com.fh.shop.admin.po.type.TypeSpec;
import com.fh.shop.admin.util.RedisUtil;
import com.fh.shop.admin.vo.attr.AttrVo;
import com.fh.shop.admin.vo.spec.SpecVo;
import com.fh.shop.admin.vo.type.TypeRelateVo;
import com.fh.shop.admin.vo.type.TypeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("typeService")
public class ITypeServiceImpl implements ITypeService {

    @Autowired
    private ITypeMapper typeMapper;

    @Autowired
    private ITypeBrandMapper typeBrandMapper;

    @Autowired
    private ITypeSpecMapper typeSpecMapper;

    @Autowired
    private IAttrMapper attrMapper;

    @Autowired
    private IAttrValueMapper attrValueMapper;

    @Autowired
    private IBrandMapper brandMapper;

    @Autowired
    private ISpecNameMapper specNameMapper;

    @Autowired
    private ISpecValueMapper specValueMapper;

    @Autowired
    private ICategoryMapper categoryMapper;


    @Override
    public ServerResponse addType(TypeParam typeParam) {
        String typeName = typeParam.getTypeName();
        String brandIds = typeParam.getBrandIds();
        String specIds = typeParam.getSpecIds();
        Type type = new Type();
        type.setTypeName(typeName);
        typeMapper.insert(type);
        Long typeId = type.getId();

        if (StringUtils.isNotEmpty(brandIds)) {
            String[] brandArr = brandIds.split(",");
            for (String brandId : brandArr) {
                TypeBrand typeBrand = new TypeBrand();
                typeBrand.setTypeId(typeId);
                typeBrand.setBrandId(Long.valueOf(brandId));
                typeBrandMapper.insert(typeBrand);
            }
        }

        if (StringUtils.isNotEmpty(specIds)) {
            String[] specArr = specIds.split(",");
            for (String specId : specArr) {
                TypeSpec typeSpec = new TypeSpec();
                typeSpec.setTypeId(typeId);
                typeSpec.setSpecId(Long.valueOf(specId));
                typeSpecMapper.insert(typeSpec);
            }
        }

        String attrNames = typeParam.getAttrNames();
        String attrValues = typeParam.getAttrValues();
        if (StringUtils.isNotEmpty(attrNames) && StringUtils.isNotEmpty(attrValues)) {
            String[] attrNameArr = attrNames.split(",");
            String[] attrValueArr = attrValues.split(";");
            for (int i = 0; i < attrNameArr.length; i++) {
                String attrNameInfo = attrNameArr[i];
                String attrInfoArr = attrValueArr[i];
                Attr attr = new Attr();
                attr.setAttrName(attrNameInfo);
                attr.setTypeId(typeId);
                attr.setAttrValue(attrInfoArr);
                attrMapper.insert(attr);
                Long attrId = attr.getId();

                String[] values = attrInfoArr.split(",");
                for (int j = 0; j < values.length; j++) {
                    String info = values[j];
                    AttrValue attrValue = new AttrValue();
                    attrValue.setAttrValue(info);
                    attrValue.setAttrId(attrId);
                    attrValue.setTypeId(typeId);
                    attrValueMapper.insert(attrValue);
                }
            }
        }
        return ServerResponse.success();
    }

    @Override
    public DataTableResult queryPageList(TypeSearchParam typeSearchParam) {
        Long count = typeMapper.findCount(typeSearchParam);

        List<Type> typeList = typeMapper.queryPageList(typeSearchParam);
        return new DataTableResult(typeSearchParam.getDraw(), count, count, typeList);
    }

    @Override
    public ServerResponse deleteType(Long id) {
        //删除类型
        typeMapper.deleteById(id);

        //删除类型品牌关联表
        QueryWrapper<TypeBrand> typeBrandQueryWrapper = new QueryWrapper<>();
        typeBrandQueryWrapper.eq("typeId", id);
        typeBrandMapper.delete(typeBrandQueryWrapper);

        //删除类型规格关联表
        QueryWrapper<TypeSpec> typeSpecQueryWrapper = new QueryWrapper<>();
        typeSpecQueryWrapper.eq("typeId", id);
        typeSpecMapper.delete(typeSpecQueryWrapper);

        //删除属性表
        QueryWrapper<Attr> attrQueryWrapper = new QueryWrapper<>();
        attrQueryWrapper.eq("typeId", id);
        attrMapper.delete(attrQueryWrapper);

        //删除属性值
        QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
        attrValueQueryWrapper.eq("typeId", id);
        attrValueMapper.delete(attrValueQueryWrapper);

        return ServerResponse.success();
    }

    @Override
    public ServerResponse findTypeById(Long id) {

        TypeVo typeVo = new TypeVo();
        Type type = typeMapper.selectById(id);

        QueryWrapper<TypeBrand> typeBrandQueryWrapper = new QueryWrapper<>();
        typeBrandQueryWrapper.eq("typeId", id);
        List<TypeBrand> typeBrands = typeBrandMapper.selectList(typeBrandQueryWrapper);
        List<Long> brandIdList = new ArrayList<>();
        for (TypeBrand typeBrand : typeBrands) {
            brandIdList.add(typeBrand.getBrandId());
        }

        QueryWrapper<TypeSpec> typeSpecQueryWrapper = new QueryWrapper<>();
        typeSpecQueryWrapper.eq("typeId", id);
        List<TypeSpec> typeSpecs = typeSpecMapper.selectList(typeSpecQueryWrapper);
        List<Long> specIdList = new ArrayList<>();
        for (TypeSpec typeSpec : typeSpecs) {
            specIdList.add(typeSpec.getSpecId());
        }

        QueryWrapper<Attr> attrQueryWrapper = new QueryWrapper<>();
        attrQueryWrapper.eq("typeId", id);
        List<Attr> attrs = attrMapper.selectList(attrQueryWrapper);

        typeVo.setType(type);
        typeVo.setSpecIdList(specIdList);
        typeVo.setBrandIdList(brandIdList);
        typeVo.setAttrList(attrs);

        return ServerResponse.success(typeVo);
    }

    @Override
    public ServerResponse updateType(TypeUpdateParam typeUpdateParam) {
        //修改类型名
        Long typeId = typeUpdateParam.getTypeId();
        Type type = new Type();
        type.setId(typeId);
        String typeName = typeUpdateParam.getTypeName();
        type.setTypeName(typeName);
        typeMapper.updateById(type);

        //更新对应表中的冗余字段
        Category category = new Category();
        category.setTypeName(typeName);

        //清楚分类缓存
        RedisUtil.delete(SystemConstant.CATELIST_KEY);

        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("typeId", typeId);
        categoryMapper.update(category, categoryQueryWrapper);

        //修改类型规格关联表
        QueryWrapper<TypeSpec> typeSpecQueryWrapper = new QueryWrapper<>();
        typeSpecQueryWrapper.eq("typeId", typeId);
        typeSpecMapper.delete(typeSpecQueryWrapper);
        String specIds = typeUpdateParam.getSpecIds();
        if (StringUtils.isNotEmpty(specIds)) {
            String[] specArr = specIds.split(",");
            for (String specId : specArr) {
                TypeSpec typeSpec = new TypeSpec();
                typeSpec.setTypeId(typeId);
                typeSpec.setSpecId(Long.valueOf(specId));
                typeSpecMapper.insert(typeSpec);
            }
        }
        //修改类型品牌关联表
        QueryWrapper<TypeBrand> typeBrandQueryWrapper = new QueryWrapper<>();
        typeBrandQueryWrapper.eq("typeId", typeId);
        typeBrandMapper.delete(typeBrandQueryWrapper);
        String brandIds = typeUpdateParam.getBrandIds();
        if (StringUtils.isNotEmpty(brandIds)) {
            String[] brandArr = brandIds.split(",");
            for (String brandId : brandArr) {
                TypeBrand typeBrand = new TypeBrand();
                typeBrand.setTypeId(typeId);
                typeBrand.setBrandId(Long.valueOf(brandId));
                typeBrandMapper.insert(typeBrand);
            }
        }
        //修改属性名表
        QueryWrapper<Attr> attrQueryWrapper = new QueryWrapper<>();
        attrQueryWrapper.eq("typeId", typeId);
        attrMapper.delete(attrQueryWrapper);
        //修改属性值表
        QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
        attrValueQueryWrapper.eq("typeId", typeId);
        attrValueMapper.delete(attrValueQueryWrapper);

        String attrNames = typeUpdateParam.getAttrNames();
        String attrValues = typeUpdateParam.getAttrValues();
        if (StringUtils.isNotEmpty(attrNames) && StringUtils.isNotEmpty(attrValues)) {
            String[] attrNameArr = attrNames.split(",");
            String[] attrValueArr = attrValues.split(";");
            for (int i = 0; i < attrNameArr.length; i++) {
                String attrNameInfo = attrNameArr[i];
                String attrInfoArr = attrValueArr[i];
                Attr attr = new Attr();
                attr.setAttrName(attrNameInfo);
                attr.setTypeId(typeId);
                attr.setAttrValue(attrInfoArr);
                attrMapper.insert(attr);
                Long attrId = attr.getId();

                String[] values = attrInfoArr.split(",");
                for (int j = 0; j < values.length; j++) {
                    String info = values[j];
                    AttrValue attrValue = new AttrValue();
                    attrValue.setAttrValue(info);
                    attrValue.setAttrId(attrId);
                    attrValue.setTypeId(typeId);
                    attrValueMapper.insert(attrValue);
                }
            }
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findTypeAll() {
        List<Type> typeList = typeMapper.selectList(null);
        return ServerResponse.success(typeList);
    }

    @Override
    public ServerResponse findTypeRelate(Long typeId) {
        //获取类型对应的品牌列表
        QueryWrapper<TypeBrand> typeBrandQueryWrapper = new QueryWrapper<>();
        typeBrandQueryWrapper.eq("typeId", typeId);
        List<TypeBrand> typeBrandList = typeBrandMapper.selectList(typeBrandQueryWrapper);
        List<Long> brandIds = typeBrandList.stream().map(x -> x.getBrandId()).collect(Collectors.toList());
        List<Brand> brandList = brandMapper.selectBatchIds(brandIds);

        //获取类型对应的属性列表
        List<AttrVo> attrVoList = new ArrayList<>();
        //根据类型Id获取属性列表
        QueryWrapper<Attr> attrQueryWrapper = new QueryWrapper<>();
        attrQueryWrapper.eq("typeId", typeId);
        List<Attr> attrList = attrMapper.selectList(attrQueryWrapper);
        //根据类型Id获取属性值列表
        QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
        attrValueQueryWrapper.eq("typeId", typeId);
        List<AttrValue> attrValueList = attrValueMapper.selectList(attrValueQueryWrapper);
        attrList.forEach(x -> {
            AttrVo attrVo = new AttrVo();
            attrVo.setAttr(x);
            List<AttrValue> attrValues = attrValueList.stream().filter(y -> y.getAttrId() == x.getId()).collect(Collectors.toList());
            attrVo.setAttrValueList(attrValues);
            attrVoList.add(attrVo);
        });


        //获取类型对应的规格列表
        List<SpecVo> specVoList = new ArrayList<>();

        QueryWrapper<TypeSpec> typeSpecQueryWrapper = new QueryWrapper<>();
        typeSpecQueryWrapper.eq("typeId", typeId);
        List<TypeSpec> typeSpecs = typeSpecMapper.selectList(typeSpecQueryWrapper);
        List<Long> specIds = typeSpecs.stream().map(x -> x.getSpecId()).collect(Collectors.toList());

        QueryWrapper<SpecName> specNameQueryWrapper = new QueryWrapper<>();
        specNameQueryWrapper.in("id", specIds);
        specNameQueryWrapper.orderByAsc("nameSort");
        List<SpecName> specNameList = specNameMapper.selectList(specNameQueryWrapper);

        QueryWrapper<SpecValue> specValueQueryWrapper = new QueryWrapper<>();
        specValueQueryWrapper.in("specId", specIds);
        specValueQueryWrapper.orderByAsc("valueSort");
        List<SpecValue> specValueList = specValueMapper.selectList(specValueQueryWrapper);

        specNameList.forEach(x -> {
            SpecVo specVo = new SpecVo();
            specVo.setSpecName(x);
            specVo.setSpecValues(specValueList.stream().filter(y -> y.getSpecId() == x.getId()).collect(Collectors.toList()));
            specVoList.add(specVo);
        });

        TypeRelateVo typeRelateVo = new TypeRelateVo();
        typeRelateVo.setBrandList(brandList);
        typeRelateVo.setAttrVoList(attrVoList);
        typeRelateVo.setSpecVoList(specVoList);
        return ServerResponse.success(typeRelateVo);
    }
}
