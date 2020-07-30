package com.fh.shop.admin.common;

import javax.servlet.http.HttpServletRequest;

public class WebContext {

    private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();

    public static void set(HttpServletRequest request) {
        requestThreadLocal.set(request);
    }

    public static HttpServletRequest get() {
        return requestThreadLocal.get();
    }

    public static void del() {
        requestThreadLocal.remove();
    }
}
