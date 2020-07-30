package com.fh.shop.admin;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapTest {

    @Test
    public void TestMap() {
        Map<String, String> map = new HashMap<>();

        map.put("姓名", "张三");
        map.put("性别", "男");
        map.put("年龄", "11");
        map.put("地区", "上市");

        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            System.out.println(key + "=" + value + "----");
        }
    }
}
