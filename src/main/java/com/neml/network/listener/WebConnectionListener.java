/**
 * 
 */
package com.neml.network.listener;

/**
 * @author Neml10345
 *
 */
public interface WebConnectionListener {
	  void onError(Exception paramException);
	  
	  void onComplete(String paramString);
	}
