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

### 基础

#### 命令

项目中定义的通信基本单元为`RemoteCommand`，业务方可通过继承`RemoteBaseCommand`实现自己的远程命令，为了方便起见目前加载全路径下实现类，只需要提供实现类即可。如：

```java
public class RpcRequest extends RemoteBaseCommand{

	@Override
	public boolean responsible() {
		return true;
	}

	@Override
	public int requestCode() {
		return 55;
	}

	@Override
	public RemotingCommandType remotingCommandType() {
		return RemotingCommandType.REQUEST_COMMAND;
	}

}
```

```java
public class RpcResponse extends RemoteBaseCommand {

	@Getter
	@Setter
	private String payload;
	
	@Override
	public boolean responsible() {
		return false;
	}

	@Override
	public int requestCode() {
		return -55;
	}

	@Override
	public RemotingCommandType remotingCommandType() {
		return RemotingCommandType.RESPONSE_COMMAND;
	}

}
```


框架通过`requestCode`匹配到对应的处理器你。

#### 命令处理器

所有的指令都通过实现`RemotingRequestProcessor`接口进行处理。


#### 通信模型

async、sync、oneway

消息处理使用Akka作为业务线程池进行异步处理。

### 示例

```java
public static void main(String[] args) {
		
	SimpleServer simpleServer = new SimpleServer(ServerConfig.create(8888));
	
	simpleServer.registerHandler(55, new RemotingRequestProcessor<RpcRequest, RpcResponse>() {
	
		@Override
		public RpcResponse handler(ChannelHandlerContext ctx, RpcRequest request) {
			RpcResponse rpcResponse = new RpcResponse();
			rpcResponse.setPayload("hello rpc");
			return rpcResponse;
		}
	});
	
	simpleServer.start();
	}
```


```java
public static void main(String[] args) throws RemotingSendRequestException, RemotingTimeoutException {
		
	SimpleClient simpleClient = new SimpleClient(ClientConfig.create().build());
	
	
	String addr = "127.0.0.1:8888";
	simpleClient.connectWithRetry(addr);
	
	RpcRequest rpcRequest = new RpcRequest();
	
	simpleClient.invokeOneway(addr, rpcRequest);
	
	RemotingCommand response = simpleClient.invokeSync(addr, rpcRequest, 1000);
	System.out.println(response.toJSON());
	
	simpleClient.invokeAsync(addr, rpcRequest, new InvokeCallback() {
		@Override
		public void onRequestException(RemotingFuture remotingFuture) {
			System.err.println("onRequestException .. " + remotingFuture.getBeginTimestamp());
		}
		@Override
		public void onReceiveResponse(RemotingFuture remotingFuture) {
			System.out.println("onReceiveResponse .. " + remotingFuture.getResponseCommand());
		}
	});
}

```
