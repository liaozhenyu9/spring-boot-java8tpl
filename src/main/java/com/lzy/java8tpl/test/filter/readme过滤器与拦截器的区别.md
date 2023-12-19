## 过滤器和拦截器的区别
参考链接，里面有几张比较经典的图片：https://blog.csdn.net/weixin_50205273/article/details/109547019
* 过滤器包着servlet容器包着拦截器包装控制器
* 先调用Filter pre 方法 -> Servlet service() 方法 -> 拦截器preHandle方法 -> controller -> 拦截器的postHandle -> 拦截器的afterCompletion -> Filter After方法


## 统一接口日志打印
参考博客：https://mp.weixin.qq.com/s/SEmj4t_Ih5wJPL6MLoKi_w


## 如何在过滤器中日志打印请求和返回参数
参考：https://www.springcloud.io/post/2022-03/record-request-and-response-bodies/#gsc.tab=0
及：https://www.baeldung.com/spring-reading-httpservletrequest-multiple-times
