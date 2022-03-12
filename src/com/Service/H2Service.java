package com.Service;

import com.Bean.H2.sourceStructure;
import com.Bean.Mongodb.MongodbColumns;
import com.Bean.Mysql.MysqlColumns;
import com.Bean.Source.Source;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public interface H2Service {
    Map<Connection, Statement> H2Connect(String JDBC_DRIVER, String DB_URL, String USER, String PASS);
    void H2Close(Connection conn,Statement stmt);

    void deleteSourceSetting(Statement stmt) throws SQLException;
    void createSourceSetting(Statement stmt) throws SQLException;
    void saveSourceSetting(Source source, Statement stmt) throws SQLException;
    List<Source> searchSourceSetting(Statement stmt) throws SQLException;
    void showSourceSetting(List<Source> sourceList);

    void deleteSourceStructure(String dbSource,Statement stmt) throws SQLException;
    void createSourceStructure(String dbSource,Statement stmt) throws SQLException;

    List<sourceStructure> searchStructure(Statement stmt, String dbSource) throws SQLException;
    void showStructure(List<sourceStructure> sourceStructureList);
    void saveSourceStructure(sourceStructure sourceStructure, String sourceName, Statement stmt) throws SQLException;

    sourceStructure fitMysqlStructure(Integer index, String source, String DbName, String tableName, List<MysqlColumns> mysqlColumnsList);
    sourceStructure fitMongodbStructure(Integer index, String source, String DbName, String tableName, List<MongodbColumns> mongoColumnsList);

}
