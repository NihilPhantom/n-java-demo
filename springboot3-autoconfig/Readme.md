# Springboot3 自动注入

本案例实现了引入 jar 包时自动注入 Controller 和 Service

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
