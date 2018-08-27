package com.stasyan.interview.xmltoxml.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

    public DBUtilStatic() {
        try {
            createSqlDB(getConnection());
        }catch (XmlToXmlException e){
            System.err.println(e.getMessage());
        }
    }

    public static Connection getConnection() throws XmlToXmlException{
        try {
            return DriverManager.getConnection(CONN_STR+"xmltoxml.db");
        }catch (SQLException e){
            throw new XmlToXmlException(e);
        }
    }


    public static void createSqlDB(Connection connection) throws XmlToXmlException {
        String queryToDropDB = "DROP TABLE IF EXISTS TEST;";
        String queryToCreateDB = "CREATE TABLE TEST(FIELD INTEGER);";

        try(Statement statement = connection.createStatement()) {
            statement.execute(queryToDropDB + queryToCreateDB);

        }catch (SQLException e){
            throw new XmlToXmlException("Невозможно получить statement" + e);

        }catch (NullPointerException e){
            throw new XmlToXmlException("Невозможно получить statement: NPE" + e);
        }
    }

}
