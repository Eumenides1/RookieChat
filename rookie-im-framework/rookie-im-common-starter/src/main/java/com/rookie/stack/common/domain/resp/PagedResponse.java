package com.rookie.stack.common.domain.resp;

import lombok.Data;

import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/4/7
 */
@Data
public class PagedResponse<T> {
    private List<T> records;
    private long total;
    private int page;
    private int pageSize;
}

