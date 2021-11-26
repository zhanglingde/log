package com.log.log;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.log.log.model.LogVO;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangling
 * @date 2021/11/22 9:30 上午
 */
public class Test01 {

    @Test
    public void test02() {
        String date = "2021-11-23T08:15:00.052Z";
        date = date.replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        try {
            Date d = format.parse(date);
            System.out.println("d = " + d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test03() {
        String json = "{\n" +
                "    \"message\": \"已超过自动派单次数上限，单据:BILL202111230000891\",\n" +
                "    \"thread_name\": \"scheduling-6\",\n" +
                "    \"level_value\": 20000,\n" +
                "    \"logger_name\": \"com.maycur.fop.service.impl.DispatchRuleServiceImpl\",\n" +
                "    \"@version\": \"1\",\n" +
                "    \"@timestamp\": \"2021-11-23T09:30:00.483Z\",\n" +
                "    \"host\": \"localhost\",\n" +
                "    \"port\": 54448,\n" +
                "    \"tags\": [\n" +
                "        \"_grokparsefailure\"\n" +
                "    ],\n" +
                "    \"level\": \"INFO\"\n" +
                "}";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            LogVO logVO1 = objectMapper.readValue(json, LogVO.class);
            System.out.println("logVO1 = " + logVO1);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        LogVO logVO = JSONUtil.toBean(json, LogVO.class);
//        System.out.println("logVO = " + logVO);
    }
}
