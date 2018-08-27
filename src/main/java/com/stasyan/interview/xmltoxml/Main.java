package com.stasyan.interview.xmltoxml;

public class Main {


    private static String firstFileName = "1";
    private static String secondFileName = "2";

    public static void main(String[] args) {

        System.out.println("fist key firstFileName and secondFileName");
        if(args.length>2) {
            firstFileName = args[0];
            secondFileName = args[1];
        }else {
            System.out.println("принимаемых пораметров должно быть 2 и более");
        }

        System.out.println(firstFileName + ".xml " + secondFileName + ".xml");
    }

}
