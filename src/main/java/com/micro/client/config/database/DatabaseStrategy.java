package com.micro.client.config.database;

public interface DatabaseStrategy {

    String getDriverClassName();
    String getHibernateDialect();
    String buildUrl(String host, String port, String database);
}
