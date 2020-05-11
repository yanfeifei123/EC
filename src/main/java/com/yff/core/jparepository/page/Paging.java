package com.yff.core.jparepository.page;


import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/*
 * 分页封装
 */
@Data
public class Paging {

    private int pagenum;
    private int pagesize;
    private int page;
    private int totalPage;
    private int totalRecord;

    public int getTotalPage() {
        return totalPage = (totalRecord + pagesize - 1) / pagesize;
    }




}
