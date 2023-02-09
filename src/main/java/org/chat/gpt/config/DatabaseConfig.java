package org.chat.gpt.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Getter
@Component
@RefreshScope
public class DatabaseConfig {

    @Value("${database.database.db.name}")
    String dbName;

    @Value("${database.database.db.schema.name}")
    String dbSchemaName;

    @Value("${database.database.db.port}")
    String dbPort;

    @Value("${database.database.db.host}")
    String dbHost;

    @Value("${database.database.db.user}")
    String dbUser;

    @Value("${database.database.db.password}")
    String dbPassword;
}
