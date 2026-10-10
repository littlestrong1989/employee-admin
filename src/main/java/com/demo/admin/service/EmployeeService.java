package com.demo.admin.service;

import com.demo.admin.common.BizException;
import com.demo.admin.common.PageResult;
import com.demo.admin.dto.EmployeeQuery;
import com.demo.admin.dto.EmployeeRequest;
import com.demo.admin.entity.Employee;
import com.demo.admin.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public PageResult<Employee> page(EmployeeQuery q) {
        int page = Math.max(q.getPage() - 1, 0);
        int size = Math.min(Math.max(q.getSize(), 1), 100);
        PageRequest pageable = PageRequest.of(page, size, parseSort(q.getSort()));
        Page<Employee> result = repository.search(
                blankToNull(q.getKeyword()), blankToNull(q.getDepartment()), q.getActive(), pageable);
        return PageResult.of(result);
    }

    public Employee get(Long id) {
        return repository.findById(id).orElseThrow(() -> new BizException(404, "员工不存在"));
    }

    @Transactional
    public Employee create(EmployeeRequest req) {
        Employee e = new Employee();
        apply(e, req);
        return repository.save(e);
    }

    @Transactional
    public Employee update(Long id, EmployeeRequest req) {
        Employee e = get(id);
        apply(e, req);
        return repository.save(e);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new BizException(404, "员工不存在");
        }
        repository.deleteById(id);
    }

    private void apply(Employee e, EmployeeRequest r) {
        e.setName(r.getName());
        e.setDepartment(r.getDepartment());
        e.setPosition(r.getPosition());
        e.setEmail(r.getEmail());
        e.setPhone(r.getPhone());
        e.setSalary(r.getSalary());
        e.setHireDate(r.getHireDate());
        e.setActive(r.getActive() == null || r.getActive());
    }

    private static String blankToNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }

    private static Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "id");
        }
        String[] parts = sort.split(",");
        String field = parts[0].trim();
        Sort.Direction dir = (parts.length > 1 && parts[1].trim().equalsIgnoreCase("asc"))
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(dir, field);
    }
}
