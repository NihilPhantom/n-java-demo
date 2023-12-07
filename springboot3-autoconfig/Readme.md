# Springboot3 自动注入

本案例实现了引入 jar 包时自动注入 Controller、Service、Mapper

## 目录说明
- entrance-server: 提供SpringBoot 的 Main 方法，是整个系统的入口
- user-server：通过直接注入类，实现了 Controller、Service 的注入
- sale-server：利用@ComponentScan，直接注入一个包下的所有组件，实现了Controller、Service、Mapper 的注入

## 特别说明
【SpringBoot2】

在 SpringBoot2 中 实现自动注入需要在 resources 下 添加`/META-INF/spring.factories` 文件。
文件内容如下：
```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=org.example.common.GlobalExceptionHandler
```

【Springboot3】

在 SpringBoot2 中 实现自动注入需要在 resources 下 
添加`/META-INF/spring/org.springframework.boot.autoconfigure.Autoconfiguration.imports` 文件，
文件内容如下：
```properties
org.example.common.GlobalExceptionHandler
```

## 注意事项
1、本项目在打包时，需要从根目录进行打包，先打包 user-server，再打包entrance-server 会报错。

2、经过测试，如果在 user-server 也添加 application.yml， 是不会生效的。

3、对于Pom中的构建配置，只有entrance-server需要加入打包的build 配置，其他的包无需额外配置

4、如果不同的模块都是在@SpringBootAppliction的注解的范围之下，则会通过Springboot的包扫描功能自动加载，而无需手动配置自动加载。