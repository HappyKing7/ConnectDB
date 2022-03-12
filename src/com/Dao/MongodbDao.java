package com.Dao;

import com.mongodb.client.*;
import org.bson.Document;
import java.util.Iterator;
import java.util.Set;

public class MongodbDao {
    public MongoCursor<String> findMongodbDB(MongoClient mc){
        MongoCursor<String> dbsCursor  = mc.listDatabaseNames().iterator();
        return dbsCursor;
    }

    public MongoCursor<String> findMongodbTbale(MongoClient mc,String dbName){
        MongoDatabase md = mc.getDatabase(dbName);
        MongoCursor<String> dbsCursor =md.listCollectionNames().iterator();;
        return dbsCursor;
    }

    public Iterator findMongodbColunms(MongoClient mc,String dbName,String tableName){
        MongoDatabase md = mc.getDatabase(dbName);
        MongoCollection mcol = md.getCollection(tableName);
        FindIterable<Document> findIterable= mcol.find();
        Document doc = findIterable.first();
        Set<String> keys = doc.keySet();
        Iterator iterator = keys.iterator();
        return iterator;
    }
}
