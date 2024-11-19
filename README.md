# QRPC 框架

*RPC（RemoteProcedureCall）即远程过程调用，是一种计算机通信协议，它允许程序在不同的计算机之间进*
*行通信和交互，就像本地调用一样。*

## 项目简介

QRPC 项目允许一个程序（称为服务消费者）像调用自己程序的方法一样，调用另一个程序（称为服务提供者）的接口，而不需要了解数据的传输处理过程、底层网络通信的细节等。这些都会由RPC框架完成，使得开发者可以轻松调用远程服务快速开发分布式系统。

## 工作原理

大致的调用流程如下图所示：



<img src="https://images73.oss-cn-beijing.aliyuncs.com/img/4529092a-37d8-4289-89f5-54477439418f.svg" alt="4529092a-37d8-4289-89f5-54477439418f" style="zoom:150%;" />

消费者调用接口方法，利用动态代理生成接口的代理对象，代理对象利用请求客户端向提供者发送序列化后的请求。提供者的 web 服务器接收到请求后，利用请求处理器将请求反序列化，拿到请求中封装的服务名称、方法名、方法参数类型、具体参数等信息。利用反射调用服务的实现类的方法，拿到返回值，然后再将值封装为响应，发送给消费者。

## 模块介绍

- **序列化模块**：Java 对象不便于在网络传输，所以需要将 Java 对象转化为字节流。

- **注册中心模块**：提供者注册地址等信息到注册中心，消费者从注册中心获取信息，得到地址，通过地址对提供者发起调用

  <img src="https://images73.oss-cn-beijing.aliyuncs.com/img/9cf1fd72-15b0-4075-acd3-e7924ef2dae9.svg" alt="9cf1fd72-15b0-4075-acd3-e7924ef2dae9" style="zoom:150%;" />

- **负载均衡模块**：如果有多个服务节点，利用负载均衡可以减轻单个节点的压力，使服务请求比较均匀的分布到各个节点上，增加系统的并发量

- **重试机制模块**：有时可能会调用接口失败，为了提高系统的可用性，可以进行重试。

- **容错机制模块**：提高系统的可靠性和健壮性，比如重试一定次数后仍然失败，接下来由容错机制来处理。

- **启动机制模块**：让框架更加易用，帮助开发者最少只用一行代码就能使用框架。

- **自定义协议**：HTTP 是一个更通用的协议，比较臃肿，而 RPC 模块是定制的，可以自定义更精简的请求头和响应头，减小体积，提高传输效率。

- **SPI 机制**：SPI 机制允许服务提供者通过特定的配置文件将自己的实现注册到系统中，然后系统通过反射机制动态加载这些实现，而不需要修改原始框架的代码，从而实现了系统的解耦、提高了可扩展性。

## 文件结构

```
├── example-common				 # 公共模块
├── example-consumer			 # 消费者（测试用）
├── example-provider			 # 提供者（测试用）
├── example-springboot-consumer  # 基于 springboot 的消费者 （测试用）
├── example-springboot-provider  # 基于 springboot 的提供者（测试用）
├── q-rpc-core					 # RPC 框架
├── q-rpc-easy					 # 简易的 RPC 框架
└── q-rpc-spring-boot-starter    # RPC 框架的启动器
```

## 快速开始

**提供者**

```java
@SpringBootApplication
@EnableRpc // 入口类加上这个注释
public class ExampleSpringbootProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleSpringbootProviderApplication.class, args);
    }

}

@Service
@RpcService // 提供服务的实现类加上这个注释
public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
```

**消费者**

```java
@SpringBootApplication
@EnableRpc(needServer = false) // 入口类加上这个注释
public class ExampleSpringbootConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleSpringbootConsumerApplication.class, args);
	}

}

@Service
public class ExampleServiceImpl {

    /**
     * 使用 Rpc 框架注入
     */
    @RpcReference // 需要调用的提供者的接口加上这个注释
    private UserService userService;

    /**
     * 测试方法
     */
    public void test() {
        User user = new User();
        user.setName("yupi");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }
}
```

## 配置指南

### 配置文件示例（消费者和提供者的 `application.properties` 文件）

```properties
rpc.name=qrpc
rpc.version=2.0
rpc.mock=false
rpc.serializer=kryo
```

## 许可证

该项目遵循 MIT 许可证 - 请参阅 LICENSE 文件了解详细信息。