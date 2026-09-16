# Git_04

基于 RabbitMQ 的邮件管理系统，Maven 多模块项目，包含消息生产者/消费者、定时调度与邮件发送功能。

## 项目简介

本项目（Maven 坐标 `邮件管理系统3`）是一个 Java 消息队列练习项目，通过 RabbitMQ 实现邮件异步发送，结合 Quartz 定时任务调度，演示生产者-消费者模式的实际应用。

## 技术栈

| 类别 | 技术 |
|------|------|
| 语言 | Java 8/11 |
| 消息队列 | RabbitMQ（amqp-client 5.6.0） |
| 邮件 | JavaMail 1.5 |
| 调度 | Quartz 2.3.2 |
| 工具 | Fastjson 1.2.47 |

## 目录结构

```
Git_04/
├── pom.xml                    # 父 POM（packaging: pom）
├── 线程调度/                   # 子模块：线程与定时调度
├── src/main/java/
│   ├── 生产者/                 # RabbitMQ 消息生产者
│   │   ├── Producerq.java
│   │   ├── Fasinfuwuqi.java   # 发送服务器
│   │   └── Tining.java        # 定时任务
│   ├── 消费者/                 # RabbitMQ 消息消费者
│   ├── Json/                  # JSON 工具
│   └── util/                  # 通用工具
└── .gitignore
```

## 快速启动

```bash
git clone https://github.com/hubaolong3632/Git_04.git
cd Git_04

# 编译
mvn clean compile

# 需先启动 RabbitMQ 服务，再运行生产者/消费者
```

## 模块说明

| 模块 | 说明 |
|------|------|
| `生产者/` | 创建消息并投递到 RabbitMQ 队列 |
| `消费者/` | 监听队列并处理邮件发送 |
| `线程调度/` | Quartz 定时任务子模块 |

## 前置条件

- JDK 8+
- Maven 3.x
- RabbitMQ 服务（默认 localhost:5672）
