package com.yff.core.jparepository.page;


import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/*
 * JPA 分页封装
 */
@Data
public class JpaPaging {

    private int pageNum;
    private int pageSize;
    private String sortType;
    private String field;

    public void JpaPaging(int pageNum, int pageSize, String sortType, String field) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.sortType = sortType;
        this.field = field;
    }

    public Pageable getPageable() {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortType.toUpperCase()), field);
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        return pageable;
    }

}
