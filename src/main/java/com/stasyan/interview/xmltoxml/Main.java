package com.stasyan.interview.xmltoxml;

import com.stasyan.interview.xmltoxml.model.XmlEntries;
import com.stasyan.interview.xmltoxml.model.XmlEntry;
import com.stasyan.interview.xmltoxml.util.DBUtilStatic;
import com.stasyan.interview.xmltoxml.util.JaxbParser;
import com.stasyan.interview.xmltoxml.util.XmlToXmlException;
import com.stasyan.interview.xmltoxml.util.XsltTransformer;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.ArrayList;

public class Main {

    private static String firstFileName = "1";
    private static String secondFileName = "2";
    private static String postfix = ".xml";
    private static final String xlsSource = "template.xls";
    private static int bigN = 1_000_000;

    // данные для подключения к бд: адрес, логин, пароль?
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();

        incomingParameters(args);

        doIt(firstFileName, secondFileName, bigN);
        transformXlsToXSLT(firstFileName + postfix, secondFileName + postfix, xlsSource);

        ArrayList<XmlEntry> xmlEntries = readXmlEntriesFromXml(secondFileName + postfix);

        int arithmeticSum = getSumXmlEntryFromArrayList(xmlEntries);

        long timeSpend = System.currentTimeMillis() - startTime;
        System.err.println("программа выполнялась: " + timeSpend / 1000L + " секунд");
        System.err.println(firstFileName + ".xml " + secondFileName + ".xml" + " записалось: " + bigN + " раз");
        System.err.println("арифметическая сумма: " + arithmeticSum);

    }

    private static void doIt(String firstFileName, String secondFileName, int bigN) {
        try {
            DBUtilStatic.addRecord(bigN);
            ArrayList<XmlEntry> entries = DBUtilStatic.readRecordsFromDB();
            saveXmlEntryToXml(entries, firstFileName + postfix);

        } catch (XmlToXmlException e) {
            e.printStackTrace();
        } catch (JAXBException j) {
            j.printStackTrace();
        }
    }

    private static void saveXmlEntryToXml(ArrayList<XmlEntry> arrayList, String fileName) throws JAXBException {
        XmlEntries xmlEntries = new XmlEntries();

        xmlEntries.setXmlEntries(arrayList);
        JaxbParser jaxbParser = new JaxbParser();

        jaxbParser.saveObject(new File(fileName), xmlEntries);
    }

    private static void transformXlsToXSLT(String xmlFile, String xsltFile, String xlsSource) throws TransformerException {
        XsltTransformer xsltTransformer = new XsltTransformer();
        xsltTransformer.transformXmlToXlst(xmlFile, xsltFile, xlsSource);

    }

    private static ArrayList<XmlEntry> readXmlEntriesFromXml(String filename) throws Exception {
        ArrayList<XmlEntry> result;
        JaxbParser jaxbParser = new JaxbParser();
        XmlEntries xmlEntries = jaxbParser.getTansformedObject(new File(filename), XmlEntries.class);

        result = xmlEntries.getXmlEntries();
        return result;
    }
    private static void incomingParameters(String[] args) {
        try {

            if (args.length == 3) {
                firstFileName = args[0];
                secondFileName = args[1];
                bigN = Integer.valueOf(args[2]);
            } else if (args.length == 2) {
                firstFileName = args[0];
                secondFileName = args[1];
            } else if (args.length == 1) {
                bigN = Integer.valueOf(args[0]);
            } else if (args.length < 1) {
                //NOP
            } else {
                System.err.println("Ошибка в параметрах:" + args.toString());
            }
        } catch (NumberFormatException n) {
            System.err.println("Ошибка ввода числа. Количество записей в бд указывается первым и единственнм значением или третим");
        }

    }

    private static int getSumXmlEntryFromArrayList(ArrayList<XmlEntry> xmlEntryArrayList) {
        int sum = 0;
        for (XmlEntry xmlEntry: xmlEntryArrayList) {
            sum += xmlEntry.getField();
        }
        return sum;
    }
}
