package com.tangz.jdbcdemo;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

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
        Savepoint sp = null;

        try {

            conn = ds.getConnection();
            conn.setAutoCommit(false); // 开启事务模式

            ptmt = conn.prepareStatement("UPDATE USER SET account = ? WHERE userName = ?");

            /*下面两个操作作为一个事务整体执行，不存在中间过程*/
            // 设置张三账户为0元
            ptmt.setInt(1, 0);
            ptmt.setString(2, "ZhangSan");
            ptmt.execute();
            sp = conn.setSavepoint(); // 设置检查点，保存断点
            // 设置李四账户为100元
            ptmt.setInt(1, 100);
            ptmt.setString(2, "LiSi");
            ptmt.execute();

            // conn.commit(); // 事务提交
            throw new SQLException(); // 为了展示检查点效果，人工抛出一个异常

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    // conn.rollback(); // 若出现异常，进行事务回滚操作
                    conn.rollback(sp); // 这里将事务回滚到保存的断点的位置：ptmt.setInt(1, 0);ptmt.setString(2, "ZhangSan");ptmt.execute();
                    ptmt.setInt(1, 100);
                    ptmt.setString(2, "ZhaoWu");
                    ptmt.execute();
                    conn.commit();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
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
