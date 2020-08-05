package org.nj.sj.controller;

import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.RecursiveTask;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/something")
public class SomeThingController
{

    @RequestMapping("/as")
    public String concurrent(@RequestBody String json)
    {
        JSONObject para = parseObject(json);
        return json;
    }


    public static void main(String[] args)
            {

        System.out.println("dfds");
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

}




