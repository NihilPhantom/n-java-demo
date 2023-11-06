package org.example.sale;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;


@ComponentScans({
        @ComponentScan(basePackages = "org.example.sale.mapper"),
        @ComponentScan(basePackages = "org.example.sale.service"),
        @ComponentScan(basePackages = "org.example.sale.controller"),
})
@MapperScan(basePackages = "org.example.sale.mapper")
public class AutoImport {
    @Autowired
    private DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().
                        getResources("classpath:/META-INF/mapper/*.xml"));
        return sessionFactory.getObject();
    }
}
