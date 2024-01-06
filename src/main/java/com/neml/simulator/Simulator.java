/**
 * 
 */
package com.neml.simulator;

import java.awt.Frame;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.neml.network.SocketClient;
import com.neml.panels.PanelManager;
import com.neml.utils.Utils;
/**
 * @author Neml10345
 *
 */
public class Simulator extends Frame {
	private static final long serialVersionUID = -6415674506293036179L;
	  
	  private final int _WIDTH = 1000;
	  
	  private final int _HEIGHT = 600;
	  
	  String[] apiNames;
	  
	  public Simulator(String name) {
	    super("Simulator for : " + name);
	    URL imgURL = getClass().getResource("/download.png");
	    ImageIcon icon = new ImageIcon(imgURL);
	    setIconImage(icon.getImage());
	    setSize(1000, 600);
	    setLayout(new FlowLayout());
	    addWindowListener(new WindowAdapter() {
	          public void windowClosing(WindowEvent windowEvent) {
	            try {
	              SocketClient.getInstance().close();
	            } catch (Exception e) {
	              e.printStackTrace();
	            } 
	            System.exit(0);
	          }
	        });
	    setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	    createMenus();
	    int type = -1;
	    PanelManager panelManager = new PanelManager(this);
	    if (name.equalsIgnoreCase("Socket Service")) {
	      type = 100;
	      add(panelManager.getConnectionPanel());
	    } else {
	      type = 101;
	      add(panelManager.getWebServicePanel());
	    } 
	    add(panelManager.getConfigPanel());
	    add(panelManager.getRequestPanel(type));
	    add(panelManager.getResponsePanel());
	    Point centerLocation = Utils.getCenterForWindow(1000, 600);
	    setLocation(centerLocation.x, centerLocation.y);
	    setVisible(true);
	  }
	  
	  private void createMenus() {
	    MenuBar menuBar = new MenuBar();
	    Menu menu = new Menu("Help");
	    MenuItem miAbout = new MenuItem("About");
	    miAbout.addActionListener(new ActionListener() {
	          public void actionPerformed(ActionEvent arg0) {
	            Simulator.this.showHelpMessage();
	          }
	        });
	    MenuItem miUsage = new MenuItem("Usage");
	    miUsage.addActionListener(new ActionListener() {
	          public void actionPerformed(ActionEvent arg0) {
	            Simulator.this.showUsageMessage();
	          }
	        });
	    MenuItem miExit = new MenuItem("Exit");
	    miExit.addActionListener(new ActionListener() {
	          public void actionPerformed(ActionEvent arg0) {
	            System.exit(0);
	          }
	        });
	    menu.add(miAbout);
	    menu.add(miUsage);
	    menu.add(miExit);
	    menuBar.add(menu);
	    setMenuBar(menuBar);
	  }
	  
	  private void showHelpMessage() {
	    URL imgURL = getClass().getResource("/images.jpg");
	    ImageIcon image = new ImageIcon(imgURL);
	    String msg = "Design By - Android Team";
	    JOptionPane.showMessageDialog(null, msg, "Simulator v0.7", 1, image);
	  }
	  
	  private void showUsageMessage() {
	    String msg = " 1. Loop size will work on single socket connection and on same thread.";
	    URL imgURL = getClass().getResource("/images.jpg");
	    ImageIcon image = new ImageIcon(imgURL);
	    JOptionPane.showMessageDialog(null, msg, "Simulator v0.7", 1, 
	        image);
	  }
}
