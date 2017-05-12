package com.tangz.jdbcdemo;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class DBPool {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private BasicDataSource ds = null;

    public DBPool() {
        dbPoolInit();
    }

    private void dbPoolInit() {
        ds = new BasicDataSource();
        ds.setUrl(DB_URL);
        ds.setDriverClassName(JDBC_DRIVER);
        ds.setUsername(USER);
        ds.setPassword(PASSWORD);
    }

    public void dbPoolRun() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = ds.getConnection();

            stmt = conn.createStatement();

            rs = stmt.executeQuery("select * from user");

            while (rs.next()) {
                System.out.println("Hello " + rs.getString("userName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
