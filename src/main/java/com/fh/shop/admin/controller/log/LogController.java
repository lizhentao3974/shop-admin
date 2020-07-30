package com.fh.shop.admin.controller.log;

import com.fh.shop.admin.biz.log.ILogService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.param.log.LogSearchParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/log")
public class LogController {

    @Resource(name = "logService")
    private ILogService logService;

    @RequestMapping("/toLogList")
    public String toLogList() {
        return "log/list";
    }

    @RequestMapping("/queryLogList")
    @ResponseBody
    public DataTableResult queryLogList(LogSearchParam logSearchParam) {
        return logService.queryLogList(logSearchParam);
    }
}
