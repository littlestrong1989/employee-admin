package com.demo.admin.config;

import com.demo.admin.entity.Employee;
import com.demo.admin.entity.SysUser;
import com.demo.admin.repository.EmployeeRepository;
import com.demo.admin.repository.SysUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

/** demo profile 下初始化演示数据（生产环境不加载） */
@Slf4j
@Component
@Profile("demo")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final SysUserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            SysUser u = new SysUser();
            u.setUsername("admin");
            u.setPassword(passwordEncoder.encode("admin123"));
            u.setRole("ROLE_ADMIN");
            userRepository.save(u);
            log.info("demo user created -> admin / admin123");
        }

        if (employeeRepository.count() == 0) {
            String[][] seed = {
                    {"张伟", "研发部", "后端工程师"},
                    {"李娜", "产品部", "产品经理"},
                    {"王强", "研发部", "前端工程师"},
                    {"赵敏", "人事部", "HR 专员"},
                    {"刘洋", "销售部", "销售代表"},
                    {"陈静", "财务部", "会计"},
            };
            int i = 0;
            for (String[] s : seed) {
                i++;
                Employee e = new Employee();
                e.setName(s[0]);
                e.setDepartment(s[1]);
                e.setPosition(s[2]);
                e.setEmail("user" + i + "@example.com");
                e.setPhone("1380000000" + i);
                e.setSalary(BigDecimal.valueOf(8000 + i * 1500L));
                e.setHireDate(LocalDate.now().minusMonths(i * 3L));
                e.setActive(true);
                employeeRepository.save(e);
            }
            log.info("demo employees seeded -> {}", employeeRepository.count());
        }
    }
}
