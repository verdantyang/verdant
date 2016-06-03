package com.verdant.jtools.common.orm.model;

import java.io.Serializable;

/**
 * Author: verdant
 * Create: 2016/03/14
 * Desc: 分页查询参数
 */
public class PageParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 页码 从1开始
     */
    private int pageNo = 1;

    /**
     * 每页条数 从10开始
     */
    private int pageSize = 10;


    public PageParam() {
        super();
    }

    public PageParam(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 取得pageNo
     *
     * @return 返回pageNo。
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置pageNo
     *
     * @param pageNo 要设置的pageNo。
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 取得pageSize
     *
     * @return 返回pageSize。
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置pageSize
     *
     * @param pageSize 要设置的pageSize。
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}
