package com.fh.shop.admin;

import com.fh.shop.admin.util.RedisUtil;
import org.junit.Test;

public class TestRedis {

    @Test
    public void test1() {
        RedisUtil.set("xingming", "dmd");

        String xingming = RedisUtil.get("xingming");
        System.out.println(xingming);
    }
}
