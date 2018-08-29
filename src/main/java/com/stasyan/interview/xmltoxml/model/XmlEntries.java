package com.stasyan.interview.xmltoxml.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "entries")
public class XmlEntries {
    private  ArrayList<XmlEntry> xmlEntries;

    public ArrayList<XmlEntry> getXmlEntries() {
        return xmlEntries;
    }

    @XmlElement(name = "entry")
    public void setXmlEntries(ArrayList<XmlEntry> xmlEntries) {
        this.xmlEntries = xmlEntries;
    }
}
