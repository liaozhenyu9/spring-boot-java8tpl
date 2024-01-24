## 安装kafka
参考博客：https://zhuanlan.zhihu.com/p/403783402
* 检查是否安装docker：执行查看镜像列表列表命令：docker images
* 检查端口是否被占用：netstat -tuln | grep 9090  没输出就是没被占用 （"-tuln"选项表示显示TCP和UDP的监听端口，而"grep 9090"用于过滤包含9090的行。如果输出中有结果，表示9090端口被占用。）
* 这里安装wurstmeister团队维护的kafka镜像，使用docker search 命令到官方公共仓库Docker Hub 上查找一下该镜像：docker search wurstmeister/kafka
* 拉取zookeeper和kafka镜像：docker pull wurstmeister/zookeeper , docker pull wurstmeister/kafka
* 启动zookeeper容器：docker run -d --name zookeeper -p 2181:2181 -t wurstmeister/zookeeper , 启动后用命令docker container ls -a 查看容器启动状态
* 启动kafka容器：docker run  -d --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=120.25.177.66:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://120.25.177.66:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -t wurstmeister/kafka
* 开放服务器的2181端口, 若kafka启动失败, 通过命令 docker logs kafka 查看日志
* docker ps 查看启动的容器获得容器id, docker exec -it ae81c2496add /bin/bash 进入容器，在里面的/opt/kafka里可以执行一些命令测试kafka
* 使用图像化工具Offset Explorer连接kafka

## 使用Spring Cloud Stream使用kafka
参考博客：https://juejin.cn/post/6931566219979915277
* 这里先考虑使用spring cloud stream, 也可以使用spring-kafka包。
* 获取文档，google搜索Spring Cloud Stream，找到官网链接：https://spring.io/projects/spring-cloud-stream#learn
* 根据springboot版本找到对应合适的版本，在LEARN标签菜单下，找到该版本，点击Reference Doc 进入开放文档：https://docs.spring.io/spring-cloud-stream/docs/3.2.10/reference/html/spring-cloud-stream-binder-kafka.html#_apache_kafka_binder
* 发现文档里有Apache Kafka Binder 和 Kafka Streams Binder 两种方式分别对应两个pom，两个都可以用，这里先用Apache Kafka Binder
* SpringCloud 3.1版本之后，@EnableBinding、@Output等StreamApi注解都标记为废弃，改用函数式编程方式

### 主要用到了3个函数式接口
说明参考：https://docs.spring.io/spring-cloud-stream/docs/3.1.0/reference/html/spring-cloud-stream.html#spring-cloud-stream-overview-producing-consuming-messages
Spring Cloud Function提供的支持：从 Spring Cloud Stream v2.1，可以使用Spring Cloud Function 内置支持的三个函数式接口来定义 stream handlers 和 sources，然后配置spring.cloud.function.definition
来说明哪个Functional bean 对应哪个binding
Supplier Function Consumer
函数式接口是什么：函数式接口就是一个有且仅有一个抽象方法，但是可以有多个非抽象方法的接口。函数式接口可以被隐式转换为 lambda 表达式。
* Supplier<T> : 无参数，返回一个结果。
* Function<T,R> : 接受一个输入参数，返回一个结果。
* Consumer<T> : 代表了接受一个输入参数并且无返回的操作。

### 集成步骤(函数式编程)
1. 引入依赖spring-cloud-stream-binder-kafka 或 spring-cloud-starter-stream-kafka
````
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-stream-kafka</artifactId>
</dependency>
````
2. 配置Binder： 之前已经讲了一个kafka实例或者rabbitMQ实例其实就是一个binder，那你现在有了一个kafka，要如何告诉Spring Cloud呢？
最简单的就是通过配置文件，配置文件配置Binder的思想很简单，就是告诉Spring Cloud Stream，我要创建一个Binder，这个Binder的类型是kafka或者rabbitMQ，然后它的IP，端口都是啥以及用户名密码等都是啥就好了。
方式一：只有一个kafka实例
````yaml
spring:
  cloud:
    stream:
      kafka:
        binder:
          # kafka的Ip和端口，可以是集群, (集群的话用逗号分隔如：172.17.0.2:9092,172.17.0.3:9092,172.17.0.4:9092)
          brokers: ip:port
````
方式二：有多个kafka实例
````yaml
spring:
  cloud:
    stream:
      binders:
        # 你的binder名字，自己随意取，我取的名字叫myKafka
        myKafka:
          # 你的binder类型，我们这里类型是kafka
          type: kafka
          # 下面的环境配置与上面的一模一样
          environment:
            spring:
              cloud:
                stream:
                  kafka:
                    binder:
                      # kafka的Ip和端口，可以是集群
                      brokers: ip:port
````
3. 用函数式接口Consumer写个消费者，因为函数式接口Supplier写的生产者会每秒自动发送消息正常情况下用不上(一般使用StreamBridge)，而函数式接口Function用于接收一个消息处理后再产生一个消息也用的少。
```java
@Configuration
@Slf4j
public class KafkaFunctionalConfig {
    @Bean
    public Consumer<String> consumer1() {
        return str -> {
            MDCUtils.setRequestId();
            log.info("consume1:{} + time:{}", str.toUpperCase(), JSON.toJSONString(LocalDateTime.now()));
            throw new RuntimeException();
        };
    }
    
    @Bean
    public Consumer<String> consumer2() {
        return str -> {
            log.info("consume2:" + str.toUpperCase());
        };
    }
}
```
4. 写完这些Bean后,还需要将它写到配置文件，告诉Spring Cloud，这些都是用于函数处理的Bean
```yaml
spring:
  cloud:
    function:
      definition: consumer1;consumer2  #告诉Spring Cloud，这些都是用于函数处理的Bean

```
5. 配置bindings: binding就是一座桥将业务函数和Binder连接起来。Binding的命名是：<functionName>-in/out-<index>
```yaml
spring:
  cloud:
    stream:
      bindings:
        consumer1-in-0:
          destination: supplierTest1
          group: consumer1Group
        consumer2-in-0:
          destination: supplierTest2
          group: consumer2Group
```
它的Binding名字就是consumer1-in-0。其中consumer1是函数名（也是Bean名），in代表这个Binding是写入的，而index是输入或输出绑定的索引。
对于典型的单个输入/输出函数，它始终为 0，因此它仅与具有多个输入和输出参数的函数相关（一个函数被多次作为输出/输出，比如这个函数被kafka和rabbitMQ都作为输出，那就是一个index0一个index1）。

6. 配置生产者binding
```yaml
spring:
  cloud:
    stream:
      supplier1-out-0:
        destination: supplierTest1
        supplier2-out-0:
          destination: supplierTest2
```
7. 用StreamBridge使用生产者binding发送消息
```java
@RestController
@RequestMapping("kafkaTest")
public class KafkaTestController {

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping("1")
    public R test1() {
        streamBridge.send("supplier1-out-0", "aaaaaaaaaaaaaaaaaaaaa");
        streamBridge.send("supplier2-out-0", "bbbbbbbbbbbbbbbbbbbbb");
        return RHelper.success();
    }
}
```

## 总结
springCloud3.1 后推荐使用内置的函数式编程的方式，原注解方式标记成了废弃但也可以用。
一个binder就代表一个kafka实例，binding 就是用于连接业务函数和binder的桥，broker代表一个kafka服务器，比如集群情况下一个kafka binder 对应多个broker。
生产消息使用StreamBridge，消费消息使用Consumer，Supplier和Function很少用到。
消费者写法：@Bean创建个Consumer 里面写消费逻辑，然后将bean名称配到spring.cloud.function.definition 中，最后再配置binding。
生产写法：先配置一个生产者biding，然后使用StreamBridge往这个binding发送消息。