该项目可以使用接口接入多个数据源配置，获取数据源里边数据库的表的表schemas

1.该项目使用 H2 当作工程的底层数据库用来存储数据源配置

2.数据源接入的类型为：mongo，mysql

3.连接数据源配置，保存数据源连接配置信息

4.获取数据源中所有数据库的所有表的schemas