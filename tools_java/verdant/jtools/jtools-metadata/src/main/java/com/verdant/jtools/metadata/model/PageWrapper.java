package com.verdant.jtools.metadata.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * PageWrapper Mybatis用分页对象 继承Arraylist
 * Author: verdant
 * Create: 2016/03/14
 */
public class PageWrapper<E> extends ArrayList<E> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 页码 从1开始
     */
    private int pageNo;

    /**
     * 每页条数 从1开始
     */
    private int pageSize = 10;

    /**
     * 总条数
     */
    protected int totalCount;

    /**
     * 总页数
     */
    protected int totalPage;


    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<E> getResults() {
        return this;
    }

    public void calc() {
        if(this.getPageSize()>0) {
            this.setTotalPage(this.getTotalCount() % this.getPageSize() == 0 ?
                    this.getTotalCount() / this.getPageSize() : this.getTotalCount() / this.getPageSize() + 1);
        }
    }

    public Page<E> toPage() {
        this.calc();
        Page<E> page = new Page<>();
        page.setResults(this.getResults());
        page.setPageNo(this.getPageNo());
        page.setPageSize(this.getPageSize());
        page.setTotalCount(this.getTotalCount());
        page.setTotalPage(this.getTotalPage());
        page.setFirstPage(this.getPageNo() == 1);
        page.setLastPage(this.getPageNo() == this.getTotalPage());
        page.setStartRow((this.getPageNo()-1)*this.getPageSize());
        page.setEndRow(this.getPageNo()*this.getPageSize());
        page.setHasPrevPage(this.getPageNo()-1>0);
        page.setPrevPage(this.getPageNo()-1);
        page.setHasNextPage(this.getPageNo()+1<=this.getTotalPage());
        page.setNextPage(this.getPageNo()+1);
        return page;
    }
}
