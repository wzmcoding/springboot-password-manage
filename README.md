# PasswordManage

一个基于 Spring Boot 的密码管理系统，提供用户注册登录和账号密码加密存储管理功能。

## 技术栈

- **后端框架**: Spring Boot 3.1.8
- **数据库**: MySQL
- **ORM框架**: MyBatis-Plus 3.5.9
- **认证**: JWT (JSON Web Token)
- **加密**: AES 加密、BCrypt 密码加密
- **构建工具**: Maven
- **JDK版本**: Java 17

## 功能特性

- 用户注册和登录
- JWT Token 认证
- 账号密码的增删改查
- 密码 AES 加密存储
- 用户数据隔离
- RESTful API 接口

## 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 5.7+

## 安装和运行

### 1. 克隆项目

```bash
git clone <repository-url>
cd PasswordManage
```

### 2. 配置数据库

创建 MySQL 数据库：

```sql
CREATE DATABASE account_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 修改配置文件

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/account_system?useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver

vault:
  jwt-secret: your-jwt-secret-key
  jwt-expire: 3600000
  aes-key: your-aes-key-16-chars
```

### 4. 编译项目

```bash
mvn clean install
```

### 5. 运行应用

```bash
mvn spring-boot:run
```

或直接运行主类 `PasswordManageApplication`

应用将在 `http://localhost:8888` 启动

## API 接口

### 用户模块

#### 用户注册
```
POST /user/register
Content-Type: application/json

{
  "username": "testuser",
  "password": "password123"
}
```

#### 用户登录
```
POST /user/login
Content-Type: application/json

{
  "username": "testuser",
  "password": "password123"
}

返回: JWT Token
```

### 账号管理模块

需要 JWT Token 认证，在请求头中添加 `Authorization: Bearer <token>`

#### 添加账号
```
POST /account/add
Content-Type: application/json
Authorization: Bearer <token>

{
  "title": "Google",
  "account": "example@gmail.com",
  "password": "mypassword",
  "note": "Google account"
}
```

#### 查询账号列表
```
GET /account/list
Authorization: Bearer <token>
```

#### 更新账号
```
PUT /account/update
Content-Type: application/json
Authorization: Bearer <token>

{
  "id": 1,
  "title": "Google",
  "account": "newemail@gmail.com",
  "password": "newpassword",
  "note": "Updated note"
}
```

#### 删除账号
```
DELETE /account/delete/{id}
Authorization: Bearer <token>
```

## 数据库表结构

### user 表
```sql
CREATE TABLE user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL
);
```

### account 表
```sql
CREATE TABLE account (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  title VARCHAR(100),
  account VARCHAR(200),
  password TEXT,
  note TEXT,
  FOREIGN KEY (user_id) REFERENCES user(id)
);
```

## 安全说明

- 用户密码使用 BCrypt 加密存储
- 账号密码使用 AES 加密存储
- 使用 JWT Token 进行身份认证
- 每个用户只能访问自己的账号数据

## 项目结构

```
PasswordManage/
├── src/
│   ├── main/
│   │   ├── java/com/zm/passwordmanage/
│   │   │   ├── account/          # 账号管理模块
│   │   │   │   ├── controller/
│   │   │   │   ├── entity/
│   │   │   │   ├── mapper/
│   │   │   │   └── service/
│   │   │   ├── user/             # 用户模块
│   │   │   │   ├── controller/
│   │   │   │   ├── entity/
│   │   │   │   ├── mapper/
│   │   │   │   └── service/
│   │   │   ├── common/           # 通用类
│   │   │   ├── config/           # 配置类
│   │   │   ├── interceptor/      # 拦截器
│   │   │   └── util/             # 工具类
│   │   └── resources/
│   │       └── application.yml   # 配置文件
│   └── test/
└── pom.xml
```

## 注意事项

- 确保 MySQL 服务已启动
- 修改 `application.yml` 中的数据库连接信息
- JWT Secret 和 AES Key 应该使用强随机字符串
- 生产环境建议使用环境变量或配置中心管理敏感信息

## 许可证

MIT License
