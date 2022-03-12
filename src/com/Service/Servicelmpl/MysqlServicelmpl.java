package com.Service.Servicelmpl;

import com.Bean.Mysql.MysqlColumns;
import com.Bean.Mysql.MysqlDB;
import com.Bean.H2.sourceStructure;
import com.Bean.Mysql.MysqlTable;
import com.Dao.MysqlDao;
import com.Service.MysqlService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MysqlServicelmpl implements MysqlService {
    private MysqlDao md = new MysqlDao();

    @Override
    public Map<Connection, Statement> MysqlConnect(String JDBC_DRIVER, String DB_URL, String USER, String PASS) {
        return md.MysqlConnect(JDBC_DRIVER,DB_URL,USER,PASS);
    }

    @Override
    public void MysqlClose(Connection conn, Statement stmt) {
        md.MysqlClose(conn,stmt);
    }

    @Override
    public List<MysqlDB> findMysqlDB(Statement stmt) throws SQLException {
        String sql = "show schemas";
        ResultSet rs = md.queryMysql(stmt,sql);
        List<MysqlDB> mysqlDBList = new ArrayList();
        while(rs.next()){
            MysqlDB msdb = new MysqlDB();
            msdb.setDatabase(rs.getString("Database"));
            mysqlDBList.add(msdb);
        }
        return mysqlDBList;
    }

    @Override
    public List<MysqlTable> findMysqlTable(Statement stmt,String dbName) throws SQLException{
        String sql = "SELECT * FROM information_schema.tables WHERE table_schema = '" + dbName + "'";
        ResultSet rs = md.queryMysql(stmt,sql);
        List<MysqlTable> mysqlTableList = new ArrayList();
        while(rs.next()){
            MysqlTable mst = new MysqlTable();
            mst.setTABLE_NAME(rs.getString("TABLE_NAME"));
            mst.setTABLE_SCHEMA(rs.getString("TABLE_SCHEMA"));
            mysqlTableList.add(mst);
        }
        return mysqlTableList;
    }

    @Override
    public List<MysqlColumns> findMysqlColumns(Statement stmt,String dbName,String tableName) throws SQLException {
        String sql = "show columns from " + dbName + "." + tableName ;
        ResultSet rs = md.queryMysql(stmt,sql);
        List<MysqlColumns> mysqlColumnsList = new ArrayList();
        while(rs.next()){
            MysqlColumns msc = new MysqlColumns();
            msc.setField(rs.getString("Field"));
            msc.setData_type(rs.getString("type"));
            msc.setKey(rs.getString("Key"));
            msc.setExtra(rs.getString("Extra"));
            msc.setDefault_value(rs.getString("Default"));
            msc.setNull_type(rs.getString("Null"));
            mysqlColumnsList.add(msc);
        }
        return mysqlColumnsList;
    }

}
