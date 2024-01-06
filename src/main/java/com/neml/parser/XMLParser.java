/**
 * 
 */
package com.neml.parser;

/**
 * @author Neml10345
 *
 */
import java.io.File;
import java.util.Vector;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLParser {
  private SAXParserFactory parser;
  
  private XMLHandler xmlHandler = new XMLHandler();
  
  private Vector<AppData> xmlDatas;
  
  public XMLParser() {
    try {
      this.parser = SAXParserFactory.newInstance();
      SAXParser saxParser = this.parser.newSAXParser();
      File configFile = new File("./config.xml");
      if (configFile.exists()) {
        saxParser.parse(configFile, this.xmlHandler);
        this.xmlDatas = this.xmlHandler.getData();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public Vector<AppData> getApps() {
    return this.xmlDatas;
  }
}
