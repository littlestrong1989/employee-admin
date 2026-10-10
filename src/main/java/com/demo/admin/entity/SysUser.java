package com.demo.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/** 系统用户（登录用） */
@Entity
@Table(name = "sys_user", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Getter
@Setter
public class SysUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String username;

    /** BCrypt 密文 */
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 32)
    private String role = "ROLE_ADMIN";

    private LocalDateTime createdAt = LocalDateTime.now();
}
