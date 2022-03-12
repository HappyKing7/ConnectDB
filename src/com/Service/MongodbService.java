package com.Service;

import com.Bean.Mongodb.MongodbColumns;
import com.Bean.Mongodb.MongodbTable;
import com.Bean.Mongodb.MongodbDB;
import com.mongodb.client.MongoClient;
import java.util.List;

public interface MongodbService {
    List<MongodbDB> findMongodbDB(MongoClient mc);
    List<MongodbTable> findMongodbTable(MongoClient mc, String dbName);
    List<MongodbColumns> findMongodbColumns(MongoClient mc,String dbName,String tableName);
}
