package com.log.log.model;

import lombok.Data;

import java.util.List;

/**
 * @author zhangling
 * @date 2021/11/22 9:21 下午
 */
@Data
public class PageUtils<T> {
    /**
     * 总记录数
     */
    private int total;
    /**
     * 每页记录数
     */
    private int size;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 当前页数
     */
    private int page;
    /**
     * 列表数据
     */
    private List<T> list;

    public List<T> getList() {
        return list;
    }


    public PageUtils() {
    }


    public PageUtils(List<T> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.total = totalCount;
        this.size = pageSize;
        this.page = currPage;
        this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
    }



    public PageUtils<T> setList(List<T> list) {
        this.list = list;
        return this;
    }
}
