package com.fh.shop.admin.biz.log;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.param.log.LogSearchParam;
import com.fh.shop.admin.po.log.Log;

import java.util.Map;

public interface ILogService {

    public void addLog(Log log);

    DataTableResult queryLogList(LogSearchParam logSearchParam);
}
