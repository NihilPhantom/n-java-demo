package org.example.config;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO 由于在拦截器中，只能获取到方法名，无法获取到注解，使用注解的方法暂且搁置了
/**
 * Java 提供了三种注解的保留策略：
 *
 * RetentionPolicy.SOURCE：源代码级别保留策略。该注解只保留在源代码中，编译后的字节码文件中不包含该注解。这种注解通常用于提供给编译器或其他工具使用，对运行时没有任何影响。
 *
 * RetentionPolicy.CLASS：类级别保留策略。该注解保留在编译后的字节码文件中，但在运行时不可通过反射获取。这种注解通常用于编译时的一些处理，对运行时没有直接影响。
 *
 * RetentionPolicy.RUNTIME：运行时保留策略。该注解保留在编译后的字节码文件中，并且在运行时可以通过反射机制获取注解的信息。这种注解通常用于在运行时进行一些动态的处理，例如自定义注解处理器、AOP（面向切面编程）等。
 */
@Retention(RetentionPolicy.RUNTIME)  // 配置 保留策略， 因为要用到反射获取
@Target(ElementType.METHOD)  // 配置注解的目标是函数
public @interface PageCut {

}
