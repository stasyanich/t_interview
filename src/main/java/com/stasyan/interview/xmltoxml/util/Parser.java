package com.stasyan.interview.xmltoxml.util;

import javax.xml.bind.JAXBException;
import java.io.File;

public interface Parser {
    Object getObject(File file, Class clazz) throws JAXBException;
    void saveObject(File file, Object object) throws JAXBException;
}
