序列化：
1. 序列化必须要有getter方法，否则会报错，setter方法可以没有。
2. isXxx() 返回boolean 或 Boolean 类型的方法会，序列化成字段xxx。

反序列化：
1.反序列化getter setter 都没有也不会报错，但属性设置不进去，相当于new 了一个对象。但只需要有getter和setter方法中的一个，数据就能设置进去。

序列化和反序列化共用
1.默认情况下，Jackson通过将JSON字段的名称与Java对象中的getter和setter方法进行匹配，将JSON对象的字段映射到Java对象中的属性。Jackson删除了getter和setter方法名称的“ get”和“ set”部分，并将其余名称的第一个字符转换为小写
2. 注解：JacksonStandardObj中的注解：@JsonProperty, @JsonFormat

总结：
1.序列化最低要求有getter方法
2.反序列化最低要求有getter或setter方法。只有getter方法的话需要规范的getter方法(如字段name -> getName), 不规范的话设置不了值(因为没有setter方法用于设置值)。
有setter方法的话参考默认情况。
3.所以有规范的getter和setter方法会很省心。