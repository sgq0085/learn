package com.gqshao.h2;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Set;

public class H2Service {

    private static final Logger logger = LoggerFactory.getLogger(H2Service.class);

    //h2 database settings
    private static final String DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:file:~/.h2/test;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private static final String PREV_SELECT = "SELECT pin FROM pins WHERE pin in(";
    private static final String PREV_INSERT = "INSERT INTO pins(pin) VALUES";
    private static final int NUM = 10000;


    public static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void create() {
        Connection conn = getConn();
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            String DropTable = "DROP TABLE IF EXISTS PINS";
            int DropTableResult = stmt.executeUpdate(DropTable);
            logger.info("DropTableResult = {} == 0", DropTableResult);
            String createTable = "CREATE TABLE IF NOT EXISTS PINS (pin varchar(32) PRIMARY KEY)";
            int createTableResult = stmt.executeUpdate(createTable);
            logger.info("createTableResult = {} == 0", createTableResult);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Set<String> removeRepeat(Set<String> pins) {
        Connection conn = getConn();
        StringBuilder builder = null;
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            // 去重
            int i = 0;
            builder = new StringBuilder(PREV_SELECT);
            Set<String> repeatPins = Sets.newHashSet();
            for (String pin : pins) {
                if (i == 0) {
                    i++;
                    builder.append("'").append(pin).append("'");
                } else {
                    i++;
                    builder.append(",'").append(pin).append("'");
                    if (i == NUM) {
                        builder.append(")");
//                        logger.info(builder.toString());
                        ResultSet queryResult = stmt.executeQuery(builder.toString());
                        while (queryResult.next()) {
                            String rrp = queryResult.getString(1);
                            repeatPins.add(rrp);

                        }
                        i = 0;
                        builder = new StringBuilder(PREV_SELECT);
                    }
                }
            }
            // 处理部分
            if (builder.length() > PREV_INSERT.length()) {
                builder.append(")");
//                logger.info(builder.toString());
                ResultSet queryResult = stmt.executeQuery(builder.toString());
                while (queryResult.next()) {
                    pins.remove(queryResult.getString(1));
                }
            }
            // 去重
            pins.removeAll(repeatPins);

            // 插入数据库
            i = 0;
            builder = new StringBuilder(PREV_INSERT);
            for (String pin : pins) {
                if (i == 0) {
                    i++;
                    builder.append("('").append(pin).append("')");
                } else {
                    i++;
                    builder.append(",('").append(pin).append("')");
                    if (i == NUM) {
//                        logger.info(builder.toString());
                        stmt.executeUpdate(builder.toString());
                        // int insertDataResult = stmt.executeUpdate(builder.toString());
                        // logger.info("insertDataResult = " + insertDataResult + "== 0");
                        i = 0;
                        builder = new StringBuilder(PREV_INSERT);
                    }
                }
            }
            // 处理剩余部分
            if (builder.length() > PREV_INSERT.length()) {
//                logger.info(builder.toString());
                stmt.executeUpdate(builder.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (builder != null) {
                logger.error(builder.toString());
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
