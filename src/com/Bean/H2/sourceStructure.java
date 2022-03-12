package com.Bean.H2;

public class sourceStructure {
    private String Source;
    private String dbName;
    private String tableName;
    private String colName;
    private String dataType;
    private String keyType;
    private String extra;
    private String defaultValue;
    private String nullType;

    public void setSource(String Source) {
        this.Source = Source;
    }
    public String getSource() {
        return Source;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    public String getDbName() {
        return dbName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getTableName() {
        return tableName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }
    public String getColName() { return colName; }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKey(String key) {
        this.keyType = key;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getNullType() {
        return nullType;
    }

    public void setNullType(String nullType) {
        this.nullType = nullType;
    }
}
