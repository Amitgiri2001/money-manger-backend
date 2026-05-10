package com.amitgiri.moneymanager.config;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbDebugConfig {

    @Bean
    CommandLineRunner testDatabase(
            DataSource dataSource
    ) {

        return args -> {

            System.out.println(
                "================================="
            );

            System.out.println(
                "CONNECTED DATABASE URL:"
            );

            System.out.println(
                dataSource
                    .getConnection()
                    .getMetaData()
                    .getURL()
            );

            System.out.println(
                "================================="
            );
        };
    }
}