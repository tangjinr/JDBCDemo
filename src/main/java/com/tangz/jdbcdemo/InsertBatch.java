package com.tangz.jdbcdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class InsertBatch {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public void insertUsers() throws ClassNotFoundException {

        Connection conn = null;
        Statement stmt = null;
        Set<String> users = getData();

        try {
            //1.装载驱动程序
            Class.forName(JDBC_DRIVER);

            //2.建立数据库连接
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            //3.执行SQL语句
            stmt = conn.createStatement();
            for (String user : users) {
                stmt.addBatch("insert into user(userName) values('" + user + "')");
            }
            stmt.executeBatch();
            stmt.clearBatch();

        } catch (SQLException e) {
            //异常处理
            e.printStackTrace();
        } finally {
            //5.清理环境
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Set<String> getData() {
        Set<String> users = new HashSet<>();
        users.add("tang");
        users.add("tang22");
        users.add("tang2222");
        return users;
    }
}
