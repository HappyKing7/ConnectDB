package com.Service;

import com.Bean.Mysql.MysqlColumns;
import com.Bean.Mysql.MysqlDB;
import com.Bean.H2.sourceStructure;
import com.Bean.Mysql.MysqlTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public interface MysqlService {
    Map<Connection, Statement> MysqlConnect(String JDBC_DRIVER, String DB_URL, String USER, String PASS);
    void MysqlClose(Connection conn,Statement stmt) ;
    List<MysqlDB> findMysqlDB(Statement stmt) throws SQLException;
    List<MysqlTable> findMysqlTable(Statement stmt,String dbName) throws SQLException;
    List<MysqlColumns> findMysqlColumns(Statement stmt,String dbName,String tableName) throws SQLException;
}
