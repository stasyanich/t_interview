package com.stasyan.interview.xmltoxml;

import com.stasyan.interview.xmltoxml.util.DBUtilStatic;

public class Main {


    private static String firstFileName = "1";
    private static String secondFileName = "2";
    private static int bigN = 1;

    // данные для подключения к бд: адрес, логин, пароль?
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        if (args.length==3){
            firstFileName = args[0];
            secondFileName = args[1];
            bigN = Integer.getInteger(args[2]);
        }else if(args.length==2) {
            firstFileName = args[0];
            secondFileName = args[1];
        }else if (args.length==1){
            bigN = Integer.getInteger(args[0]);
        }else if (args.length<1){
            //NOP
        }else {
            System.out.println("Ошибка в параметрах:" + args.toString());
        }

//        System.out.println(firstFileName + ".xml " + secondFileName + ".xml" + " записалось: " + bigN + " раз");

        new DBUtilStatic();

        doIt(firstFileName,secondFileName,bigN);

        long timeSpend = System.currentTimeMillis()-startTime;
        System.out.println("программа выполнялась: "+timeSpend/1000L );
    }

    private static void doIt(String firstFileName, String secondFileName, int bigN) {

    }

}
