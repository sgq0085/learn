package com.gqshao.bootstrap.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TimeFilter {

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date recordDay;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date min;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date max;

    public Date getRecordDay() {
        return recordDay;
    }

    public void setRecordDay(Date recordDay) {
        this.recordDay = recordDay;
    }

    public Date getMin() {
        return min;
    }

    public void setMin(Date min) {
        this.min = min;
    }

    public Date getMax() {
        return max;
    }

    public void setMax(Date max) {
        this.max = max;
    }
}