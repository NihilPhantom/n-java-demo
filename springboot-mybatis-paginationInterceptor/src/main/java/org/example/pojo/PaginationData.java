package org.example.pojo;

import java.util.List;

public class PaginationData<T> {
    private Integer currentPage;          // 当前页码
    private Integer pageSize;             // 每页数据条数
    private Integer totalCount;           // 数据总条数
    private List<T> dataList;         // 当前页数据列表

    // 构造函数
    public PaginationData(int currentPage, int pageSize, int totalCount, List<T> dataList) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.dataList = dataList;
    }
    public PaginationData(List<T> dataList) {
        this.dataList = dataList;
    }

    public PaginationData(int totalCount, List<T> dataList) {
        this.totalCount = totalCount;
        this.dataList = dataList;
    }

    public PaginationData(){};


    // getter方法
    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}