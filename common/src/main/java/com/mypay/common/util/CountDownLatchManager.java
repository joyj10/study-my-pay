package com.mypay.common.util;

import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 멀티스레드 환경에서 특정 작업의 완료를 기다리거나 데이터를 공유하는 데 도움을 주는 유틸리티 클래스
 */
@Configuration
public class CountDownLatchManager {
    private final Map<String, CountDownLatch> countDownLatchMap;
    private final Map<String, String> stringMap;

    public CountDownLatchManager() {
        this.countDownLatchMap = new HashMap<>();
        this.stringMap = new HashMap<>();
    }

    public void addCountDownLatch(String key) {
        this.countDownLatchMap.put(key, new CountDownLatch(1));
    }

    public void setDataForKey(String key, String data){
        this.stringMap.put(key, data);
    }
    public String getDataForKey(String key){
        return this.stringMap.get(key);
    }
    public CountDownLatch getCountDownLatch(String key) {
        return this.countDownLatchMap.get(key);
    }
}