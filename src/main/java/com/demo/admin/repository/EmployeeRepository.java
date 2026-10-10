package com.demo.admin.repository;

import com.demo.admin.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("""
            select e from Employee e
            where (:keyword is null or lower(e.name) like lower(concat('%', :keyword, '%'))
                                    or lower(e.email) like lower(concat('%', :keyword, '%')))
              and (:department is null or e.department = :department)
              and (:active is null or e.active = :active)
            """)
    Page<Employee> search(@Param("keyword") String keyword,
                          @Param("department") String department,
                          @Param("active") Boolean active,
                          Pageable pageable);
}
