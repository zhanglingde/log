package com.log.log.utils;

import cn.hutool.json.JSONUtil;
import com.log.log.model.PageUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangling  2021/8/9 15:26
 */
@Component
public class ElasticSearchUtils {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchUtils.class);

    @Resource(name = "esClient")
    private RestHighLevelClient client;

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    public boolean isExistIndex(String index) {
        GetIndexRequest request = new GetIndexRequest(index);
        boolean exists = true;
        try {
            exists = client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("判断索引【" + index + "】是否存在异常", e);
            throw new RuntimeException("判断索引【" + index + "】是否存在异常");
        }
        return exists;
    }

    /**
     * 创建索引
     *
     * @param index
     * @return
     */
    public boolean createIndex(String index) {
        if (isExistIndex(index)) {
            return false;
        }
        // 1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(index);
        // 可以设置索引的定义 映射等信息
        // request.settings(Settings.builder().put("index.number_of_shards",3).put("index.number_of_replicas",2));
        // request.mapping("{\"properties\":{\"title\":{\"type\":\"text\"}}}", XContentType.JSON);
        CreateIndexResponse response = null;
        try {
            response = client.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("创建索引【" + index + "】异常", e);
            throw new RuntimeException("创建索引【" + index + "】异常");
        }
        return response.isAcknowledged();
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public boolean deleteIndex(String index) {
        if (!isExistIndex(index)) {
            logger.error("索引【{}】不存在", index);
            return false;
        }
        // 删除索引请求
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        AcknowledgedResponse response = null;
        try {
            response = client.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("删除索引【" + index + "】异常", e);
            throw new RuntimeException("删除索引【" + index + "】异常");
        }
        return response.isAcknowledged();

    }

    /**
     * 通过 id 判断文档是否存在
     *
     * @param index 索引
     * @param id    文档id
     * @return
     */
    public boolean isExistDocById(String index, String id) {
        GetRequest request = new GetRequest(index, id);
        // 不获取返回的 _source 的上下文
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        try {
            return client.exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("判断文档【" + index + "/" + id + "】是否存在异常", e);
            throw new RuntimeException("判断文档【" + index + "/" + id + "】是否存在异常");
        }
    }

    /**
     * 添加文档
     *
     * @param doc   索引文档参，可以通过 json字符串、map、XContentBuilder 三种方式
     * @param index 文档 索引
     * @param id    文档 id,id 为 null 时，自动生成文档 id
     * @return
     */
    public String addDoc(Map<String, ?> doc, String index, String id) {
        // 构建 IndexRequest 请求，请求参数为文档索引的索引名称
        IndexRequest request = new IndexRequest(index);
        request.source(doc);        // 通过 map 方式传参
        request.id(id);

        try {
            // 同步执行请求
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            return indexResponse.getId();
        } catch (IOException e) {
            logger.error("添加文档【" + index + "/" + id + "】异常", e);
            throw new RuntimeException("添加文档【" + index + "/" + id + "】异常");
        }
    }

    /**
     * 根据 id 查询文档
     *
     * @param index
     * @param id
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getDocById(String index, String id, Class<T> clazz) {
        GetRequest request = new GetRequest(index, id);
        try {
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            if (response.isExists()) {
                String json = response.getSourceAsString();
                T t = JSONUtil.toBean(json, clazz);
                return t;
            } else {
                return null;
            }
        } catch (IOException e) {
            logger.error("查询 es 异常", e);
            throw new RuntimeException("查询 es 异常");
        }
    }

    /**
     * 根据文档 id 删除文档
     *
     * @param index
     * @param id
     */
    public void deleteDocById(String index, String id) {
        DeleteRequest request = new DeleteRequest(index, id);
        try {
            DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("删除es文档出错", e);
            throw new RuntimeException("删除es文档出错" + e.getCause().getMessage());
        }
    }

    /**
     * 多条件查询，查询全部字段
     *
     * @param index
     * @param builder
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> searchListData(String index, SearchSourceBuilder builder, Class<T> clazz) {
        SearchRequest request = new SearchRequest(index);
        if (builder == null) {
            builder = new SearchSourceBuilder();
        }
        builder.trackTotalHits(true);
        request.source(builder);
        SearchResponse response = null;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("查询es 异常", e);
            throw new RuntimeException("查询 es 异常");
        }
        if (response.status().getStatus() == 200) {
            // 解析查询结果
            List<T> list = setSearchResponse(response, clazz);
            return list;
        }
        return null;
    }

    /**
     * 解析查询结果
     *
     * @param response
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> List<T> setSearchResponse(SearchResponse response, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            String json = hit.getSourceAsString();
            T t = JSONUtil.toBean(json, clazz);
            list.add(t);
        }
        return list;
    }

    /**
     * 多条件查询，查询全部字段
     *
     * @param index
     * @param builder
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> PageUtils<T> searchPageData(String index, SearchSourceBuilder builder, Class<T> clazz) {
        SearchRequest request = new SearchRequest(index);
        if (builder == null) {
            builder = new SearchSourceBuilder();
        }
        builder.trackTotalHits(true);
        request.source(builder);
        SearchResponse response = null;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("查询es 异常", e);
            throw new RuntimeException("查询 es 异常");
        }
        if (response.status().getStatus() == 200) {
            // 解析查询结果
            List<T> list = setSearchResponse(response, clazz);
            return list;
        }
        return null;
    }

    /**
     * 解析查询结果
     *
     * @param response
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> PageUtils<T> setPageSearchResponse(SearchResponse response, Class<T> clazz) {
        PageUtils<T> page = new PageUtils<>();
        List<T> list = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            String json = hit.getSourceAsString();
            T t = JSONUtil.toBean(json, clazz);
            list.add(t);
        }
        page.setTotalPage(0);
        page.setList(list);
        return page;
    }

}
