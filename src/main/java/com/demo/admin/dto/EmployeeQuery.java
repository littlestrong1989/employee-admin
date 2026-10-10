package com.demo.admin.dto;

import lombok.Data;

/** 分页检索条件 */
@Data
public class EmployeeQuery {
    private String keyword;
    private String department;
    private Boolean active;
    private int page = 1;
    private int size = 10;
    private String sort = "id,desc";
}
