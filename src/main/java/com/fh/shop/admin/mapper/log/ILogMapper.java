package com.fh.shop.admin.mapper.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.log.LogSearchParam;
import com.fh.shop.admin.po.log.Log;

import java.util.List;

public interface ILogMapper extends BaseMapper<Log> {

    Long queryCount(LogSearchParam logSearchParam);

    List<Log> queryLogList(LogSearchParam logSearchParam);
}
