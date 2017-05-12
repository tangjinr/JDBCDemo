package com.tangz.jdbcdemo;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class DBPoolDBCP extends Thread{

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private BasicDataSource ds = null;

    public DBPoolDBCP() {
        dbPoolInit();
    }

    private void dbPoolInit() {
        ds = new BasicDataSource();
        ds.setUrl(DB_URL);
        ds.setDriverClassName(JDBC_DRIVER);
        ds.setUsername(USER);
        ds.setPassword(PASSWORD);
        ds.setMaxTotal(2);
    }

    // 通过连接池获取连接
    private void dbPoolTest() {
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

    // 通过jdbc访问数据库
     void jdbcTest(){
        Connection conn = null;
        // Statement stmt = null;
        // 使用游标，Statement变为PrepareStatement
        PreparedStatement ptmt = null;
        ResultSet rs = null;

        try {
            //1.装载驱动程序
            Class.forName(JDBC_DRIVER);

            //2.建立数据库连接
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            //3.执行SQL语句,使用游标
            ptmt = conn.prepareStatement("select userName from user");
            ptmt.setFetchSize(1); // 读取1条记录作为一个批次
            rs = ptmt.executeQuery();

            //4.获取执行结果
            while (rs.next()) {
                System.out.println("Hello " + rs.getString("userName"));
            }
        } catch (Exception e) {
            //异常处理
            e.printStackTrace();
        } finally {
            //5.清理环境
            try {
                if (conn != null) {
                    conn.close();
                }
                if (ptmt != null) {
                    ptmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void run(){
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 10000) {
            // jdbcTest();
            dbPoolTest();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new DBPoolDBCP().start();
        }
    }

}
