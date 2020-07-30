package com.fh.shop.admin.biz.log;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.mapper.log.ILogMapper;
import com.fh.shop.admin.param.log.LogSearchParam;
import com.fh.shop.admin.po.log.Log;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.vo.log.LogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("logService")
public class ILogServiceImpl implements ILogService {

    @Autowired
    private ILogMapper logMapper;

    @Override
    public void addLog(Log log) {
        logMapper.insert(log);
    }

    @Override
    public DataTableResult queryLogList(LogSearchParam logSearchParam) {
        Long count = logMapper.queryCount(logSearchParam);

        List<Log> logList = logMapper.queryLogList(logSearchParam);

        List<LogVo> logVoList = new ArrayList<>();
        for (Log log : logList) {
            LogVo logVo = new LogVo();
            logVo.setId(log.getId());
            logVo.setUserName(log.getUserName());
            logVo.setRealName(log.getRealName());
            logVo.setInfo(log.getInfo());
            logVo.setInsertTime(DateUtil.date2str(log.getInsertTime(), DateUtil.FULL_TIME));
            logVo.setStutas(log.getStutas());
            logVo.setContent(log.getContent());
            logVo.setParamInfo(log.getParamInfo());
            logVoList.add(logVo);
        }
        return new DataTableResult(logSearchParam.getDraw(), count, count, logVoList);
    }
}
