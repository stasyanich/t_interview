package com.stasyan.interview.xmltoxml;

import com.stasyan.interview.xmltoxml.model.XmlEntries;
import com.stasyan.interview.xmltoxml.model.XmlEntry;
import com.stasyan.interview.xmltoxml.util.DBUtilStatic;
import com.stasyan.interview.xmltoxml.util.JaxbParser;
import com.stasyan.interview.xmltoxml.util.XmlToXmlException;
import com.stasyan.interview.xmltoxml.util.XsltParser;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.ArrayList;

public class Main {

    private static String firstFileName = "1";
    private static String secondFileName = "2";
    private static String postfix = ".xml";
    private static String xlsSource = "template.xls";
    private static int bigN = 1_00;

    // данные для подключения к бд: адрес, логин, пароль?
    public static void main(String[] args) throws TransformerException {
        long startTime = System.currentTimeMillis();

        incomingParameters(args);

        doIt(firstFileName, secondFileName, bigN);

        XsltParser xsltParser = new XsltParser();
        xsltParser.transformXmlToXlst(firstFileName+postfix,secondFileName+postfix,xlsSource);

        long timeSpend = System.currentTimeMillis() - startTime;
        System.err.println("программа выполнялась: " + timeSpend / 1000L + " секунд");
        System.err.println(firstFileName + ".xml " + secondFileName + ".xml" + " записалось: " + bigN + " раз");


    }

    private static void doIt(String firstFileName, String secondFileName, int bigN) {
        try {
            DBUtilStatic.addRecord(bigN);
            ArrayList<XmlEntry> entries = DBUtilStatic.readRecordsFromDB();
            saveObjectsToXml(entries,firstFileName+postfix);
            readObjectsFromXml(firstFileName+postfix);
        } catch (XmlToXmlException e) {
            e.printStackTrace();
        }catch (JAXBException j){
            j.printStackTrace();
        }
    }

    private static void saveObjectsToXml(ArrayList<XmlEntry> arrayList, String fileName) throws JAXBException{
        XmlEntries xmlEntries = new XmlEntries();

        xmlEntries.setXmlEntries(arrayList);
        JaxbParser jaxbParser = new JaxbParser();

        jaxbParser.saveObject(new File(fileName), xmlEntries);
    }

    private static XmlEntries readObjectsFromXml(String filename) throws JAXBException{
        JaxbParser jaxbParser = new JaxbParser();
        XmlEntries xmlEntries =(XmlEntries) jaxbParser.getObject(new File(filename), XmlEntries.class);
        return xmlEntries;
    }

    private static void incomingParameters(String[] args){
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
        }catch (NumberFormatException n){
            System.err.println("Ошибка ввода числа. Количество записей в бд указывается первым и единственнм значением или третим");
        }

        }

}
