/**
 * 
 */
package com.neml;

	
import com.neml.simulator.Simulator;
import com.neml.utils.Constants;
import com.neml.utils.Utils;
import java.awt.Choice;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.ImageIcon;
//import javax.swing.UIManager
import javax.swing.UIManager;

/**
 * @author Neml10345
 *
 */

public class Application implements Constants.BIDDING_APP_APIS {
  private static final int WINDOW_WIDTH = 300;
  
  private static final int WINDOW_HEIGHT = 300;
  
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception exception) {}
    final Frame frame = new Frame();
    frame.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent windowEvent) {
            System.exit(0);
          }
        });
    //URL imgURL = frame.getClass().getResource("/src/main/java/download.png");
    //URL imgURL = frame.getClass().getResource("/download.png");
    URL imgURL = Application.class.getClassLoader().getResource("download.png");
    ImageIcon icon = new ImageIcon(imgURL);
    frame.setIconImage(icon.getImage());
    frame.setTitle("Simulator");
    final Choice chAPISelector = new Choice();
    chAPISelector.addItem("Select");
    chAPISelector.add("Socket Service");
    chAPISelector.add("Web Service");
    chAPISelector.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent arg0) {
            String item = arg0.getItem().toString();
            frame.setVisible(false);
            frame.dispose();
            int selectedIndex = chAPISelector.getSelectedIndex() - 1;
            Simulator simulator = new Simulator(item);
          }
        });
    frame.add(chAPISelector);
    Point centPoint = Utils.getCenterForWindow(300, 300);
    frame.setLocation(centPoint.x, centPoint.y);
    frame.setVisible(true);
    frame.setSize(300, 300);
  }
}
