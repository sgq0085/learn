package com.gqshao.test.commons.persistence.domain;

import java.util.List;

public class JqgridResponse<T> {

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 总页数
     */
    private Integer total;

    /**
     * 总记录数
     */
    private Integer records;

    /**
     * 返回数据
     */
    private List<T> rows;

    protected JqgridResponse() {

    }

    public JqgridResponse(Integer page, Integer total, Integer records,	List<T> rows) {
		super();
		this.page = page;
		this.total = total;
		this.records = records;
		this.rows = rows;
	}

	public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords(Integer records) {
        this.records = records;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "JqgridResponse [page=" + page + ", total=" + total + ", records=" + records + "]";
    }
}
