package com.tangz.jdbcdemo;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transaction {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test?useCursorFetch=true";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private BasicDataSource ds = null;

    public Transaction() {
        ds = new BasicDataSource();
        ds.setUrl(DB_URL);
        ds.setDriverClassName(JDBC_DRIVER);
        ds.setUsername(USER);
        ds.setPassword(PASSWORD);
    }

    public void transferAccount() throws ClassNotFoundException {

        Connection conn = null;
        PreparedStatement ptmt = null; // 使用游标

        try {

            conn = ds.getConnection();
            ptmt = conn.prepareStatement("UPDATE USER SET account = ? WHERE userName = ?");

            // 设置张三账户为0元
            ptmt.setInt(1, 0);
            ptmt.setString(2, "ZhangSan");
            ptmt.execute();
            // 设置李四账户为100元
            ptmt.setInt(1, 100);
            ptmt.setString(2, "LiSi");
            ptmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (ptmt != null) {
                    ptmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
