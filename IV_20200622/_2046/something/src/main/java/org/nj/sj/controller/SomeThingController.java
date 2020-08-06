package org.nj.sj.controller;

import lombok.Setter;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.log4j.Log4j;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static org.nj.sj.controller.SomeThingController.toJSONString;

@Log4j
class RequestMetrics
{
    @Setter
    int totalCount; // 请求患者总数
    int successCount; // 成功总数
    int failedCount; // 失败总数
    List<String> failedPatients = new ArrayList<>(); // 失败患者集合
    @Setter
    double totalTime; // 总共耗费的时间
    volatile boolean finished; // 请求是否完成

    synchronized public void addSuccessCount()
    {
        successCount++;
        if (successCount + failedCount == totalCount)
        {
            finished = true;
            notify();
        }
    }

    synchronized public void addFailedCount()
    {
        failedCount++;
        if (successCount + failedCount == totalCount)
        {
            finished = true;
            notify();
        }
    }

    synchronized public void addFailedPatientSN(String patientSN)
    {
        failedPatients.add(patientSN);
    }

    public synchronized void waitForComplete()
    {
        while (!finished)
        {
            try
            {
                wait();
            } catch (InterruptedException e)
            {
                log.info(e);
            }
        }
    }

    public String getResponse()
    {
        JSONObject rt = new JSONObject(true);
        rt.put("totalPatient", totalCount);
        rt.put("success", successCount);
        rt.put("failed", failedCount);
        rt.put("totalTime(s)", totalTime);
        rt.put("averagePatientTime(s)", Double.valueOf(SomeThingController.formatDouble(totalTime/(totalCount == 0 ? 1 : totalCount))));
        rt.put("failedPatients", failedPatients);
        return toJSONString(rt);
    }

    public String toString()
    {
        return "\n" + getResponse();
    }

}

@Log4j
@RestController
@RequestMapping("/something")
public class SomeThingController
{

    @RequestMapping("/as")
    public String concurrent(@RequestBody String json)
    {
        log.info("start test log");
        JSONObject para = parseObject(json);
        return json;
    }


    public static void main(String[] args) {
        log.info("start test log");
        RequestMetrics requestMetrics = new RequestMetrics();
        String a = new String("{'a':[{'c':1}, 2, 3], 'b':'d'}");
        JSONObject para = parseObject(a);
        JSONObject p = JSON.parseObject(a);
        System.out.println(a);
        System.out.println(para);
    }


    public static final int FASTJSON_PARSE_FEATURE;
    public static final int FASTJSON_GENERATE_FEATURE;
    static {
        int feature = JSON.DEFAULT_PARSER_FEATURE;
        feature &= ~Feature.UseBigDecimal.getMask();
        feature |= Feature.OrderedField.getMask();
        FASTJSON_PARSE_FEATURE = feature;
        feature = JSON.DEFAULT_GENERATE_FEATURE;
        feature |= Feature.DisableCircularReferenceDetect.getMask();
        FASTJSON_GENERATE_FEATURE = feature;
    }
    public static JSONObject parseObject(String json) {
        return (JSONObject)JSON.parse(json, FASTJSON_PARSE_FEATURE);
    }

    public static String toJSONString(JSON json) {
        return JSON.toJSONString(json, FASTJSON_GENERATE_FEATURE, new SerializerFeature[0]);
    }

    public static String formatDouble(double t)
    {
        return String.format("%.3f", t);
    }

}




