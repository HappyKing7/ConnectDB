package com.Service.Servicelmpl;

import com.Bean.Mongodb.MongodbColumns;
import com.Bean.Mongodb.MongodbTable;
import com.Bean.Mongodb.MongodbDB;
import com.Dao.MongodbDao;
import com.Service.MongodbService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MongodbServicelmpl implements MongodbService {
    private MongodbDao md = new MongodbDao();

    @Override
    public List<MongodbDB> findMongodbDB(MongoClient mc) {
        MongoCursor<String> dbsCursor = md.findMongodbDB(mc);
        List<MongodbDB> mongodbDBList = new ArrayList<>();
        while(dbsCursor.hasNext()) {
            MongodbDB mdd = new MongodbDB();
            mdd.setDatabase(dbsCursor.next());
            mongodbDBList.add(mdd);
        }
        return mongodbDBList;
    }

    @Override
    public List<MongodbTable> findMongodbTable(MongoClient mc, String dbName) {
        MongoCursor<String> dbsCursor = md.findMongodbTbale(mc,dbName);
        List<MongodbTable> mongodbTableList = new ArrayList<>();
        while(dbsCursor.hasNext()) {
            MongodbTable mdt = new MongodbTable();
            mdt.setTableName(dbsCursor.next());
            mongodbTableList.add(mdt);
        }
        return mongodbTableList;
    }

    @Override
    public List<MongodbColumns> findMongodbColumns(MongoClient mc,String dbName,String tableName) {
        Iterator iterator = md.findMongodbColunms(mc,dbName,tableName);
        List<MongodbColumns> mongodbColumnsList = new ArrayList<>();
        while(iterator.hasNext()){
            MongodbColumns mdc = new MongodbColumns();
            mdc.setColName((String)iterator.next());
            mongodbColumnsList.add(mdc);
        }
        return mongodbColumnsList;
    }

}
