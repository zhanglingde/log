package com.log.log.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    private String timestamp;
    private String version;
    private String stackTrace;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("@timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
