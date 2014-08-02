package com.gqshao.test.commons.persistence.interceptor;

import java.sql.*;
import java.util.*;

import org.apache.ibatis.executor.parameter.*;
import org.apache.ibatis.executor.statement.*;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.*;
import org.springframework.stereotype.*;

import com.gqshao.test.commons.persistence.domain.*;
import com.gqshao.test.commons.utils.*;

@Component
@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class PageInterceptor implements Interceptor {

    /**
     * 拦截后要执行的方法
     */

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler delegate = (StatementHandler) Reflections.getFieldValue(handler, "delegate");
        BoundSql boundSql = delegate.getBoundSql();
        Object obj = boundSql.getParameterObject();
        if (obj instanceof BaseFilter) {
            BaseFilter baseFilter = (BaseFilter) obj;
            MappedStatement mappedStatement = (MappedStatement) Reflections.getFieldValue(delegate,
                    "mappedStatement");
            mappedStatement.getResultSetType();
            mappedStatement.getResultMaps();
            Connection connection = (Connection) invocation.getArgs()[0];
            String sql = boundSql.getSql();
            this.setTotalRecord(baseFilter, mappedStatement, connection);
            String pageSql = this.getPageSql(baseFilter, sql);
            Reflections.setFieldValue(boundSql, "sql", pageSql);
            BaseFilter.setLocal(baseFilter);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 给当前的参数对象page设置总记录数
     * 
     * @param page Mapper映射语句对应的参数对象
     * @param mappedStatement Mapper映射语句
     * @param connection 当前的数据库连接
     */
    private void setTotalRecord(BaseFilter filter, MappedStatement mappedStatement, Connection connection) {
        BoundSql boundSql = mappedStatement.getBoundSql(filter);
        String sql = boundSql.getSql();
        String countSql = this.getCountSql(sql);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql,
                parameterMappings, filter);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, filter,
                countBoundSql);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int totalRecord = rs.getInt(1);
                filter.setRecords(totalRecord);
                int rows = filter.getRows();
                // 计算总页数
                int total = totalRecord / rows;
                total = totalRecord % rows == 0 ? total : total + 1;
                filter.setTotal(total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据原Sql语句获取对应的查询总记录数的Sql语句
     * @param sql
     * @return
     */
    private String getCountSql(String sql) {
        return "select count (1) from (" + sql + ")";
        // int index = sql.indexOf("from");
        // return "select count(1) " + sql.substring(index);
    }

    /**
     * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle 其它的数据库都 没有进行分页
     * 
     * @param filter 条件对象
     * @param sql 原sql语句
     * @return
     */
    private String getPageSql(BaseFilter filter, String sql) {
        Integer page = filter.getPage() < 1 ? 1 : filter.getPage();
        Integer rows = filter.getRows();
        StringBuffer sqlBuffer = new StringBuffer(sql);
        if (page == 1 && rows == 1) {
            sqlBuffer.insert(0, "select * from (").append(") where rownum=1 ");
        } else {
            int offset = (page - 1) * rows + 1;
            sqlBuffer.insert(0, "select result_temp_table.*, rownum row_now from (")
                    .append(") result_temp_table where rownum < ").append(offset + rows);
            sqlBuffer.insert(0, "select * from (").append(") where row_now >= ").append(offset);
        }
        return sqlBuffer.toString();
    }
}
