package com.stasyan.interview.xmltoxml.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "entries")
public class XmlEntries {
    ArrayList<XmlEntry> xmlEntries;

    @XmlElement(name = "entry")
    public ArrayList<XmlEntry> getXmlEntries() {
        return xmlEntries;
    }

    public void setXmlEntries(ArrayList<XmlEntry> xmlEntries) {
        this.xmlEntries = xmlEntries;
    }
}
