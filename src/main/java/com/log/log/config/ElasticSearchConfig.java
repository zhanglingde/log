package com.log.log.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ES 配置类
 * @author zhangling  2021/8/9 14:49
 */
@Configuration
public class ElasticSearchConfig {

    @Bean("esClient")
    public RestHighLevelClient restHighLevelClient() {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost",9200,"http")
                // 集群配置      new HttpHost("192.168.152.133",9200,"http")
        );
        return new RestHighLevelClient(builder);
    }
}
