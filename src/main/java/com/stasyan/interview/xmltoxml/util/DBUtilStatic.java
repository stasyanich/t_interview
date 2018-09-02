package com.stasyan.interview.xmltoxml.util;

import com.stasyan.interview.xmltoxml.model.XmlEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class DBUtilStatic {
    private static final String JDBC_DRIVER = "org.sqlite.JDBC";
    private static final String CONN_STR = "jdbc:sqlite:";
    private static final String DB_NAME = "xmltoxml.db";
    private static Connection connection;
    private static final Logger log = LogManager.getLogger();

    static {
        try {
            Class.forName(JDBC_DRIVER);
            connection = getConnection();
            clearDB();
        } catch (ClassNotFoundException e) {
            log.error("Cannot to load jdbc driver");
        } catch (XmlToXmlException e) {
            log.error(e.getMessage());
        }
    }

    private static Connection getConnection() throws XmlToXmlException {
        log.info("Try to get connection to data base");
        try (Connection connection = DriverManager.getConnection(CONN_STR + DB_NAME)) {
            return connection;
        } catch (SQLException e) {
            throw new XmlToXmlException(e);
        }
    }

    private static void querySqlDB(String query) throws XmlToXmlException {
        log.info("Try to execute query: " + query);
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);

        } catch (SQLException e) {
            throw new XmlToXmlException("Impossible to get a statement: " + e);

        } catch (NullPointerException e) {
            throw new XmlToXmlException("Impossible to get a statement: NPE " + e);
        }
    }

    public static void addRecord(int amount) throws XmlToXmlException {
        log.info("Try to insert " + amount + " records to TEST");
        String sqlQuery = "INSERT INTO TEST (FIELD) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            connection.setAutoCommit(false);

            for (int i = 0; i < amount; i++) {
                statement.setInt(1, i);
                statement.addBatch();
            }

            statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            throw new XmlToXmlException(e);
        }
    }

    public static ArrayList<XmlEntry> readRecordsFromDB() throws XmlToXmlException {
        log.info("Try to read records from TEST");
        ArrayList<XmlEntry> result;
        String sqlQuery = "SELECT * FROM TEST";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sqlQuery);
            result = parseResultSetXmlBeans(rs);
        } catch (SQLException e) {
            throw new XmlToXmlException(e);
        }

        return result;
    }

    private static ArrayList<XmlEntry> parseResultSetXmlBeans(ResultSet rs) throws SQLException {
        log.info("Try to parse objects from ResultSet ");
        ArrayList<XmlEntry> result = new ArrayList<>();
        while (rs.next()) {
            XmlEntry xmlEntry = new XmlEntry();
            xmlEntry.setField(rs.getInt("FIELD"));
            result.add(xmlEntry);
        }
        return result;
    }

    private static void clearDB() throws XmlToXmlException {
        querySqlDB("DROP TABLE IF EXISTS TEST");
        querySqlDB("CREATE TABLE TEST (FIELD INTEGER)");
    }
}
