package com.gqshao.test.commons.persistence.domain;

public class BaseFilter{

    // 排序字段
    private String sidx;

    // 排序方式 desc/asc
    private String sord;

    // 页码/当前页默认第一页
    private Integer page = 1;

    // 每页记录数，默认是15
    private Integer rows = 15;

    // 总记录数
    private Integer records;

    // 总页数
    private Integer total;

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
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

    @Override
    public String toString() {
        return "BaseFilter [sidx=" + sidx + ", sord=" + sord + ", page=" + page + ", rows=" + rows + "]";
    }

    @SuppressWarnings("rawtypes")
	public static final void setLocal(BaseFilter baseFilter) {
//        JqgridResponseContext.set("baseFilter", baseFilter);
		JqgridResponse jqgridResponse = (JqgridResponse) JqgridResponseContext.get("jqgridResponse");
    	if(jqgridResponse==null){
    		jqgridResponse = new JqgridResponse();
    	}
    	jqgridResponse.setPage(baseFilter.getPage());
        jqgridResponse.setTotal(baseFilter.getTotal());
        jqgridResponse.setRecords(baseFilter.getRecords());
        JqgridResponseContext.set("jqgridResponse", jqgridResponse);
    }

}
