package com.log.log.service;

import cn.hutool.core.util.StrUtil;
import com.log.log.model.LogVO;
import com.log.log.model.PageUtils;
import com.log.log.model.SearchDTO;
import com.log.log.utils.ElasticSearchUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author zhangling  2021/8/10 11:01
 */
@Service
public class LogService {

    @Autowired
    ElasticSearchUtils elasticSearchUtils;

    /**
     * 日志列表
     * @return
     */
    public PageUtils<LogVO> logList(SearchDTO searchDTO) {
        SearchSourceBuilder search = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StrUtil.isNotBlank(searchDTO.getMessage())) {
            boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("message", searchDTO.getMessage()));
        }
        search.query(boolQueryBuilder);
        search.size(Optional.ofNullable(searchDTO.getSize()).orElse(1));
        search.from(Optional.ofNullable(searchDTO.getPage()).orElse(10));
        List<LogVO> list = elasticSearchUtils.searchListData("log-ling-dev-2021.11.22", search, LogVO.class);
        PageUtils<LogVO> pageUtils = new PageUtils<>();

        pageUtils.setList(list);
        return pageUtils;
    }
}
