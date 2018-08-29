package com.stasyan.interview.xmltoxml.util;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XsltParser {

    public Object getObjectFromXlst(File file, Class clazz)  {
        throw new NullPointerException();
    }


    public void transformXmlToXlst(String xmlFile, String xsltFile, String xlsSource) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(xlsSource);
        Transformer transformer = factory.newTransformer(xslt);
        Source xml = new StreamSource(xmlFile);
        transformer.transform(xml,new StreamResult(xsltFile));
    }
}
