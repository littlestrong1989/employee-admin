package com.demo.admin.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmployeeRequest {

    @NotBlank(message = "不能为空")
    @Size(max = 64, message = "长度不能超过 64")
    private String name;

    @NotBlank(message = "不能为空")
    @Size(max = 32, message = "长度不能超过 32")
    private String department;

    @Size(max = 64, message = "长度不能超过 64")
    private String position;

    @Email(message = "格式不正确")
    private String email;

    @Size(max = 32, message = "长度不能超过 32")
    private String phone;

    @DecimalMin(value = "0", message = "不能为负数")
    private BigDecimal salary;

    private LocalDate hireDate;

    private Boolean active = Boolean.TRUE;
}
