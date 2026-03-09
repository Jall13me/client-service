package com.micro.client.config.database;

import org.springframework.stereotype.Component;

@Component("mysql")
public class MySQLStrategy implements DatabaseStrategy {

    @Override
    public String getDriverClassName() {
        return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    public String getHibernateDialect() {
        return "org.hibernate.dialect.MySQLDialect";
    }

    @Override
    public String buildUrl(String host,String port,String database){
        return String.format("jdbc:mysql://%s:%s/%s", host, port, database);
    }
}
