/**
 * 
 */
package com.neml.utils;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Vector;

/**
 * @author Neml10345
 *
 */

public class Utils {
  public static Point getCenterForWindow(int windowWidth, int windowHeight) {
    int CENTER_WIDTH = 0;
    int CENTER_HEIGHT = 0;
    Point point = new Point();
    Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
    CENTER_WIDTH = windowSize.width / 2 - windowWidth / 2;
    CENTER_HEIGHT = windowSize.height / 2 - windowHeight / 2;
    point.x = CENTER_WIDTH;
    point.y = CENTER_HEIGHT;
    return point;
  }
  
  public static Vector<String> getClassesFromPackage() {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    String packageName = "com.simulator.forms";
    packageName = packageName.replace(".", "/");
    URL url = classLoader.getResource(packageName);
    try {
      File file = new File(new URI(url.toString()));
      File[] files = file.listFiles();
      Vector<String> list = new Vector<String>();
      for (int i = 0; i < files.length; i++) {
        String fileName = files[i].getName();
        if (!fileName.contains("$")) {
          System.out.println("Class: " + fileName);
          fileName = fileName.substring(0, fileName.indexOf("."));
          list.add(fileName);
        } 
      } 
      return list;
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return null;
    } 
  }
}
