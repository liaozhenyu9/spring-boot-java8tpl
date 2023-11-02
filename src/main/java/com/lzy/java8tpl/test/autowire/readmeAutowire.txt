@Autowired  (由Spring提供)
1.@Autowired 默认先按类型找bean, 如果找到多个再根据名字获取bean注入。
    (如：@Autowired 修饰private AutowireDao autowireDao11; 时，如果容器里有两个AutowireDao类型的bean，但一个名字是autowireDao11，另一个名字是autowireDao22，则会根据名字注入autowireDao11。
    如果两个bean的名字都不叫autowireDao11，这时候就会报错)
2.若想直接使用某个名字获取bean注入，搭配@Qualifier("xxx")来指定bean名称。
3.或者两个同类型的bean，有一个使用了@Primary注解修饰，那就找到两个bean也不会按名字注入了，会选择这个@Primary修饰的bean注入。
4.非必须的bean可以使用@Autowired(required = false)注入

@Resource   (由J2EE提供)
1. 默认按照byName自动注入，当找不到与名称匹配的bean会按类型装配
2. 默认按照名称进行装配，名称可以通过name属性进行指定，如果没有指定name属性，当注解写在字段上时，默认取字段名进行名称查找。
3. 如果name属性一旦指定，就只会按照名称进行装配。


final + 构造器注入
1. 不能提供无参构造方法，否则会优先使用无参构造函数。无无参构造函数下，也不能提供多个有参构造函数，否则会报错。如果一定要提供多个有参构造函数，要用来注入bean的那个构造函数需要加上@Autowired
2. 官方推荐该方式，有如下优点：保证注入的组件不可变，确保需要的依赖不为空，解决循环依赖的问题(若有循环依赖会在项目启动时抛错)
3. 经测试发现跟@Autowired一样，默认先按类型找bean，如果找到多个再根据名字获取bean注入。
4. 一般搭配@RequiredArgsConstructor使用，同时对需要注入的属性用final关键词修饰。因为@RequiredArgsConstructor 注解会对标有 @NonNull 注解的非static变量和 final 非static变量生成构造函数，构造函数的参数顺序为各参数声明顺序。
5. @RequiredArgsConstructor 注意：1.final或@NonNull修饰的变量不能在定义的时候被初始化(如：private final AutowireDao autowireDao2 = new AutowireDao();)，否则不会出现在构造参数中。


将bean注入到静态属性中
1. 参考RestTemplateUtils类


总结：
1. 优先使用final + 构造器注入
2. 尽量一定统一使用final不用@NonNull，需要注入的final属性一定不能有初始值，不需要注入的final属性一定加上初始值。
3. 尽量一定只有一个构造函数，也就是只有@RequiredArgsConstructor生成的构造函数
