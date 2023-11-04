# SpringBoot 注解分页

## 原理说明

本项目使用Mybatis 的拦截器在SQL语句进行执行前，向sql语句中加入limit 语句实现分页功能。

为了实现方便分页插件的拦截，要求实现分页的组件需要在第一个参数传入 PaginationData 类型的参数

```java
public interface Interceptor {
  //是实现拦截逻辑的地方，内部要通过invocation.proceed()显式地推进责任链前进，也就是调用下一个拦截器拦截目标方法。
  Object intercept(Invocation invocation) throws Throwable;
  //就是用当前这个拦截器生成对目标target的代理
  Object plugin(Object target);
  //用于设置额外的参数，参数配置在拦截器的Properties节点里
 void setProperties(Properties properties);
}
```

## TODO 列表

1、在进行分页查询的同时返回

2、尝试使用注解来标识一个方法需要进行分页，虽然加上了注解对函数进行标识，仍然需要传参，这样反而多此一举，但是还是值得进行试验的


## 实现发现
当拦截器拦截在 StatementHandler 的 prepare 可以正常拦截，
但是在 StatementHandler 的 query 阶段拦截不会生效。