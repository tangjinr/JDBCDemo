package com.tangz.jdbcdemo;

import java.sql.*;

public class HelloJDBC {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    // 使用游标，在原来url上加上?useCursorFetch=true
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test?useCursorFetch=true";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public void helloword() throws ClassNotFoundException {
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

            //3.执行SQL语句
            // stmt = conn.createStatement();
            // rs = stmt.executeQuery("select userName from user");

            // 使用游标
            ptmt = conn.prepareStatement("select userName from user");
            ptmt.setFetchSize(1); // 读取1条记录作为一个批次
            rs = ptmt.executeQuery();

            //4.获取执行结果
            while (rs.next()) {
                System.out.println("Hello " + rs.getString("userName"));
            }
        } catch (SQLException e) {
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

}
