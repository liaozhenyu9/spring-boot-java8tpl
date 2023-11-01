序列化：
1.使用@JsonValue 注解。 需要注意的是@JsonValue也会作用到反序列化但有坑,就是输入0 跟 “0” 都会解析，0 会解析为第0个枚举。
所以反序列化注意一定加上@JsonCreator，反序列化@JsonCreator优先级会高于@JsonValue


反序列化：
1.使用@JsonCreator注解


总结：
1.尽量一定要有@JsonValue 和 @JsonCreator 两个注解。