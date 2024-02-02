## 1.在看一个“实际项目中如何使用线程池”的帖子其中一个人的回答
1.Bean 的方式创建、管理线程池
2.不同的业务使用不同的线程池
3.CompletableFuture 用起来很方便
目前我是这样用 没出过啥问题
```java
@Configuration
public class ThreadPoolConfig {

    @Bean(destroyMethod = "shutdown", name = "xxxxxThreadPool")
    public ThreadPoolExecutor xxxxxThreadPool() {
        ThreadFactory tf =
                new ThreadFactoryBuilder().setNameFormat("xxxxxThreadPool-%d").build();
        return new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 2,
                xxx,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(xxxx),
                tf,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Bean(destroyMethod = "shutdown", name = "xxxxxThreadPool")
    public ThreadPoolExecutor xxxxxThreadPool() {
    }

}
```