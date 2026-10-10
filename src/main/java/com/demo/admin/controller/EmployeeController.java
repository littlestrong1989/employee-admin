package com.demo.admin.controller;

import com.demo.admin.common.ApiResponse;
import com.demo.admin.common.PageResult;
import com.demo.admin.dto.EmployeeQuery;
import com.demo.admin.dto.EmployeeRequest;
import com.demo.admin.entity.Employee;
import com.demo.admin.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "员工管理")
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "分页检索员工（支持 keyword/department/active/sort）")
    @GetMapping
    public ApiResponse<PageResult<Employee>> page(EmployeeQuery query) {
        return ApiResponse.ok(employeeService.page(query));
    }

    @Operation(summary = "查询单个员工")
    @GetMapping("/{id}")
    public ApiResponse<Employee> get(@PathVariable Long id) {
        return ApiResponse.ok(employeeService.get(id));
    }

    @Operation(summary = "新增员工")
    @PostMapping
    public ApiResponse<Employee> create(@Valid @RequestBody EmployeeRequest req) {
        return ApiResponse.ok(employeeService.create(req));
    }

    @Operation(summary = "修改员工")
    @PutMapping("/{id}")
    public ApiResponse<Employee> update(@PathVariable Long id,
                                        @Valid @RequestBody EmployeeRequest req) {
        return ApiResponse.ok(employeeService.update(id, req));
    }

    @Operation(summary = "删除员工（仅 ROLE_ADMIN）")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ApiResponse.ok();
    }
}
