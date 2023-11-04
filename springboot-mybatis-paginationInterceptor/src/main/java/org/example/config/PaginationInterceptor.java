package org.example.config;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.example.pojo.PaginationData;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

@Intercepts({
    @Signature(
            type = StatementHandler.class, method = "prepare",
            args = {
                    Connection.class,
                    Integer.class
            }
    )
})

public class PaginationInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        // 进行拦截判断
        Object parameterObject = statementHandler.getParameterHandler().getParameterObject();
        if(parameterObject==null){
            return invocation.proceed();
        }

        PaginationData paginationData = null;
        if ((parameterObject instanceof PaginationData)){
            paginationData = (PaginationData) parameterObject;
        }
        else if((((Map<?, ?>)parameterObject).get("param1") instanceof PaginationData)){
            paginationData = (PaginationData)(((Map<?, ?>)parameterObject).get("param1"));
        }
        if(paginationData==null){
            return invocation.proceed();
        }

        // 获取原始 SQL
        String originalSql = (String) metaObject.getValue("delegate.boundSql.sql");

        // 生成分页 SQL
        String paginatedSql = generatePaginatedSql(originalSql, paginationData);

        // 设置分页 SQL
        metaObject.setValue("delegate.boundSql.sql", paginatedSql);

        // 继续执行原始的 prepare 方法
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        // 生成object对象的动态代理对象
        return Plugin.wrap(o, this);
    }


    @Override
    public void setProperties(Properties properties) {
        // 可以在这里配置一些参数

    }

    private String generatePaginatedSql(String originalSql, PaginationData paginationData) {
        StringBuilder paginatedSql = new StringBuilder();

        // 添加 LIMIT 子句限制每条语句长度
        paginatedSql.append(originalSql.trim());
        paginatedSql.append(" LIMIT " + paginationData.getPageSize());

        // 添加 OFFSET 子句查询偏移
        paginatedSql.append(" OFFSET " + ((paginationData.getCurrentPage()-1)*paginationData.getPageSize()));

        return paginatedSql.toString();
    }
}

//public class PaginationInterceptor implements Interceptor {
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        Object[] args = invocation.getArgs();
//        MappedStatement mappedStatement = (MappedStatement) args[0];
//        Object parameterObject = args[1];
//        org.apache.ibatis.session.RowBounds rowBounds = (org.apache.ibatis.session.RowBounds) args[2];
//
//        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
//        BoundSql boundSql = statementHandler.getBoundSql();
//
//        if(parameterObject instanceof PaginationData){
//
//        }
//
////        // 获取目标方法
////        Method method = invocation.getMethod();
////
////        // 判断方法上是否存在 @NeedPagination 注解
////        if (method.isAnnotationPresent(PageCut.class)) {
////            System.out.println("拦截成功");
////        }
//
//        // 继续执行原始的方法
//
//
//
//        Object res = invocation.proceed();
//
//        return res;
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//        // 可以在此处设置插件的属性
//    }
//
//    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
//        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
//        builder.resource(ms.getResource());
//        builder.fetchSize(ms.getFetchSize());
//        builder.statementType(ms.getStatementType());
//        builder.keyGenerator(ms.getKeyGenerator());
//        builder.keyProperty(String.join(",", ms.getKeyProperties()));
//        builder.timeout(ms.getTimeout());
//        builder.parameterMap(ms.getParameterMap());
//        builder.resultMaps(ms.getResultMaps());
//        builder.resultSetType(ms.getResultSetType());
//        builder.cache(ms.getCache());
//        builder.flushCacheRequired(ms.isFlushCacheRequired());
//        builder.useCache(ms.isUseCache());
//        return builder.build();
//    }
//
//}