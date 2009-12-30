package eu.siilbek.janno;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.content.res.XmlResourceParser;

import junit.framework.TestCase;

public class ZoneTest extends TestCase {
  public void testParseZones() throws FileNotFoundException {
    FileInputStream fis = new FileInputStream("res/xml/zones.xml");
    ZoneList zl = new ZoneList(fis);
    
    

  }
}
