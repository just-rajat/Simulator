/**
 * 
 */
package com.neml.utils;

/**
 * @author Neml10345
 *
 */

public class Constants {
  public static String getBiidingAPISnippet(String apiName) {
    String apiExampleText = "";
    if (apiName.equalsIgnoreCase(""))
      return apiExampleText; 
    if (apiName.equalsIgnoreCase("Login")) {
      apiExampleText = "TC=101|UID=as|Pwd=mumbai123";
    } else if (apiName.equalsIgnoreCase("GetCommodity")) {
      apiExampleText = "TC=102|MKT=ERD";
    } else if (apiName.equalsIgnoreCase("Get Lot/Incremental")) {
      apiExampleText = "TC=103|MKT=ERD|CMDT=DRY CHILLY|CAID=ALL|CTR=-1";
    } else if (apiName.equalsIgnoreCase("Get Modified")) {
      apiExampleText = "TC=105|MKT=ERD|CMDT=DRY CHILLY|CAID=ALL|CTR=-1";
    } else if (apiName.equalsIgnoreCase("Get Submitted Bids")) {
      apiExampleText = "TC=106|MKT=ERD|CMDT=DRY CHILLY|TMID=435345|UID=as|CAID=ALL";
    } else if (apiName.equalsIgnoreCase("Submit Bid")) {
      apiExampleText = "TC=104|LotId=M502-999B|Price=1000|MKT=ERD|TMID=435345|UID=as";
    } else if (apiName.equalsIgnoreCase("Get Commission Agent")) {
      apiExampleText = "TC=107|MKT=ERD";
    } else if (apiName.equalsIgnoreCase("Get Lot Count")) {
      apiExampleText = "TC=108|MKT=ERD|CMDT=DRY CHILLY|CTR=-1";
    } else if (apiName.equalsIgnoreCase("Ping")) {
      apiExampleText = "TC=100|";
    } 
    return apiExampleText;
  }
  
  public static interface APPLICATION_TYPE {
    public static final int APP_TYPE_SOCKET = 100;
    
    public static final int APP_TYPE_WEB_SERVICE = 101;
    
    public static final String APP_STRING_TYPE_SOCKET = "Socket Service";
    
    public static final String APP_STRING_TYPE_WEB_SERVICE = "Web Service";
  }
  
  public static interface BIDDING_APP_APIS {
    public static final String LOGIN = "Login";
    
    public static final String GET_COMMODITY = "GetCommodity";
    
    public static final String GET_LOT_OR_INCREMENTAL = "Get Lot/Incremental";
    
    public static final String SUBMIT_BID = "Submit Bid";
    
    public static final String GET_MODIFIED = "Get Modified";
    
    public static final String GET_SUBMITTED_BIDS = "Get Submitted Bids";
    
    public static final String GET_COMMISSION_AGENT = "Get Commission Agent";
    
    public static final String GET_LOT_COUNT = "Get Lot Count";
    
    public static final String PING = "Ping";
  }
  
  public static interface WINDOW {
    public static final int WIDTH = 1000;
    
    public static final int HEIGHT = 600;
  }
}