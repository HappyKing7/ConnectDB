package com.company;

import com.Bean.Mongodb.MongodbColumns;
import com.Bean.Mongodb.MongodbDB;
import com.Bean.Mongodb.MongodbTable;
import com.Bean.Mysql.MysqlColumns;
import com.Bean.Mysql.MysqlDB;
import com.Bean.H2.sourceStructure;
import com.Bean.Mysql.MysqlTable;
import com.Bean.Source.Source;
import com.Service.Servicelmpl.H2Servicelmpl;
import com.Service.Servicelmpl.MongodbServicelmpl;
import com.Service.Servicelmpl.MysqlServicelmpl;
import com.mongodb.client.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class Main {

    //H2
    static final String H2_JDBC_DRIVER = "org.h2.Driver";
    static final String H2_DB_URL = "jdbc:h2:D:/Study/Java/DBconnect/H2data/test";
    static final String H2_USER = "wangxin";
    static final String H2_PASS = "67813831";
    static H2Servicelmpl h2sl = new H2Servicelmpl();

    //Mysql
    static final String Mysql_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String Mysql_DB_URL = "jdbc:mysql://127.0.0.1:3306/";
    static final String Mysql_USER = "root";
    static final String Mysql_PASS = "Wx678138311*";
    static MysqlServicelmpl msql = new MysqlServicelmpl();


    //Mongodb
    static final String Mongodb_DB_URL = "localhost:27017";
    static final String Mongodb_USER = "wangxin";
    static final String Mongodb_PASS = "67813831";
    static MongodbServicelmpl mdsl = new MongodbServicelmpl();

    //Mysql连接
    public static void connectMysql(Statement h2Stmt) throws SQLException {
        //连接Mysql
        Map<Connection, Statement> mysqlMp = msql.MysqlConnect(Mysql_JDBC_DRIVER,Mysql_DB_URL,Mysql_USER,Mysql_PASS);
        Connection mysqlConn = null;
        Statement mysqlStmt = null;
        for(Connection c : mysqlMp.keySet()){ mysqlConn = c; }
        mysqlStmt = mysqlMp.get(mysqlConn);

        //创建Mysql数据源存储表
        h2sl.createSourceStructure("Mysql",h2Stmt);
        h2sl.deleteSourceStructure("Mysql",h2Stmt);

        //获取Mysql的数据库结构
        List<MysqlDB> mysqlDBList = msql.findMysqlDB(mysqlStmt);    //获取Mysql的数据库
        for(int i=4 ; i<mysqlDBList.size() ; i++)
        {
            String dbName = mysqlDBList.get(i).getDatabase();
            List<MysqlTable> mysqlTableList = msql.findMysqlTable(mysqlStmt,dbName);
            //获取Mysql的数据库的表
            for (int j=0 ; j<mysqlTableList.size() ; j++)
            {
                String tableName = mysqlTableList.get(j).getTABLE_NAME();
                List<MysqlColumns> mysqlColumnsList = msql.findMysqlColumns(mysqlStmt,dbName,tableName);
                //获取Mysql的数据库的表的col
                for (int k=0 ; k<mysqlColumnsList.size() ; k++){
                    //Mysql数据库信息封装进H2数据库
                    sourceStructure sourceStructure = h2sl.fitMysqlStructure(k,"Mysql",dbName,tableName,mysqlColumnsList);
                    //Mysql数据库信息写入H2
                    h2sl.saveSourceStructure(sourceStructure,"Mysql",h2Stmt);
                }
            }
        }
        //关闭连接Mysql
        msql.MysqlClose(mysqlConn,mysqlStmt);
    }


    //Mongodb
    public static void connectMongodb(Statement h2Stmt) throws SQLException {
        //连接Mongodb
        String url = "mongodb://" + Mongodb_USER + ":" + Mongodb_PASS + "@" + Mongodb_DB_URL;
        MongoClient mc = MongoClients.create(url);

        //创建Mongodb数据源存储表
        h2sl.createSourceStructure("Mongodb",h2Stmt);
        h2sl.deleteSourceStructure("Mongodb",h2Stmt);

        //获取Mongodb的数据库结构
        List<MongodbDB> mongodbDBList = mdsl.findMongodbDB(mc);
        for(int i=3 ; i<mongodbDBList.size() ; i++)
        {
            String dbName = mongodbDBList.get(i).getDatabase();
            List<MongodbTable> mongodbTableList = mdsl.findMongodbTable(mc,dbName);

            //获取Mongodb的数据库的表
            for (int j=0 ; j<mongodbTableList.size() ; j++)
            {
                String tableName = mongodbTableList.get(j).getTableName();
                List<MongodbColumns> mongodbColumnsList = mdsl.findMongodbColumns(mc,dbName,tableName);

                //获取Mongodb的数据库的表的col
                for (int k=0 ; k<mongodbColumnsList.size() ; k++){
                    //Mongodb数据库信息封装进H2数据库
                    sourceStructure source = h2sl.fitMongodbStructure(k,"Mongodb",dbName,tableName,mongodbColumnsList);
                    //Mongodb数据库信息写入H2
                    h2sl.saveSourceStructure(source,"Mongodb",h2Stmt);
                }
            }
        }
        //关闭连接Mongodb
        mc.close();
    }

    public static void main(String[] args) throws SQLException {
        //连接H2
        Map<Connection, Statement> h2Mp = h2sl.H2Connect(H2_JDBC_DRIVER,H2_DB_URL,H2_USER,H2_PASS);
        Connection h2Conn = null;
        Statement h2Stmt = null;
        for(Connection c : h2Mp.keySet()){ h2Conn = c; }
        h2Stmt = h2Mp.get(h2Conn);

        //创建数据源配置信息储存表
        h2sl.createSourceSetting(h2Stmt);
        h2sl.deleteSourceSetting(h2Stmt);

        //保存数据源H2信息
        Source source = new Source();
        source.setSourceName("H2");
        source.setSourceUrl(H2_DB_URL);
        source.setUser(H2_USER);
        source.setPassword(H2_PASS);
        h2sl.saveSourceSetting(source,h2Stmt);

        //保存数据源Mysql信息
        source = new Source();
        source.setSourceName("Mysql");
        source.setSourceUrl(Mysql_DB_URL);
        source.setUser(Mysql_USER);
        source.setPassword(Mysql_PASS);
        h2sl.saveSourceSetting(source,h2Stmt);
        //连接Mysql
        connectMysql(h2Stmt);

        //保存数据源Mongodb信息
        source = new Source();
        source.setSourceName("Mongodb");
        source.setSourceUrl(Mongodb_DB_URL);
        source.setUser(Mongodb_USER);
        source.setPassword(Mongodb_PASS);
        h2sl.saveSourceSetting(source,h2Stmt);
        //连接Mongodb
        connectMongodb(h2Stmt);

        //控制台显示数据源配置信息
        System.out.println("数据源配置信息:");
        List<Source> sourceList = h2sl.searchSourceSetting(h2Stmt);
        h2sl.showSourceSetting(sourceList);

        //控制台显示Mysql Structure
        System.out.println();
        System.out.println("Mysql结构:");
        List<sourceStructure> sourceStructureList = h2sl.searchStructure(h2Stmt,"Mysql");
        h2sl.showStructure(sourceStructureList);

        //控制台显示连接Mongodb Structure
        System.out.println();
        System.out.println("连接Mongodb结构:");
        sourceStructureList = h2sl.searchStructure(h2Stmt,"Mongodb");
        h2sl.showStructure(sourceStructureList);

        //关闭连接H2
        h2sl.H2Close(h2Conn,h2Stmt);

    }
}
