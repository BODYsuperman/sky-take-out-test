# Sky Takeout - 外卖订餐系统

![Java](https://img.shields.io/badge/Java-17+-ED8B00?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7.3-6DB33F?logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-6.x-DC382D?logo=redis&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.x-C71A36?logo=apache-maven&logoColor=white)

Sky Takeout 是一个完整的外卖订餐平台后端系统，基于 Spring Boot 构建，提供从商家管理到用户下单的全流程功能。

## 📋 功能模块

### 管理端功能
- **员工管理** - 餐厅员工账号管理、权限控制
- **分类管理** - 菜品和套餐分类管理
- **菜品管理** - 菜品信息维护、口味配置、图片上传
- **套餐管理** - 套餐组合配置、关联菜品管理
- **订单管理** - 订单状态跟踪、处理流程管理

### 用户端功能
- **用户微信登录** - 微信授权一键登录
- **购物车功能** - 菜品添加、数量修改、删除操作
- **地址簿管理** - 收货地址维护、默认地址设置
- **订单下单** - 完整的下单流程、支付集成

## 🚀 技术栈

### 后端框架
- **Spring Boot 2.7.3** - 核心应用框架
- **MyBatis Plus 3.5.2** - ORM 框架，简化数据库操作
- **Spring Validation** - 参数校验
- **JWT** - 用户认证和授权
- **Lombok** - 简化 POJO 类编写

### 数据库与缓存
- **MySQL 8.0** - 主要数据存储
- **Redis** - 缓存、会话管理、分布式锁

### 工具与依赖
- **FastJSON 2.0.12** - JSON 序列化/反序列化
- **Apache HttpClient** - HTTP 客户端
- **Swagger 3.0.0** - API 文档生成
- **Apache Commons Lang3** - 工具类库

## 📁 项目结构

```
sky-take-out/
├── src/main/java/com/sky/
│   ├── config/           # 配置类
│   ├── constant/         # 常量定义
│   ├── controller/       # 控制器层
│   ├── dto/             # 数据传输对象
│   ├── entity/          # 实体类
│   ├── exception/       # 异常处理
│   ├── interceptor/     # 拦截器
│   ├── mapper/          # MyBatis Mapper
│   ├── result/          # 统一返回结果封装
│   ├── service/         # 服务层接口及实现
│   ├── utils/           # 工具类
│   └── vo/              # 视图对象
├── src/main/resources/
│   ├── application.yml  # 应用配置文件
│   └── mapper/          # MyBatis XML 映射文件
├── Dockerfile           # Docker 镜像构建文件
├── docker-compose.yml   # 容器编排配置
├── pom.xml             # Maven 依赖管理
└── README.md           # 项目说明文档
```

## ⚙️ 快速开始

### 环境要求
- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.x+

### 本地运行

1. **克隆项目**
   ```bash
   git clone https://github.com/BODYsuperman/sky-take-out-test.git
   cd sky-take-out-test
   ```

2. **配置数据库**
   - 创建 MySQL 数据库：`CREATE DATABASE sky_takeout CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`
   - 修改 `application.yml` 中的数据库连接配置

3. **启动应用**
   ```bash
   mvn spring-boot:run
   ```

### Docker 部署

1. **使用 Docker Compose 启动所有服务**
   ```bash
   docker-compose up -d
   ```

2. **或者单独构建并运行应用**
   ```bash
   docker build -t sky-takeout .
   docker run -p 8080:8080 sky-takeout
   ```

## 🧪 测试

项目包含完整的测试计划和自动化测试脚本：

```bash
# 运行自动化测试
./test-automation.sh
```

详细测试用例请参考 [TEST_PLAN.md](TEST_PLAN.md)

## 📚 文档

- **API 文档**: [API_DOCUMENTATION.md](API_DOCUMENTATION.md)
- **用户手册**: [USER_MANUAL.md](USER_MANUAL.md)
- **部署指南**: 查看 [deploy.sh](deploy.sh) 脚本
- **监控配置**: [monitoring/](monitoring/) 目录

## 📊 监控

项目集成了完整的监控方案：
- **Prometheus** - 指标收集
- **Grafana** - 可视化仪表板
- **Logback** - 日志配置

启动监控：
```bash
./monitoring/start-monitoring.sh
```

## 🔄 分支策略

- **main** - 生产就绪的主分支
- **feature/\*-module** - 各功能模块的独立开发分支

## 📝 开发规范

- 使用统一的响应格式封装
- 控制器层只处理请求和响应
- 服务层处理业务逻辑
- Mapper 层只处理数据访问
- 异常统一处理机制

## 🤝 贡献指南

1. Fork 本仓库
2. 创建 feature 分支 (`git checkout -b feature/your-feature`)
3. 提交更改 (`git commit -am 'Add some feature'`)
4. 推送到分支 (`git push origin feature/your-feature`)
5. 创建 Pull Request

## 📄 许可证

本项目仅供学习和参考使用。

## 📞 联系方式

如有问题或建议，请通过 GitHub Issues 提交。