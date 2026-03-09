package com.micro.client.config.database;

import org.springframework.stereotype.Component;

@Component("postgresql")
public class PostgresSQLStrategy implements DatabaseStrategy {

    @Override
    public String getDriverClassName() {
        return "org.postgresql.Driver";
    }

    @Override
    public String getHibernateDialect() {
        return "org.hibernate.dialect.PostgreSQLDialect";
    }

    @Override
    public String buildUrl(String host,String port,String database) {
        return String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
    }
}
