package com.fh.shop.admin.biz.area;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.area.Area;

import java.util.List;

public interface IAreaService {

    public ServerResponse queryAreaZtree();

    void addArea(Area area);

    ServerResponse deleteAreas(Long[] ids);

    Area findArea(Long id);

    ServerResponse updateArea(Area area);

    ServerResponse findAreaList(Long areaId);
}
