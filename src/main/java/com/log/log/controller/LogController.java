package com.log.log.controller;


import com.log.log.model.LogVO;
import com.log.log.model.PageUtils;
import com.log.log.model.ResultVO;
import com.log.log.model.SearchDTO;
import com.log.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 日志列表
 * @author zhangling  2021/8/10 10:57
 */
@RestController
@RequestMapping("/system/log")
public class LogController {

    @Autowired
    LogService logService;

    @GetMapping("/")
    public ResultVO<PageUtils<LogVO>> logList(SearchDTO searchDTO){
        ResultVO<PageUtils<LogVO>> pageUtilsResultVO = logService.logList(searchDTO);
        return pageUtilsResultVO;
    }
}
