package com.stasyan.interview.xmltoxml;

import com.stasyan.interview.xmltoxml.util.DBUtilStatic;
import com.stasyan.interview.xmltoxml.util.XmlToXmlException;

public class Main {

    private static String firstFileName = "1";
    private static String secondFileName = "2";
    private static int bigN = 1;

    // данные для подключения к бд: адрес, логин, пароль?
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
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
                System.out.println("Ошибка в параметрах:" + args.toString());
            }

            doIt(firstFileName, secondFileName, bigN);

            long timeSpend = System.currentTimeMillis() - startTime;
            System.out.println("программа выполнялась: " + timeSpend / 1000L + " секунд");
        }catch (NumberFormatException n){
            System.err.println("Ошибка ввода числа. Количество записей в бд указывается первым и единственнм значением или третим");
        }
    }

    private static void doIt(String firstFileName, String secondFileName, int bigN) {
        System.out.println(firstFileName + ".xml " + secondFileName + ".xml" + " записалось: " + bigN + " раз");
        try {
            DBUtilStatic.addRecord(bigN);
        } catch (XmlToXmlException e) {
            e.printStackTrace();
        }
    }

}
