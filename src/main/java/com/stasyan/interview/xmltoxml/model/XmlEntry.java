package com.stasyan.interview.xmltoxml.model;

import javax.xml.bind.annotation.XmlElement;

public class XmlEntry {

    private int field;

    public XmlEntry() {
    }

    public XmlEntry(int field) {
        this.field = field;
    }

    public int getField() {
        return field;
    }

    @XmlElement
    public void setField(int field) {
        this.field = field;
    }
}
