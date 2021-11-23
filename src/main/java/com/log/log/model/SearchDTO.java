package com.log.log.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zhangling
 * @date 2021/11/20 11:47 上午
 */
@Data
public class SearchDTO {
    private String message;
    private String level;
    private Integer page;
    private Integer size;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "",timezone = "Asia/Shanghai")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "",timezone = "Asia/Shanghai")
    private Date endTime;
}
