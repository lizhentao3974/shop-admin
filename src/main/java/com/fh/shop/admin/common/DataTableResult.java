package com.fh.shop.admin.common;

import java.io.Serializable;
import java.util.List;

public class DataTableResult implements Serializable {

    private Long draw;

    private Long recordsFiltered;

    private Long recordsTotal;

    private List data;

    public DataTableResult() {
    }

    public DataTableResult(Long draw, Long recordsFiltered, Long recordsTotal, List data) {
        this.data = data;
        this.draw = draw;
        this.recordsFiltered = recordsFiltered;
        this.recordsTotal = recordsTotal;
    }

    public Long getDraw() {
        return draw;
    }

    public DataTableResult setDraw(Long draw) {
        this.draw = draw;
        return this;
    }

    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public DataTableResult setRecordsFiltered(Long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
        return this;
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public DataTableResult setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
        return this;
    }

    public List getData() {
        return data;
    }

    public DataTableResult setData(List data) {
        this.data = data;
        return this;
    }
}
