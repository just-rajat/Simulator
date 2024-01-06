/**
 * 
 */
package com.neml.network.listener;

/**
 * @author Neml10345
 *
 */

public interface SocketConnectionListener {
  void onDisconnected(Exception paramException);
  
  void onConnected();
  
  void onReceived(String paramString);
}
