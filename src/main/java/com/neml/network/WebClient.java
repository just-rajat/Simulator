/**
 * 
 */
package com.neml.network;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import com.neml.network.listener.WebConnectionListener;
/**
 * @author Neml10345
 *
 */

public class WebClient {
  private String _url;
  
  private String _method;
  
  private WebConnectionListener webConnectionListener;
  
  public WebClient(String urlHost, String methodType) {
    this._url = urlHost;
    this._method = methodType;
  }
  
  public void setConnectionListener(WebConnectionListener listener) {
    this.webConnectionListener = listener;
  }
  
  public void execute(String params) {
    String response = "";
    if (this._method.equalsIgnoreCase("get"))
      this._url = String.valueOf(this._url) + params; 
    try {
      URL url = new URL(this._url);
      if (this._url.contains("https")) {
        HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.29.2.233", 8080));
        conn = (HttpsURLConnection)url.openConnection(proxy);
        conn.setDoOutput(true);
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestMethod(this._method);
        conn.setReadTimeout(10000);
        if (this._method.equalsIgnoreCase("post")) {
          OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
          writer.write(params);
          writer.flush();
          writer.close();
        } 
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null)
          response = String.valueOf(response) + line; 
        reader.close();
        this.webConnectionListener.onComplete(response);
      } else {
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        System.out.println(" Proxy reqired ? " + conn.usingProxy());
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.29.2.233", 8080));
        conn = (HttpURLConnection)url.openConnection(proxy);
        conn.setDoOutput(true);
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestMethod(this._method);
        conn.setReadTimeout(10000);
        if (this._method.equalsIgnoreCase("post")) {
          OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
          writer.write(params);
          writer.flush();
          writer.close();
        } 
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null)
          response = String.valueOf(response) + line; 
        reader.close();
        this.webConnectionListener.onComplete(response);
      } 
    } catch (Exception e) {
      this.webConnectionListener.onError(e);
    } 
  }
}
