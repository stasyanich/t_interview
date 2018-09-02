package com.stasyan.interview.xmltoxml.util;

import com.stasyan.interview.xmltoxml.model.XmlEntries;
import com.stasyan.interview.xmltoxml.model.XmlEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class JaxbParser  {
    private static final Logger log = LogManager.getLogger();

    public Object getObject(File file, Class clazz) throws JAXBException {
        log.info("Try to get object from XML file"  );
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object object = unmarshaller.unmarshal(file);

        return object;
    }


    public void saveObject(File file, Object object) throws JAXBException {
        log.info("Try to save object from XML file"  );
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(object, file);
    }

    public XmlEntries getTansformedXmlEntries(File file) throws Exception{
        log.info("Try to transform object from XML file by XSLT"  );
        XmlEntries xmlEntries = new XmlEntries();
        ArrayList<XmlEntry> xmlEntryArrayList = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        if (file.isFile() && file.canRead()) {
            Document document = builder.parse(file);
            Element root = document.getDocumentElement();
            NodeList nodeList = root.getElementsByTagName("entry");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node instanceof Element) {
                    String data = ((Element) node).getAttribute("field");
                    int field = Integer.valueOf(data);
                    XmlEntry xmlEntry = new XmlEntry(field);
                    xmlEntryArrayList.add(xmlEntry);
                }
            }
            xmlEntries.setXmlEntries(xmlEntryArrayList);
        }
        return xmlEntries;
    }
}
