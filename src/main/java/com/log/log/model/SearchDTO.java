package com.log.log.model;

import lombok.Data;

/**
 * @author zhangling
 * @date 2021/11/20 11:47 上午
 */
@Data
public class SearchDTO {
    private String message;
    private Integer page;
    private Integer size;
}
