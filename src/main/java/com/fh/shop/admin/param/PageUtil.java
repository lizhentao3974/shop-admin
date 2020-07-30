package com.fh.shop.admin.param;

public class PageUtil {
    private Long draw;

    private Long start;

    private Long length;

    public Long getDraw() {
        return draw;
    }

    public PageUtil setDraw(Long draw) {
        this.draw = draw;
        return this;
    }

    public Long getStart() {
        return start;
    }

    public PageUtil setStart(Long start) {
        this.start = start;
        return this;
    }

    public Long getLength() {
        return length;
    }

    public PageUtil setLength(Long length) {
        this.length = length;
        return this;
    }
}
