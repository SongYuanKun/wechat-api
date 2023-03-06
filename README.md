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
├── application             // 应用层
│   ├── command            // 命令实现
│   ├── query              // 查询实现
│   └── service            // 应用服务实现
├── domain                  // 领域层
│   ├── event              // 事件实现
│   ├── model              // 领域模型实现
│   ├── repository         // 领域仓储接口和实现
│   └── service            // 领域服务实现
├── infrastructure          // 基础设施层
│   ├── config             // 配置相关
│   ├── datasource         // 数据源配置和实现
│   ├── messaging          // 消息队列配置和实现
│   ├── persistence        // 持久化相关配置和实现
│   ├── web                // web相关配置和实现
│   └── util               // 工具类
└── presentation            // 表现层
├── controller         // 控制器实现
├── dto                // 数据传输对象
├── exception          // 异常处理
└── view               // 视图实现
```

