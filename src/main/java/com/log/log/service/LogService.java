package com.log.log.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.log.log.model.LogVO;
import com.log.log.model.PageUtils;
import com.log.log.model.SearchDTO;
import com.log.log.utils.DateUtils;
import com.log.log.utils.ElasticSearchUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        String index = "fop-dev-";
        String day = Optional.ofNullable(searchDTO.getDay()).orElse(DateUtil.format(new Date(), "yyyy-MM-dd"));
        index += day;
        SearchSourceBuilder search = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StrUtil.isNotBlank(searchDTO.getMessage())) {
            boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("message", searchDTO.getMessage()));
        }
        if (StrUtil.isNotBlank(searchDTO.getLevel())) {
            boolQueryBuilder.filter(QueryBuilders.matchQuery("level", searchDTO.getLevel()));
        }
        if (StrUtil.isNotEmpty(searchDTO.getStartTime()) && StrUtil.isNotEmpty(searchDTO.getEndTime())) {
            Date startTime = DateUtils.stringToDate(day + searchDTO.getStartTime(), "yyyy-MM-ddHH:mm:ss");
            Date endTime = DateUtils.stringToDate(day + searchDTO.getEndTime(), "yyyy-MM-ddHH:mm:ss");
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("@timestamp").gte(startTime).lt(endTime));
        }
        search.query(boolQueryBuilder);
        search.size(Optional.ofNullable(searchDTO.getSize()).orElse(1));
        search.from(Optional.ofNullable(searchDTO.getPage()).orElse(10));
        search.sort("@timestamp", SortOrder.DESC);

        PageUtils<LogVO> page = elasticSearchUtils.searchPageData(index, search, LogVO.class);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        try {
            for (LogVO logVO : page.getList()) {
                logVO.setTimestamp(logVO.getTimestamp().replace("Z", " UTC"));
                // DateUtils.stringToDate(logVO.getTimestamp(),"yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                logVO.setCreateTime(format.parse(logVO.getTimestamp()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return page;
    }
}
