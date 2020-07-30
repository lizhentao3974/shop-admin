package com.fh.shop.admin.biz.spec;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.spec.ISpecNameMapper;
import com.fh.shop.admin.mapper.spec.ISpecValueMapper;
import com.fh.shop.admin.param.spec.SpecAndValue;
import com.fh.shop.admin.param.spec.SpecSearchParam;
import com.fh.shop.admin.param.spec.SpecUpdateParam;
import com.fh.shop.admin.po.spec.SpecName;
import com.fh.shop.admin.po.spec.SpecValue;
import com.fh.shop.admin.vo.spec.SpecVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("specService")
public class ISpecServiceImpl implements ISpecService {
    @Autowired
    private ISpecNameMapper specNameMapper;

    @Autowired
    private ISpecValueMapper specValueMapper;

    @Override
    public void addSpecName(SpecName specName1) {
        specNameMapper.addSpecName(specName1);
    }

    @Override
    public void addSpecValue(SpecValue specValue1) {
        specValueMapper.addSpecValue(specValue1);
    }

    @Override
    public ServerResponse addSpec(SpecAndValue specAndValue) {
        String specNames = specAndValue.getSpecNames();
        String nameSorts = specAndValue.getNameSorts();
        String specValues = specAndValue.getSpecValues();
        if (StringUtils.isEmpty(specNames) || StringUtils.isEmpty(nameSorts) || StringUtils.isEmpty(specValues)) {
            return ServerResponse.error(ResponseEnum.SPEC_NAME_VALUE_SORT_NULL);
        }
        String[] specNameArr = specNames.split(",");
        String[] nameSortArr = nameSorts.split(",");
        String[] specValueArr = specValues.split(";");
        String specStr = "";
        for (int i = 0; i < specNameArr.length; i++) {
            SpecName specName = new SpecName();
            specName.setSpecName(specNameArr[i]);
            specName.setNameSort(Integer.valueOf(nameSortArr[i]));
            specNameMapper.insert(specName);
            Long id = specName.getId();
            String values = specValueArr[i];
            String[] valueArr = values.split(",");
            for (int j = 0; j < valueArr.length; j++) {
                String valueInfo = valueArr[j];
                String[] split = valueInfo.split("=");
                SpecValue specValueDB = new SpecValue();
                specValueDB.setSpecValue(split[0]);
                specValueDB.setValueSort(Integer.valueOf(split[1]));
                specValueDB.setSpecId(id);
                specValueMapper.insert(specValueDB);
            }

        }
        return ServerResponse.success();
    }

    @Override
    public DataTableResult queryPageList(SpecSearchParam specSearchParam) {
        Long count = specNameMapper.findCount(specSearchParam);

        List<SpecName> specNameList = specNameMapper.queryPageList(specSearchParam);
        return new DataTableResult(specSearchParam.getDraw(), count, count, specNameList);
    }

    @Override
    public ServerResponse deleteSpec(Long id) {
        QueryWrapper<SpecValue> specValueQueryWrapper = new QueryWrapper<>();
        specValueQueryWrapper.eq("specId", id);
        specValueMapper.delete(specValueQueryWrapper);

        specNameMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findSpecById(Long id) {
        SpecName specName = specNameMapper.selectById(id);

        QueryWrapper<SpecValue> specValueQueryWrapper = new QueryWrapper<>();
        specValueQueryWrapper.eq("specId", id);
        List<SpecValue> valueList = specValueMapper.selectList(specValueQueryWrapper);

        SpecVo specVo = new SpecVo();
        specVo.setSpecName(specName);
        specVo.setSpecValues(valueList);
        return ServerResponse.success(specVo);
    }

    @Override
    public ServerResponse updateSpec(SpecUpdateParam specUpdateParam) {
        SpecName specName = new SpecName();
        specName.setSpecName(specUpdateParam.getSpecName());
        specName.setNameSort(Integer.valueOf(specUpdateParam.getNameSort()));
        specName.setId(specUpdateParam.getId());
        specNameMapper.updateById(specName);

        Long id = specUpdateParam.getId();
        QueryWrapper<SpecValue> specValueQueryWrapper = new QueryWrapper<>();
        specValueQueryWrapper.eq("specId", id);
        specValueMapper.delete(specValueQueryWrapper);

        String specValues = specUpdateParam.getSpecValues();
        String[] valueArr = specValues.split(",");
        for (int j = 0; j < valueArr.length; j++) {
            String valueInfo = valueArr[j];
            String[] split = valueInfo.split("=");
            SpecValue specValueDB = new SpecValue();
            specValueDB.setSpecValue(split[0]);
            specValueDB.setValueSort(Integer.valueOf(split[1]));
            specValueDB.setSpecId(id);
            specValueMapper.insert(specValueDB);
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findSpecList() {
        List<SpecName> specNames = specNameMapper.selectList(null);
        return ServerResponse.success(specNames);
    }
}
