/**
 * 
 */
package com.neml.parser;

/**
 * @author Neml10345
 *
 */
import java.io.Serializable;
import java.util.Vector;

public class AppData implements Serializable {
  private static final long serialVersionUID = -4645676252185614043L;
  
  private String appName;
  
  private int type;
  
  Vector<String[]> apiList;
  
  public String getAppName() {
    return this.appName;
  }
  
  public void setAppName(String appName) {
    this.appName = appName;
  }
  
  public int getType() {
    return this.type;
  }
  
  public void setType(int type) {
    this.type = type;
  }
  
  public Vector<String[]> getApiList() {
    return this.apiList;
  }
  
  public void setApiList(Vector<String[]> apiList) {
    this.apiList = apiList;
  }
}
