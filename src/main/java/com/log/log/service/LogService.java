package com.log.log.service;

import cn.hutool.core.util.StrUtil;
import com.log.log.model.LogVO;
import com.log.log.model.PageUtils;
import com.log.log.model.SearchDTO;
import com.log.log.utils.ElasticSearchUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
     *
     * @return
     */
    public PageUtils<LogVO> logList(SearchDTO searchDTO) {
        SearchSourceBuilder search = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StrUtil.isNotBlank(searchDTO.getMessage())) {
            boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("message", searchDTO.getMessage()));
        }
        if (StrUtil.isNotBlank(searchDTO.getLevel())) {
            boolQueryBuilder.filter(QueryBuilders.matchQuery("level", searchDTO.getLevel()));
        }
        if (searchDTO.getStartTime() != null && searchDTO.getEndTime() != null) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("@timestamp").gte(searchDTO.getStartTime()).lt(searchDTO.getEndTime()));
        }
        search.query(boolQueryBuilder);
        search.size(Optional.ofNullable(searchDTO.getSize()).orElse(1));
        search.from(Optional.ofNullable(searchDTO.getPage()).orElse(10));
        search.sort("@timestamp", SortOrder.DESC);

        PageUtils<LogVO> page = elasticSearchUtils.searchPageData("log-ling-dev-2021.11.23", search, LogVO.class);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        try {
            for (LogVO logVO : page.getList()) {
                logVO.setTimestamp(logVO.getTimestamp().replace("Z", " UTC"));
                logVO.setCreateTime(format.parse(logVO.getTimestamp()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return page;
    }
}
