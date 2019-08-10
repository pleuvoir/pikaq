## pikaq

[![HitCount](http://hits.dwyl.io/pleuvoir/pikaq.svg)](http://hits.dwyl.io/pleuvoir/pikaq) 
[![GitHub issues](https://img.shields.io/github/issues/pleuvoir/pikaq.svg)](https://github.com/pleuvoir/pikaq/issues)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/pleuvoir/pikaq/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.pleuvoir/pikaq.svg?label=maven%20central)](https://oss.sonatype.org/#nexus-search;quick~pikaq)
[![Download](https://img.shields.io/badge/downloads-master-green.svg)](https://codeload.github.com/pleuvoir/pikaq/zip/master)

## 介绍

本项目是对网络通信框架netty、akka的封装，方便使用。

## 特性

- [x] 序列化与反序列化器
- [x] 基础通信模型
- [x] 自动断连和重连
- [x] 心跳
- [x] 自定义通讯协议
- [x] 自定义业务处理器
- [x] 异步业务线程

## 快速开始

### 引入依赖


```xml
<dependency>
  <groupId>io.github.pleuvoir</groupId>
  <artifactId>pikaq</artifactId>
  <version>${latest.version}</version>
</dependency>
```

### 自定义业务

项目启动时会扫描所有实现了`Initable`接口的类，并且按照优先级进行初始化行为。可以使用此特性实现命令包/业务处理器的注册。


#### 指令

项目中定义的通信基本单元为`RemoteCommand`，业务方可通过继承`RemoteBaseCommand`实现自己的远程命令。远程命令需要提供初始化路径，通过继承`RemoteCommandInitAdapter`类，告知用户远程命令所在的包，完成加载。

如下：

```java
public class PentaqCommandHandlerInit extends RemoteCommandInitAdapter {

	@Override
	protected String location() {
		return "io.pentaq.remoting.command";
	}
}

```

#### 指令处理器

所有的指令都通实现承`RemoteCommandProcessor`接口进行处理。此外，通过继承`CommandProcessorInitAdapter`来进行业务处理器的注册。

```java
public class UserCommandInitTest extends CommandProcessorInitAdapter {

	@Override
	public void init() {
		registerHandler(443, new RemoteCommandProcessor<RemoteCommand, RemoteCommand>() {
			@Override
			public RemoteCommand handler(ChannelHandlerContext ctx, RemoteCommand request) {
				System.out.println("park.");
				return null;
			}
		});
		
		registerHandler(444, new RemoteCommandProcessor<RemoteCommand, RemoteCommand>() {
			@Override
			public RemoteCommand handler(ChannelHandlerContext ctx, RemoteCommand request) {
				System.out.println("unpark~");
				return null;
			}
		});
	}

}
```

### QA

#### 为什么不直接扫描，而是需要指定路径？

为了减少扫描的时间，以及更清晰的知道目标类所在的位置。


