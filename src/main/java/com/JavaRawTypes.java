package com;

import java.util.HashMap;

public class JavaRawTypes {
    public static void main(String[] args) {
        HashMap<String, Integer> hashMap = getHashMap();

        HashMap map = hashMap;
        map.put("H", "Hello");
        Integer i = hashMap.get("H"); // 转换异常
        System.out.println(i);
    }

    public static HashMap<String, Integer> getHashMap() {
        return new HashMap<String, Integer>();
    }
}
