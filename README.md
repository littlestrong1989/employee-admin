# Employee Admin · 企业管理后台（后端）

一个可直接运行的企业级后台示例：**Spring Boot 3 + JWT 鉴权 + 员工 CRUD + 分页检索 + Swagger 文档**。
内置 **H2 内存库演示模式**，克隆即可跑，无需装 MySQL。

---

## 技术栈

| 项 | 版本/说明 |
|---|---|
| Java | 17 |
| Spring Boot | 3.3.4 |
| 安全 | Spring Security 6 + JWT (jjwt 0.12.6) |
| 持久层 | Spring Data JPA (Hibernate 6) |
| 数据库 | H2（演示）/ MySQL 8（生产） |
| 文档 | springdoc-openapi (Swagger UI) |
| 校验 | Jakarta Bean Validation |
| 其他 | Lombok、统一响应体、全局异常处理 |

## 功能一览

- ✅ 登录签发 JWT（无状态鉴权）
- ✅ 员工**新增 / 修改 / 删除 / 详情**
- ✅ **分页 + 关键字 + 部门 + 在职状态** 组合检索，支持排序
- ✅ 参数校验 + 统一错误响应（不泄露堆栈）
- ✅ 方法级权限（删除仅 `ROLE_ADMIN`）
- ✅ Swagger UI 在线调试
- ✅ 双 profile：`demo`（H2 免配置）/ `mysql`（生产）

## 快速开始（30 秒，无需 MySQL）

```bash
mvn spring-boot:run
```

启动后：

| 入口 | 地址 |
|---|---|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| H2 控制台 | http://localhost:8080/h2-console |
| 演示账号 | **admin / admin123** |

> demo profile 会自动建表并写入 6 条演示员工数据。

## 用 MySQL 跑

```bash
export DB_HOST=127.0.0.1 DB_NAME=employee_admin DB_USER=root DB_PASSWORD=你的密码
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

建库：`CREATE DATABASE employee_admin DEFAULT CHARSET utf8mb4;`

## API 示例

**1) 登录拿 token**

```bash
curl -s -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"admin","password":"admin123"}'
```

```json
{"code":0,"message":"ok","data":{"token":"eyJhbGciOiJIUzI1NiJ9...","username":"admin","role":"ROLE_ADMIN"}}
```

**2) 带 token 检索员工（分页 + 关键字）**

```bash
TOKEN=上一步的token
curl -s "http://localhost:8080/api/employees?page=1&size=5&keyword=张&department=研发部" \
  -H "Authorization: Bearer $TOKEN"
```

**3) 新增员工**

```bash
curl -s -X POST http://localhost:8080/api/employees \
  -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' \
  -d '{"name":"孙悟空","department":"研发部","position":"架构师","email":"wukong@example.com","salary":30000,"hireDate":"2020-01-01","active":true}'
```

## 项目结构

```
src/main/java/com/demo/admin/
├── EmployeeAdminApplication.java
├── common/        # ApiResponse / PageResult / BizException / 全局异常
├── config/        # SecurityConfig / OpenApiConfig / DataInitializer
├── security/      # JwtUtil / JwtAuthFilter / UserDetailsServiceImpl
├── entity/        # SysUser / Employee
├── repository/    # Spring Data JPA 仓储（含条件检索）
├── dto/           # 请求/响应对象 + 校验注解
├── service/       # AuthService / EmployeeService
└── controller/    # AuthController / EmployeeController
```

## 生产注意事项

- ⚠️ `app.jwt.secret` 必须通过环境变量 `APP_JWT_SECRET` 覆盖（Base64，≥32 字节）
- ⚠️ 演示账号仅存在于 `demo` profile，生产请接入真实用户体系
- ⚠️ H2 控制台仅在 demo 放开；生产请关闭
- 建议加：接口限流、操作审计日志、Docker 化部署、CI

## 作者

**Java 后端 · 10 年经验 · 可接管理系统 / 接口开发 / 系统维护 / AI 集成**
