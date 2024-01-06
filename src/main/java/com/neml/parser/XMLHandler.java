/**
 * 
 */
package com.neml.parser;

/**
 * @author Neml10345
 *
 */

import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler implements XMLTags {
  private Vector<AppData> xmlDataList = new Vector<AppData>();
  
  private AppData xmlData;
  
  private Vector<String[]> apisList;
  
  private String buffer;
  
  private String apiName;
  
  private String apiExample;
  
  public void startElement(String namespaceURI, String localName, String qName, Attributes attr) throws SAXException {
    super.startElement(namespaceURI, localName, qName, attr);
    if (qName.equalsIgnoreCase("appname")) {
      this.xmlData = new AppData();
      this.apisList = (Vector)new Vector<String>();
    } 
  }
  
  public void endElement(String uri, String localName, String qName) throws SAXException {
    super.endElement(uri, localName, qName);
    if (qName.equalsIgnoreCase("apps")) {
      this.xmlData.setApiList(this.apisList);
      this.xmlDataList.add(this.xmlData);
    } else if (qName.equalsIgnoreCase("appname")) {
      this.xmlData.setAppName(this.buffer);
    } else if (qName.equalsIgnoreCase("type")) {
      this.xmlData.setType(Integer.parseInt(this.buffer));
    } else if (qName.equalsIgnoreCase("api-name")) {
      this.apiName = this.buffer;
    } else if (qName.equalsIgnoreCase("api-sample")) {
      this.apiExample = this.buffer;
      this.apisList.add(new String[] { this.apiName, this.apiExample });
    } 
  }
  
  public void characters(char[] ch, int start, int lenght) throws SAXException {
    super.characters(ch, start, lenght);
    this.buffer = new String(ch, start, lenght);
  }
  
  public Vector<AppData> getData() {
    return this.xmlDataList;
  }
}
