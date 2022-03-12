package com.Dao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class H2Dao {
    public Map<Connection, Statement> H2Connect(String JDBC_DRIVER, String DB_URL, String USER, String PASS)
    {
        Map<Connection,Statement> mp = new HashMap<Connection,Statement>();
        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            mp.put(conn,stmt);
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
        return mp;
    }

    public void H2Close(Connection conn,Statement stmt)
    {
        try{
            if(stmt!=null) stmt.close();
        }catch(SQLException se2){
        }
        try{
            if(conn!=null) conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }

    public void updateH2(Statement stmt,String sql) throws SQLException {
        stmt.executeUpdate(sql);
    }

    public ResultSet queryH2(Statement stmt,String sql) throws SQLException {
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }
}
