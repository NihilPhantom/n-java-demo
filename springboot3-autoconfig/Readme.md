# Springboot3 自动注入

本案例实现了引入 jar 包时自动注入 Controller 和 Service， 

其中user-server 提供Controller 和 Service，而entrance-server 提供了Main方法。

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
本项目在打包时，需要从根目录进行打包，先打包 user-server，再打包entrance-server 会报错。

经过测试，如果在 user-server 也添加application.yml， 是不会在 entrance-server 中生效的。