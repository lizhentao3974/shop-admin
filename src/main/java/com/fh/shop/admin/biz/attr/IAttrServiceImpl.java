package com.fh.shop.admin.biz.attr;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.attr.IAttrMapper;
import com.fh.shop.admin.mapper.attr.IAttrValueMapper;
import com.fh.shop.admin.param.attr.AttrUpdateParam;
import com.fh.shop.admin.po.attr.Attr;
import com.fh.shop.admin.po.attr.AttrValue;
import com.fh.shop.admin.vo.attr.AttrVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("attrService")
public class IAttrServiceImpl implements IAttrService {

    @Autowired
    private IAttrMapper attrMapper;
    @Autowired
    private IAttrValueMapper attrValueMapper;

    @Override
    public ServerResponse findAttr(Long id) {
        Attr attr = attrMapper.selectById(id);

        QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
        attrValueQueryWrapper.eq("attrId", id);
        List<AttrValue> attrValueList = attrValueMapper.selectList(attrValueQueryWrapper);

        AttrVo attrVo = new AttrVo();
        attrVo.setAttr(attr);
        attrVo.setAttrValueList(attrValueList);

        return ServerResponse.success(attrVo);
    }

    @Override
    public ServerResponse updateAttr(AttrUpdateParam attrUpdateParam) {
        Attr attr = new Attr();
        Long attrId = attrUpdateParam.getAttrId();
        Long typeId = attrUpdateParam.getTypeId();
        attr.setId(attrId);
        attr.setTypeId(typeId);
        attr.setAttrName(attrUpdateParam.getAttrName());
        String attrValues = attrUpdateParam.getAttrValues();
        attr.setAttrValue(attrValues);
        attrMapper.updateById(attr);


        QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
        attrValueQueryWrapper.eq("attrId", attrId);
        attrValueMapper.delete(attrValueQueryWrapper);

        if (StringUtils.isNotEmpty(attrValues)) {
            String[] attrValueArr = attrValues.split(",");
            for (String s : attrValueArr) {
                AttrValue attrValue = new AttrValue();
                attrValue.setTypeId(typeId);
                attrValue.setAttrId(attrId);
                attrValue.setAttrValue(s);
                attrValueMapper.insert(attrValue);
            }
        }

        return ServerResponse.success();
    }
}
