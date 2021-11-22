package com.log.log.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zhangling  2021/8/9 15:17
 */
@Data
public class LogVO {
    private Long port;
    private Long levelValue;
    private String level;
    private String threadName;
    private String host;
    private String loggerName;
    private String message;
    @JsonProperty("@timestamp")
    private Date timestamp;
    private String version;


}
