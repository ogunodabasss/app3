package com.example.app3.m1.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Slf4j
public class SpringAmqpUtils {

    @SuppressWarnings("unchecked")
    public static void consumerReturnExceptionsHandle(Object res) throws Exception {

        if (res != null) {
            if (res instanceof LinkedHashMap) {
                LinkedHashMap<String, LinkedHashMap<String, Object>> map = (LinkedHashMap<String, LinkedHashMap<String, Object>>) res;
                if (map.containsKey("exception")) {
                    log.error(res.toString());
                    ArrayList<LinkedHashMap<String, Object>> exception = (ArrayList<LinkedHashMap<String, Object>>) map.get("exception").get("stackTrace");
                    throw new AmqpException(exception.toString());
                }
            }
        } else throw new AmqpException("res is null");
    }
}
