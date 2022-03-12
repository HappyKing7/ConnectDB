package com.Bean.Mongodb;

public class MongodbColumns {
    private String colName;
    private String data_type;
    private String null_type;
    private String key;
    private String default_value;
    private String extra;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getNull_type() {
        return null_type;
    }

    public void setNull_type(String null_type) {
        this.null_type = null_type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDefault_value() {
        return default_value;
    }

    public void setDefault_value(String default_value) {
        this.default_value = default_value;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
