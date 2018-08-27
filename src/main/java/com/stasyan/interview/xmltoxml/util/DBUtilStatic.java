package com.stasyan.interview.xmltoxml.util;

import com.sun.istack.internal.NotNull;

import java.sql.*;

public class DBUtilStatic {
    private static final String JDBC_DRIVER = "org.sqlite.JDBC";
    private static final String CONN_STR = "jdbc:sqlite:";
    private static Connection connection;

    static {
        try {
            Class.forName(JDBC_DRIVER);
            connection = getConnection();
            connection.setAutoCommit(false);

            clearDB();
        }catch (ClassNotFoundException e){
            System.err.println("Enable to load jdbc driver");
        }catch (XmlToXmlException e){
            System.err.println(e.getMessage());
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public DBUtilStatic() {
        try {
            addRecord(1_000_000);
        } catch (XmlToXmlException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws XmlToXmlException{
        try {
            return DriverManager.getConnection(CONN_STR+"xmltoxml.db");
        }catch (SQLException e){
            throw new XmlToXmlException(e);
        }
    }


    private static void querySqlDB(String query) throws XmlToXmlException {

        try(Statement statement = connection.createStatement()) {
            statement.execute(query);

        }catch (SQLException e){
            throw new XmlToXmlException("Невозможно получить statement" + e);

        }catch (NullPointerException e){
            throw new XmlToXmlException("Невозможно получить statement: NPE" + e);
        }
    }

    private static void addRecord(int amount) throws XmlToXmlException{
        String sqlQuery = "INSERT INTO TEST (FIELD) VALUES (?)";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)){

            for (int i=1;i<=amount;i++){
                statement.setInt(1, i);
                statement.addBatch();
            }

            statement.executeBatch();
            connection.commit();
        }catch (SQLException e){
            throw new XmlToXmlException(e);
        }
    }

    private static void clearDB() throws XmlToXmlException{
        querySqlDB("DROP TABLE IF EXISTS TEST");
        querySqlDB("CREATE TABLE TEST (FIELD INTEGER)");
    }
}
