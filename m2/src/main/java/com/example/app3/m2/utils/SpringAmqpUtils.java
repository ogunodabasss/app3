package com.example.app3.m2.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SpringAmqpUtils {

    @SuppressWarnings("unchecked")
    public static void consumerReturnExceptionsHandle(Object res) throws Exception {

        if (res != null) {
            if (res instanceof LinkedHashMap) {
                LinkedHashMap<String, LinkedHashMap<String, Object>> map = (LinkedHashMap<String, LinkedHashMap<String, Object>>) res;
                if (map.containsKey("exception")) {
                    ArrayList<LinkedHashMap<String, Object>> exception = (ArrayList<LinkedHashMap<String, Object>>) map.get("exception").get("stackTrace");
                    throw new Exception(exception.toString());
                }
            }
        }
    }
}
