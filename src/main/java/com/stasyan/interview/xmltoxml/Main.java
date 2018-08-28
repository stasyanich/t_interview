package com.stasyan.interview.xmltoxml;

import com.stasyan.interview.xmltoxml.model.XmlEntries;
import com.stasyan.interview.xmltoxml.model.XmlEntry;
import com.stasyan.interview.xmltoxml.util.DBUtilStatic;
import com.stasyan.interview.xmltoxml.util.JaxbParser;
import com.stasyan.interview.xmltoxml.util.XmlToXmlException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;

public class Main {

    private static String firstFileName = "1";
    private static String secondFileName = "2";
    private static String postfix = ".xml";
    private static int bigN = 1_000;

    // данные для подключения к бд: адрес, логин, пароль?
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        incomingParameters(args);

        doIt(firstFileName, secondFileName, bigN);

        try {
            XmlEntries xmlEntries = new XmlEntries();
           ArrayList<XmlEntry> entries = DBUtilStatic.readRecordsFromDB();
            xmlEntries.setXmlEntries(entries);
            JaxbParser jaxbParser = new JaxbParser();

            jaxbParser.saveObject(new File(firstFileName+postfix), xmlEntries);
        } catch (XmlToXmlException e) {
            e.printStackTrace();
        }catch (JAXBException e){
            e.printStackTrace();
        }
        long timeSpend = System.currentTimeMillis() - startTime;
        System.err.println("программа выполнялась: " + timeSpend / 1000L + " секунд");
        System.err.println(firstFileName + ".xml " + secondFileName + ".xml" + " записалось: " + bigN + " раз");


    }

    private static void doIt(String firstFileName, String secondFileName, int bigN) {
        try {
            DBUtilStatic.addRecord(bigN);
        } catch (XmlToXmlException e) {
            e.printStackTrace();
        }
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
