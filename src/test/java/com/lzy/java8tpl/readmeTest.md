## 1.最简单使用
 * 只需要一个注解@SpringBootTest即可使用.
 * 确保有正确的启动类，默认的启动环境配置，测试类会自动去加载。

## 2. 较完整的使用
 * @SpringBootTest(classes = SpringBootJava8tplApplication.class)  //指定SpringBoot项目的启动类
 * @ActiveProfiles("REL")  //指定项目使用的配置文件

## 3. 断言库
 * 导入: import static org.assertj.core.api.Assertions.assertThat; 使用方法如下：
 ````
 String actual = "Hello, World";
 String expected = "Hello, World";
 assertThat(actual).isEqualTo(expected); // 断言 actual 等于 expected
 
 //还可以使用 isNotNull(), isInstanceOf(), contains(), isGreaterThan(), 等方法来构建你的断言
 ````


## 总结
 * 一般情况下就用@SpringBootTest(classes = SpringBootJava8tplApplication.class) 和 @ActiveProfiles("REL") 这俩注解就行
 * @Test使用JUnit 5的org.junit.jupiter.api.Test