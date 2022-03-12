package com.Service.Servicelmpl;

import com.Bean.H2.sourceStructure;
import com.Bean.Mongodb.MongodbColumns;
import com.Bean.Mysql.MysqlColumns;
import com.Bean.Source.Source;
import com.Dao.H2Dao;
import com.Dao.MongodbDao;
import com.Service.H2Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class H2Servicelmpl implements H2Service {
    private H2Dao h2d = new H2Dao();
    private MongodbDao mdd = new MongodbDao();

    @Override
    public Map<Connection, Statement> H2Connect(String JDBC_DRIVER, String DB_URL, String USER, String PASS) {
        return h2d.H2Connect(JDBC_DRIVER,DB_URL,USER,PASS);
    }

    @Override
    public void H2Close(Connection conn, Statement stmt) {
        h2d.H2Close(conn,stmt);
    }

    @Override
    public void deleteSourceSetting(Statement stmt) throws SQLException {
        String sql = "delete from SourceSetting";
        h2d.updateH2(stmt,sql);
    }

    @Override
    public void createSourceSetting(Statement stmt) throws SQLException {
        String sql = "create table if not exists SourceSetting (sourceName varchar,sourceUrl varchar," +
                "sourceUser varchar,sourcePassword varchar," +
                "primary key (sourceName,sourceUser));";
        h2d.updateH2(stmt,sql);
    }

    @Override
    public void saveSourceSetting(Source source, Statement stmt) throws SQLException {
        String sql = "insert into SourceSetting values ('" + source.getSourceName() + "','" + source.getSourceUrl() +
                "','" + source.getUser() + "','" + source.getPassword() + "')";
        h2d.updateH2(stmt,sql);
    }

    @Override
    public List<Source> searchSourceSetting(Statement stmt) throws SQLException {
        String sql = "select * from SourceSetting";
        ResultSet rs = h2d.queryH2(stmt,sql);
        List<Source> sourceList = new ArrayList();
        while(rs.next()){
            Source source = new Source();
            source.setSourceName(rs.getString("SOURCENAME"));
            source.setSourceUrl(rs.getString("SOURCEURL"));
            source.setUser(rs.getString("SOURCEUSER"));
            source.setPassword(rs.getString("SOURCEPASSWORD"));
            sourceList.add(source);
        }
        return sourceList;
    }

    @Override
    public void showSourceSetting(List<Source> sourceList) {
        for(int i=0 ; i < sourceList.size() ; i ++)
        {
            System.out.println(sourceList.get(i).getSourceName() + "\t" + sourceList.get(i).getSourceUrl()
                    + "\t" + sourceList.get(i).getSourceName() + "\t" + sourceList.get(i).getPassword()
            );
        }
    }

    @Override
    public void deleteSourceStructure(String dbSource,Statement stmt) throws SQLException {
        String sql = "delete from " + dbSource +"Structure";
        h2d.updateH2(stmt,sql);
    }

    @Override
    public void createSourceStructure(String dbSource,Statement stmt) throws SQLException {
        dbSource = dbSource + "Structure";
        String sql = "create table if not exists " + dbSource + " (dbSource varchar,dbName varchar," +
                "tableName varchar,colName varchar,dataType varchar,keyType varchar,extra varchar," +
                "nullType varchar,defaultValue varchar,"+
                "primary key (dbSource,dbName,tableName,colName));"
                ;
        h2d.updateH2(stmt,sql);
    }

    @Override
    public void saveSourceStructure(sourceStructure sourceStructure, String sourceName, Statement stmt) throws SQLException {
        String dbSource = sourceName + "Structure";
        String sql = "insert into " + dbSource + " values ('" + sourceStructure.getSource() + "','" + sourceStructure.getDbName() +
                "','" + sourceStructure.getTableName() + "','" + sourceStructure.getColName() + "','" + sourceStructure.getDataType() +
                "','" + sourceStructure.getKeyType() + "','" + sourceStructure.getExtra() + "','" + sourceStructure.getNullType() +
                "','" + sourceStructure.getDefaultValue() + "')";
        h2d.updateH2(stmt,sql);
    }

    @Override
    public List<sourceStructure> searchStructure(Statement stmt, String dbSource) throws SQLException {
        dbSource = dbSource + "Structure";
        String sql = "select * from " + dbSource;
        ResultSet rs = h2d.queryH2(stmt,sql);
        List<sourceStructure> sourceStructureList = new ArrayList();
        while(rs.next()){
            sourceStructure sourceStructure = new sourceStructure();
            sourceStructure.setSource(rs.getString("DBSOURCE"));
            sourceStructure.setDbName(rs.getString("DBNAME"));
            sourceStructure.setTableName(rs.getString("TABLENAME"));
            sourceStructure.setColName(rs.getString("ColNAME"));
            sourceStructure.setDataType(rs.getString("DataType"));
            sourceStructure.setKey(rs.getString("KeyType"));
            sourceStructure.setExtra(rs.getString("Extra"));
            sourceStructure.setNullType(rs.getString("NullType"));
            sourceStructure.setDefaultValue(rs.getString("DefaultValue"));
            sourceStructureList.add(sourceStructure);
        }
        return sourceStructureList;
    }

    @Override
    public void showStructure(List<sourceStructure> sourceStructureList) {
        String mysqlSource = "";
        String mysqlDB = "";
        String mysqlTable = "";
        for(int i = 0; i < sourceStructureList.size() ; i ++)
        {
            if(mysqlSource != sourceStructureList.get(i).getSource())
            {
                mysqlSource = sourceStructureList.get(i).getSource();
                System.out.println(mysqlSource);
            }

            if(mysqlDB != sourceStructureList.get(i).getDbName())
            {
                if (i != 0)
                {
                    System.out.println();
                }
                mysqlDB = sourceStructureList.get(i).getDbName();
                System.out.println("\t" + mysqlDB);
            }

            if(mysqlTable != sourceStructureList.get(i).getTableName())
            {
                mysqlTable = sourceStructureList.get(i).getTableName();
                System.out.println("\t\t" + mysqlTable);
            }

            String columnName = sourceStructureList.get(i).getColName();
            String dataType = sourceStructureList.get(i).getDataType();
            String key = sourceStructureList.get(i).getKeyType();
            String extra = sourceStructureList.get(i).getExtra();
            String nullType = sourceStructureList.get(i).getNullType();
            String defaultValue = sourceStructureList.get(i).getDefaultValue();
            System.out.println("\t\t\t" + columnName + "\t" + dataType + "\t" + key + "\t" + extra
                    + "\t" + nullType + "\t" + defaultValue);
        }
    }

    @Override
    public sourceStructure fitMysqlStructure(Integer index, String source, String DbName, String tableName, List<MysqlColumns> mysqlColumnsList) {
        sourceStructure sourceStructure = new sourceStructure();
        sourceStructure.setSource(source);
        sourceStructure.setDbName(DbName);
        sourceStructure.setTableName(tableName);
        sourceStructure.setColName(mysqlColumnsList.get(index).getField());
        sourceStructure.setDataType(mysqlColumnsList.get(index).getData_type());
        sourceStructure.setKey(mysqlColumnsList.get(index).getKey());
        sourceStructure.setExtra(mysqlColumnsList.get(index).getExtra());
        sourceStructure.setDefaultValue(mysqlColumnsList.get(index).getDefault_value());
        sourceStructure.setNullType(mysqlColumnsList.get(index).getNull_type());
        return sourceStructure;
    }

    @Override
    public sourceStructure fitMongodbStructure(Integer index, String source, String DbName, String tableName, List<MongodbColumns> mongoColumnsList) {
        sourceStructure sourceStructure = new sourceStructure();
        sourceStructure.setSource(source);
        sourceStructure.setDbName(DbName);
        sourceStructure.setTableName(tableName);
        sourceStructure.setColName(mongoColumnsList.get(index).getColName());
        sourceStructure.setDataType("");
        sourceStructure.setKey("");
        sourceStructure.setExtra("");
        sourceStructure.setDefaultValue("");
        sourceStructure.setNullType("");
        return sourceStructure;
    }
}
