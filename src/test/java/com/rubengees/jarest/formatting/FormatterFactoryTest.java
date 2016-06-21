package com.rubengees.jarest.formatting;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * TODO: Describe class
 *
 * @author Ruben Gees
 * @date 21.06.16
 */
public class FormatterFactoryTest {
    @Test
    public void testMakeFormatterHTML() throws Exception {
        assertTrue("Test content type text/html", FormatterFactory.makeFormatter("text/html") instanceof
                HTMLFormatter);
    }

    @Test
    public void testMakeFormatterJSON() throws Exception {
        assertTrue("Test content type application/json", FormatterFactory.makeFormatter("application/json") instanceof
                JSONFormatter);
    }

    @Test
    public void testMakeFormatterXML() throws Exception {
        assertTrue("Test content type text/xml", FormatterFactory.makeFormatter("text/xml") instanceof
                XMLFormatter);
    }

    @Test
    public void testMakeFormatterXMLOtherContentType() throws Exception {
        assertTrue("Test content type application/xml", FormatterFactory.makeFormatter("application/xml") instanceof
                XMLFormatter);
    }

    @Test
    public void testMakeFormatterPlainText() throws Exception {
        assertTrue("Test content type text/plain", FormatterFactory.makeFormatter("text/plain") instanceof
                DefaultFormatter);
    }

    @Test
    public void testMakeFormatterNull() throws Exception {
        assertTrue("Test null al content type", FormatterFactory.makeFormatter(null) instanceof DefaultFormatter);
    }

}