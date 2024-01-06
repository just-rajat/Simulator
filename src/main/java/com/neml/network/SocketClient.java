/**
 * 
 */
package com.neml.network;
import com.neml.network.listener.SocketConnectionListener;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.Socket;
/**
 * @author Neml10345
 *
 */

public class SocketClient {
  private Socket socketClient;
  
  private SocketConnectionListener mListener;
  
  private Thread packetPoolThread;
  
  private static SocketClient _instance;
  
  private String m_Response = "";
  
  private String mHostIP;
  
  private int mPort;
  
  private int mTimeout;
  
  private int loop;
  
  private long delay;
  
  private String endSeperator;
  
  private SocketClient() {
    this.socketClient = new Socket();
  }
  
  public static SocketClient getInstance() {
    if (_instance == null)
      _instance = new SocketClient(); 
    return _instance;
  }
  
  public void setServerHost(String hostIp) {
    this.mHostIP = hostIp;
  }
  
  public void setServerPortNumber(int port) {
    this.mPort = port;
  }
  
  public void setTimeout(int timeout) {
    this.mTimeout = timeout;
  }
  
  public void setConnectionListener(SocketConnectionListener listener) {
    this.mListener = listener;
  }
  
  public synchronized void execute(final String request) {
    if (request == null) {
      System.out.println("Connection API");
    } else {
      System.out.println(request);
    } 
    this.packetPoolThread = new Thread(new Runnable() {
          public void run() {
            try {
              if (request != null) {
                if (SocketClient.this.socketClient == null)
                  return; 
              } else if (request == null) {
                SocketClient.this.socketClient = null;
              } 
              if (SocketClient.this.socketClient == null || !SocketClient.this.socketClient.isBound() || 
                SocketClient.this.socketClient.isClosed()) {
                SocketClient.this.socketClient = new Socket(SocketClient.this.mHostIP, SocketClient.this.mPort);
                SocketClient.this.socketClient.setKeepAlive(true);
                SocketClient.this.socketClient.setSoTimeout(SocketClient.this.mTimeout);
              } 
              if (request == null) {
                if (!SocketClient.this.socketClient.isClosed()) {
                  SocketClient.this.mListener.onConnected();
                  return;
                } 
                SocketClient.this.mListener.onDisconnected(new Exception());
                return;
              } 
              InputStream inputStream = SocketClient.this.socketClient.getInputStream();
              SocketClient.this.socketClient.getOutputStream().write(request.getBytes());
              ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10240);
              byte[] buffer = new byte[10240];
              int bytesRead = 0;
              int startIndex = 0;
              while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                SocketClient.this.m_Response = byteArrayOutputStream.toString();
                startIndex = SocketClient.this.m_Response.length();
                if (SocketClient.this.m_Response.contains(SocketClient.this.getEndSeperator()))
                  break; 
              } 
              SocketClient.this.mListener.onReceived(SocketClient.this.m_Response);
            } catch (Exception e) {
              SocketClient.this.mListener.onDisconnected(e);
            } 
          }
        });
    this.packetPoolThread.start();
  }
  
  public void close() throws Exception {
    this.socketClient.close();
  }
  
  public int getLoop() {
    return this.loop;
  }
  
  public void setLoop(int loop) {
    this.loop = loop;
  }
  
  public long getDelay() {
    return this.delay;
  }
  
  public void setDelay(long delay) {
    this.delay = delay;
  }
  
  public void setEndSeperator(String endSeperator) {
    this.endSeperator = endSeperator;
  }
  
  public String getEndSeperator() {
    if (this.endSeperator == null || this.endSeperator.equalsIgnoreCase("") || 
      this.endSeperator.equalsIgnoreCase("null"))
      this.endSeperator = "^"; 
    return this.endSeperator;
  }
}