package com.stasyan.interview.xmltoxml.util;

import com.stasyan.interview.xmltoxml.model.XmlEntry;

import java.sql.*;
import java.util.ArrayList;

public class DBUtilStatic {
    private static final String JDBC_DRIVER = "org.sqlite.JDBC";
    private static final String CONN_STR = "jdbc:sqlite:";
    private static Connection connection;

    static {
        try {
            Class.forName(JDBC_DRIVER);
            connection = getConnection();
            clearDB();
        }catch (ClassNotFoundException e){
            System.err.println("Cannot to load jdbc driver");
        }catch (XmlToXmlException e) {
            System.err.println(e.getMessage());
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

    public static void addRecord(int amount) throws XmlToXmlException{

        String sqlQuery = "INSERT INTO TEST (FIELD) VALUES (?)";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)){
            connection.setAutoCommit(false);

            for (int i=0; i<amount; i++){
                statement.setInt(1, i);
                statement.addBatch();
            }

            statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);

        }catch (SQLException e){
            throw new XmlToXmlException(e);
        }
    }

    public static ArrayList<XmlEntry> readRecordsFromDB() throws XmlToXmlException{
        ArrayList<XmlEntry> result ;
        String sqlQuery = "SELECT * FROM TEST";
        try(Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sqlQuery);
            result = parseResultSetXmlBeans(rs);
        }catch (SQLException e){
            throw new XmlToXmlException(e);
        }

        return result;
    }

    private static ArrayList<XmlEntry> parseResultSetXmlBeans(ResultSet rs) throws SQLException {
        ArrayList<XmlEntry> result = new ArrayList<>();
        while (rs.next()){
            XmlEntry xmlEntry = new XmlEntry();
            xmlEntry.setField(rs.getInt("FIELD"));
            result.add(xmlEntry);
        }
        return result;
    }

    private static void clearDB() throws XmlToXmlException{
        querySqlDB("DROP TABLE IF EXISTS TEST");
        querySqlDB("CREATE TABLE TEST (FIELD INTEGER)");
    }
}
