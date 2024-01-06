/**
 * 
 */
package com.neml.parser;

/**
 * @author Neml10345
 *
 */

public interface XMLTags {
  public static interface TAG {
    public static final String TAG_APPS = "apps";
    
    public static final String TAG_APP_NAME = "appname";
    
    public static final String TAG_API_TYPE = "type";
    
    public static final String TAG_APP_DETAILS = "app-details";
    
    public static final String TAG_API_NAME = "api-name";
    
    public static final String TAG_API_EXMAPLE = "api-sample";
  }
  
  public static interface VALUES {
    public static final int TAG_API_TYPE_SOCKET = 10;
    
    public static final int TAG_API_TYPE_WEB_SERVICE = 20;
  }
}
