package com.demo.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** 员工档案 */
@Entity
@Table(name = "employee", indexes = {
        @Index(name = "idx_emp_name", columnList = "name"),
        @Index(name = "idx_emp_dept", columnList = "department")
})
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, length = 32)
    private String department;

    @Column(length = 64)
    private String position;

    @Column(length = 128)
    private String email;

    @Column(length = 32)
    private String phone;

    @Column(precision = 12, scale = 2)
    private BigDecimal salary;

    private LocalDate hireDate;

    @Column(nullable = false)
    private Boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();
}
