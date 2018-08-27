package com.stasyan.interview.xmltoxml.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtilStatic {
    private static final String JDBC_DRIVER = "org.sqlite.JDBC";
    private static final String CONN_STR = "jdbc:sqlite:";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        }catch (ClassNotFoundException e){
            System.err.println("Enable to load jdbc driver");
        }

    }

    public static Connection getConnection(){

        try(Connection connection = DriverManager.getConnection(CONN_STR+"xmltoxml.db")) {
            return connection;
        }catch (SQLException e){
            System.err.println("Подключение к BD невозможно");
            return null;
        }

    }

}
