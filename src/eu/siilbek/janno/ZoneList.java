package eu.siilbek.janno;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class ZoneList extends ArrayList implements ContentHandler {

  private static final long serialVersionUID = -4297410984875339951L;

  public static void main(String args[]) throws FileNotFoundException {
    FileInputStream fis = new FileInputStream("res/xml/zones.xml");
    ZoneList l = new ZoneList(fis);
  }

  public ZoneList(InputStream is) {
    try {
      SAXParserFactory spf = SAXParserFactory.newInstance();
      SAXParser sp = spf.newSAXParser();
      XMLReader xr = sp.getXMLReader();
      xr.setContentHandler(this);
      xr.parse(new InputSource(is));
      is.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void characters(char[] ch, int start, int length) throws SAXException {
  }

  public void endDocument() throws SAXException {
  }

  public void endElement(String uri, String localName, String qName)
      throws SAXException {
  }

  public void endPrefixMapping(String prefix) throws SAXException {
  }

  public void ignorableWhitespace(char[] ch, int start, int length)
      throws SAXException {
  }

  public void processingInstruction(String target, String data)
      throws SAXException {
  }

  public void setDocumentLocator(Locator locator) {
  }

  public void skippedEntity(String name) throws SAXException {
  }

  public void startDocument() throws SAXException {
  }

  public void startElement(String uri, String localName, String qName,
      Attributes atts) throws SAXException {
    if ("zone".equals(localName)) {
      Zone z = new Zone();
      z.city = atts.getValue("city");
      z.key = atts.getValue("key");
      z.provider = atts.getValue("provider");
      z.address = atts.getValue("address");
      z.desc = atts.getValue("desc");
      add(z);
    }
  }

  public void startPrefixMapping(String prefix, String uri) throws SAXException {
  }
}
