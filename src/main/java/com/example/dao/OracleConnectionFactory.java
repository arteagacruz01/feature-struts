package com.example.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class OracleConnectionFactory {

    private static final Properties props = new Properties();
    static {
        try (InputStream is = OracleConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is == null) throw new RuntimeException("db.properties no encontrado en classpath");
            props.load(is);
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (Exception e) {
            throw new RuntimeException("Error cargando configuración de base de datos", e);
        }
    }

    public static Connection getConnection() {
        try {
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener conexión a Oracle", e);
        }
    }
}
