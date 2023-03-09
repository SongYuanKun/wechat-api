# 博客后台

## 博客界面

[https://github.com/SongYuanKun/vue-blog]

## 管理平台

[https://github.com/SongYuanKun/vue-admin]

# DDD改造

## 包结构改造

目标报结构

```
com.songyuankun
├── application               // 应用层
│   ├── assembler            // 转换层，负责将领域模型转换为DTO
│   ├── command              // 命令实现，负责实现具体的业务逻辑
│   ├── dto                  // DTO，数据传输对象，与表现层进行数据交互
│   ├── event                // 事件实现，负责处理领域事件
│   ├── handler              // 处理器，用于处理命令和查询
│   ├── query                // 查询实现，负责查询具体的数据
│   └── service              // 应用服务实现，负责处理跨聚合根的业务逻辑
├── domain                    // 领域层
│   ├── event                // 事件实现，定义领域事件
│   ├── aggregate            // 领域聚合根，负责维护一组相关的领域对象
│   ├── model                // 领域模型实现，负责实现领域对象
│   ├── repository           // 领域仓储接口和实现，用于持久化和查询领域对象
│   └── service              // 领域服务实现，用于实现领域逻辑，跨聚合根的逻辑不在此处实现
├── infrastructure            // 基础设施层
│   ├── config               // 配置相关，包括应用和基础设施的配置
│   ├── datasource           // 数据源配置和实现，负责与数据库进行交互
│   ├── messaging            // 消息队列配置和实现，负责与消息队列进行交互
│   ├── persistence          // 持久化相关配置和实现，提供与持久化层的交互
│   ├── web                  // web相关配置和实现，提供与web相关的交互
│   └── util                 // 工具类，提供一些常用的工具类
└── presentation              // 表现层
    ├── controller           // 控制器实现，处理HTTP请求和响应
    ├── dto                  // 数据传输对象，与应用层进行数据交互
    ├── exception            // 异常处理，处理各种异常
    └── view                 // 视图实现，用于展示数据
```

