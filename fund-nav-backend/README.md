# 青文基金估值后端服务

阿里云服务器后端 Java 代码仓库 - 基金估值工具

## 📖 项目简介

这是一个基于 Spring Boot 3.4.1 的基金估值后端服务系统，提供用户管理、基金持仓管理、自选基金跟踪等功能。系统采用前后端分离架构，为微信小程序提供 RESTful API 接口。

## 🏗️ 技术架构

### 核心技术栈
- **Spring Boot 3.4.1** - 应用框架
- **MyBatis-Plus 3.5.9** - ORM 持久层框架
- **MySQL** - 关系型数据库
- **Redis** - 缓存中间件
- **JWT (jjwt 0.12.7)** - 身份认证
- **微信小程序** - 客户端集成

### 依赖库
- **Hutool 5.8.1** - Java 工具库
- **FastJSON 1.2.83** - JSON 处理
- **Lombok** - 代码简化
- **Spring AOP** - 面向切面编程


## 🚀 核心功能

### 1. 用户认证模块 (`/api/v1.0/fund/valuation/auth`)
- **微信登录** - `POST /login`
    - 通过微信小程序 code 换取 token
    - 返回用户信息和认证令牌

- **退出登录** - `POST /logout`
    - 清除用户会话信息

### 2. 用户管理模块 (`/api/v1.0/fund/valuation`)
- **查询用户信息** - `POST /queryUserInfo`
- **删除用户信息** - `POST /deleteUserInfo`
- **查询用户账户** - `POST /queryUserAccount`
- **删除用户账户** - `POST /deleteUserAccount`

### 3. 基金仓位管理 (`/api/v1.0/fund/valuation`)
- **更新基金加仓** - `POST /changePosition`
- **基金加仓** - `POST /addPosition`
- **基金减仓** - `POST /decreasePosition`

### 4. 基金关系管理 (`/api/v1.0/fund/valuation`)
- **获取自选基金列表** - `POST /getUserWatchlistFunds`
- **获取持有基金列表** - `POST /getUserHoldingFunds`
- **新增自选基金** - `POST /addWatchlistFund`
- **新增持有基金** - `POST /addHoldingFund`

## 📊 数据模型

### 主要实体类
- **UserInfo** - 用户信息
- **AccountInfo** - 账户信息
- **FundInfo** - 基金信息
- **FundHolding** - 基金持仓
- **FundWatchlist** - 基金自选

## ⚙️ 配置说明

### 开发环境配置 (`config/application-dev.yml`)

### 错误处理

系统提供全局异常处理器 `GlobalExceptionHandler`：
- **BusinessException** - 业务异常（400）
- **其他异常** - 服务器错误（500）

## 🔧 核心特性

1. **分层架构** - Controller → Service → Mapper 清晰分层
2. **统一响应** - 标准化 API 响应格式
3. **异常处理** - 全局异常捕获和处理
4. **缓存支持** - Redis 缓存配置和优化
5. **安全认证** - JWT Token 身份验证
6. **请求拦截** - 自定义拦截器处理通用逻辑
7. **多环境支持** - dev/prod 环境隔离

## 📦 数据库表结构

主要数据表：
- `user_info` - 用户信息表
- `account_info` - 账户信息表
- `fund_info` - 基金信息表
- `fund_holding` - 基金持仓表
- `fund_watchlist` - 基金自选表

## 🤝 贡献指南

1. Fork 本仓库
2. 创建特性分支
3. 提交变更
4. 推送到分支
5. 创建 Pull Request

## 📄 许可证

查看 [LICENSE](LICENSE) 文件了解详情。

## 👤 作者

- **青灯文案**
- Author Email: rjw970725@163.com

## 📞 联系方式

如有问题或建议，请通过以下方式联系：
- 提交 Issue
- 发送邮件

---

**最后更新时间**: 2026-03-23


