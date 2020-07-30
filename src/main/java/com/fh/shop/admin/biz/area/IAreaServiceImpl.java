package com.fh.shop.admin.biz.area;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.area.IAreaMapper;
import com.fh.shop.admin.po.area.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("areaService")
public class IAreaServiceImpl implements IAreaService {

    @Autowired
    private IAreaMapper areaMapper;

    @Override
    public ServerResponse queryAreaZtree() {
        List<Area> areaList = areaMapper.selectList(null);
        return ServerResponse.success(areaList);
    }

    @Override
    public void addArea(Area area) {
        areaMapper.insert(area);
    }

    @Override
    public ServerResponse deleteAreas(Long[] ids) {
        List<Long> longList = Arrays.asList(ids);
        areaMapper.deleteBatchIds(longList);
        return ServerResponse.success();
    }

    @Override
    public Area findArea(Long id) {
        Area area = areaMapper.selectById(id);
        return area;
    }

    @Override
    public ServerResponse updateArea(Area area) {
        areaMapper.updateById(area);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findAreaList(Long areaId) {
        QueryWrapper<Area> areaQueryWrapper = new QueryWrapper<>();
        areaQueryWrapper.in("fId", areaId);
        List<Area> areaList = areaMapper.selectList(areaQueryWrapper);
        return ServerResponse.success(areaList);
    }
}
