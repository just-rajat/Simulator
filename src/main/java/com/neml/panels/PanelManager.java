/**
 * 
 */
package com.neml.panels;

import com.neml.network.SocketClient;
import com.neml.network.WebClient;
import com.neml.network.listener.SocketConnectionListener;
import com.neml.network.listener.WebConnectionListener;
/**
 * @author Neml10345
 *
 */
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

public class PanelManager {
  private Frame _frame;
  
  public PanelManager(Frame frame) {
    this._frame = frame;
  }
  
  public Panel getConnectionPanel() {
    int childWidth = 250;
    Panel panel = new Panel(new FlowLayout());
    Label lblHostIP = new Label();
    lblHostIP.setText("Host IP :");
    lblHostIP.setAlignment(2);
    lblHostIP.setPreferredSize(new Dimension(childWidth / 2, 20));
    final TextField tfHostIP = new TextField();
    tfHostIP.setPreferredSize(new Dimension(childWidth, 20));
    Label lblPort = new Label();
    lblPort.setText("Port :");
    lblPort.setAlignment(2);
    lblPort.setBounds(10, 10, 10, 10);
    lblPort.setPreferredSize(new Dimension(childWidth / 2, 20));
    final TextField tfPort = new TextField();
    tfPort.setBounds(10, 10, 10, 10);
    tfPort.setPreferredSize(new Dimension(childWidth, 20));
    final Button btnConnect = new Button("Connect");
    btnConnect.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            SocketClient client = SocketClient.getInstance();
            client.setLoop(PanelManager.this.getLoopSize());
            client.setDelay(PanelManager.this.getDelay());
            client.setEndSeperator(PanelManager.this.getEndSeperator());
            client.setServerHost(tfHostIP.getText());
            client.setServerPortNumber(Integer.parseInt(tfPort.getText()));
            client.setConnectionListener(new SocketConnectionListener() {
                  public void onReceived(String packet) {
                    System.out.println("onReceived\n" + packet);
                    btnConnect.setBackground(Color.GREEN);
                  }
                  
                  public void onDisconnected(Exception exception) {
                    System.out.println("onDisconnected\n" + exception);
                    btnConnect.setBackground(Color.RED);
                  }
                  
                  public void onConnected() {
                    System.out.println("onConnected\n");
                    btnConnect.setBackground(Color.GREEN);
                  }
                });
            client.execute(null);
          }
        });
    btnConnect.setBackground(Color.RED);
    panel.add(lblHostIP);
    panel.add(tfHostIP);
    panel.add(lblPort);
    panel.add(tfPort);
    panel.add(btnConnect);
    return panel;
  }
  
  public Panel getWebServicePanel() {
    int childWidth = 250;
    Panel panel = new Panel(new FlowLayout());
    panel.setName("WebServiceConfigPanel");
    Label lblHostUrl = new Label();
    lblHostUrl.setText("Host Url :");
    lblHostUrl.setAlignment(2);
    lblHostUrl.setPreferredSize(new Dimension(childWidth / 2, 20));
    TextField tfHostUrl = new TextField();
    tfHostUrl.setName("WebServiceUrl");
    tfHostUrl.setPreferredSize(new Dimension(childWidth, 20));
    Label lblMethodType = new Label();
    lblMethodType.setText("Request Method :");
    lblMethodType.setAlignment(2);
    lblMethodType.setBounds(10, 10, 10, 10);
    lblMethodType.setPreferredSize(new Dimension(childWidth / 2, 20));
    Choice chcMethod = new Choice();
    chcMethod.setName("WebServiceMethod");
    chcMethod.add("Select");
    chcMethod.add("GET");
    chcMethod.add("POST");
    panel.add(lblHostUrl);
    panel.add(tfHostUrl);
    panel.add(lblMethodType);
    panel.add(chcMethod);
    return panel;
  }
  
  public synchronized Panel getAPISelectionPanel(final Frame parent, final Vector<String[]> apis) {
    Panel panel = new Panel();
    final Choice comboBox = new Choice();
    comboBox.addItem("Select");
    for (int i = 0; i < apis.size(); i++)
      comboBox.addItem(((String[])apis.get(i))[0]); 
    comboBox.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent ie) {
            int requestApiSelectedIndex = comboBox.getSelectedIndex() - 1;
            PanelManager.this.updateRequestBox(parent, ie.getID(), apis.get(requestApiSelectedIndex));
          }
        });
    comboBox.select(0);
    panel.add(comboBox);
    return panel;
  }
  
  public Panel getRequestPanel(final int appType) {
    int childWidth = 250;
    Panel panel = new Panel(new FlowLayout());
    panel.setName("RequestPanel");
    Panel reqLblPanel = new Panel(new FlowLayout());
    Label requestLabel = new Label();
    requestLabel.setText("Request");
    requestLabel.setAlignment(1);
    requestLabel.setPreferredSize(new Dimension(100, 20));
    reqLblPanel.add(requestLabel);
    Panel reqTFPanel = new Panel(new FlowLayout());
    reqTFPanel.setName("localRequestPanel");
    final TextArea requestTF = new TextArea();
    requestTF.setName("req");
    requestTF.setBounds(10, 10, 10, 10);
    requestTF.setPreferredSize(new Dimension(childWidth * 3, 60));
    reqTFPanel.add(requestTF);
    final Panel panelButton = new Panel();
    Button btnSendReq = new Button("Send Request");
    btnSendReq.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            SocketClient client = SocketClient.getInstance();
            client.setLoop(PanelManager.this.getLoopSize());
            client.setDelay(PanelManager.this.getDelay());
            client.setEndSeperator(PanelManager.this.getEndSeperator());
            for (int i = 0; i < client.getLoop(); i++) {
              if (appType == 100) {
                client.setConnectionListener(new SocketConnectionListener() {
                      public void onReceived(String packet) {
                        System.out.println("onReceived\n" + packet);
                        panelButton.setBackground(Color.GREEN);
                       // PanelManager.access$0(PanelManager.null.this).updateResponseBox((PanelManager.null.access$0(PanelManager.null.this))._frame, packet);
                        PanelManager.this.updateResponseBox(PanelManager.this._frame, packet);
                      }
                      
                      public void onDisconnected(Exception exception) {
                        System.out.println("onDisconnected\n" + exception);
                        panelButton.setBackground(Color.RED);
                      //  PanelManager.null.access$0(PanelManager.null.this).updateResponseBox((PanelManager.null.access$0(PanelManager.null.this))._frame, exception.getMessage());
                        //PanelManager.access$0(PanelManager.this).updateResponseBox((PanelManager.access$0(PanelManager.this))._frame, exception.getMessage());
                        PanelManager.this.updateResponseBox(PanelManager.this._frame,  exception.getMessage());
                      }
                      
                      public void onConnected() {
                        System.out.println("onConnected\n");
                        panelButton.setBackground(Color.GREEN);
                      }
                    });
                client.execute(requestTF.getText().trim());
               // PanelManager.this.getTextField("ConfigPanel", "_LoopCount").setText(client.getLoop());
                PanelManager.this.getTextField("ConfigPanel", "_LoopCount").setText(String.valueOf(client.getLoop()));
               // PanelManager.this.getTextField("ConfigPanel", "_Delay").setText(client.getDelay());
                PanelManager.this.getTextField("ConfigPanel", "_Delay").setText(String.valueOf(client.getDelay()));
               // PanelManager.this.getTextField("ConfigPanel", "_EndSepeartor").setText(client.getEndSeperator());
                PanelManager.this.getTextField("ConfigPanel", "_EndSepeartor").setText(String.valueOf(client.getEndSeperator()));
              } else if (appType == 101) {
                WebClient webClient = new WebClient(PanelManager.this.getUrl(PanelManager.this._frame), PanelManager.this.getMethod(PanelManager.this._frame));
                webClient.setConnectionListener(new WebConnectionListener() {
                      public void onError(Exception exception) {
                        System.out.println("onDisconnected\n" + exception);
                        panelButton.setBackground(Color.RED);
                       // PanelManager.null.access$0(PanelManager.null.this).updateResponseBox((PanelManager.null.access$0(PanelManager.null.this))._frame, exception.getMessage());
                        PanelManager.this.updateResponseBox(PanelManager.this._frame,  exception.getMessage());
                      }
                      
                      public void onComplete(String response) {
                        System.out.println("onReceived\n" + response);
                        panelButton.setBackground(Color.GREEN);
                        //PanelManager.null.access$0(PanelManager.null.this).updateResponseBox((PanelManager.null.access$0(PanelManager.null.this))._frame, response);
                        PanelManager.this.updateResponseBox(PanelManager.this._frame,  response);
                      }
                    });
                webClient.execute(requestTF.getText().trim());
              } 
            } 
          }
        });
    panelButton.add(btnSendReq);
    panelButton.setBackground(Color.RED);
    panel.add(reqLblPanel);
    panel.add(reqTFPanel);
    panel.add(panelButton);
    return panel;
  }
  
  public Panel getResponsePanel() {
    int childWidth = 250;
    Panel panel = new Panel(new FlowLayout());
    panel.setName("ResponsePanel");
    Panel respLblPanel = new Panel(new FlowLayout());
    Label responseLabel = new Label();
    responseLabel.setText("Response");
    responseLabel.setAlignment(0);
    responseLabel.setBounds(10, 10, 10, 10);
    responseLabel.setPreferredSize(new Dimension(100, 20));
    respLblPanel.add(responseLabel);
    TextArea responseTF = new TextArea();
    responseTF.setName("resp");
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setName("localResponsePanel");
    scrollPane.add(responseTF);
    scrollPane.setBounds(10, 10, 10, 10);
    scrollPane.setPreferredSize(new Dimension(childWidth * 3, 300));
    panel.add(respLblPanel);
    panel.add(scrollPane);
    return panel;
  }
  
  public void updateRequestBox(Frame parent, int selectedApiIndex, String[] samples) {
    Component[] components = parent.getComponents();
    for (int i = 0; i < components.length; i++) {
      if (components[i] instanceof Panel && components[i].getName().equalsIgnoreCase("RequestPanel")) {
        Component[] innerComponents = ((Panel)components[i]).getComponents();
        for (int j = 0; j < innerComponents.length; j++) {
          Component[] childs = ((Panel)innerComponents[j]).getComponents();
          for (int k = 0; k < childs.length; k++) {
            if (childs[k] instanceof TextArea) {
              TextArea requestTF = (TextArea)childs[k];
              String tag = requestTF.getName();
              if (tag != null && tag.equalsIgnoreCase("req")) {
                requestTF.setText(samples[1]);
                break;
              } 
            } 
          } 
        } 
      } 
    } 
  }
  
  public void updateResponseBox(Frame parent, String outputText) {
    Component[] components = parent.getComponents();
    for (int i = 0; i < components.length; i++) {
      if (components[i] instanceof Panel && components[i].getName().equalsIgnoreCase("ResponsePanel")) {
        Component[] innerComponents = ((Panel)components[i]).getComponents();
        for (int j = 0; j < innerComponents.length; j++) {
          Component[] childs;
          try {
            childs = ((Panel)innerComponents[j]).getComponents();
          } catch (Exception e) {
            childs = ((ScrollPane)innerComponents[j]).getComponents();
          } 
          for (int k = 0; k < childs.length; k++) {
            if (childs[k] instanceof TextArea) {
              TextArea responseTF = (TextArea)childs[k];
              String tag = responseTF.getName();
              if (tag != null && tag.equalsIgnoreCase("resp")) {
                responseTF.setText(outputText.trim());
                break;
              } 
            } 
          } 
        } 
      } 
    } 
  }
  
  private String getUrl(Frame parent) {
    String url = "";
    Component[] components = parent.getComponents();
    for (int i = 0; i < components.length; i++) {
      Component comp = components[i];
      if (comp instanceof Panel && comp.getName().equalsIgnoreCase("WebServiceConfigPanel")) {
        Component[] innerComponents = ((Panel)comp).getComponents();
        for (int j = 0; j < innerComponents.length; j++) {
          if (innerComponents[j] instanceof TextField) {
            TextField tfUrl = (TextField)innerComponents[j];
            String tag = tfUrl.getName();
            if (tag != null && tag.equalsIgnoreCase("WebServiceUrl")) {
              url = tfUrl.getText();
              break;
            } 
          } 
        } 
      } 
    } 
    return url;
  }
  
  private String getMethod(Frame parent) {
    String method = "";
    Component[] components = parent.getComponents();
    for (int i = 0; i < components.length; i++) {
      Component comp = components[i];
      if (comp instanceof Panel && comp.getName().equalsIgnoreCase("WebServiceConfigPanel")) {
        Component[] innerComponents = ((Panel)comp).getComponents();
        for (int j = 0; j < innerComponents.length; j++) {
          if (innerComponents[j] instanceof Choice) {
            Choice chcMethod = (Choice)innerComponents[j];
            String tag = chcMethod.getName();
            if (tag != null && tag.equalsIgnoreCase("WebServiceMethod")) {
              method = chcMethod.getSelectedItem();
              break;
            } 
          } 
        } 
      } 
    } 
    return method;
  }
  
  public Panel getConfigPanel() {
    Panel panel = new Panel(new FlowLayout());
    panel.setName("ConfigPanel");
    Label lblLoopSize = new Label();
    lblLoopSize.setText("Loop Size :");
    lblLoopSize.setAlignment(2);
    lblLoopSize.setPreferredSize(new Dimension(62, 20));
    TextField tfLoopSize = new TextField();
    tfLoopSize.setName("_LoopCount");
    tfLoopSize.setPreferredSize(new Dimension(125, 20));
    tfLoopSize.setText("1");
    Label lblDelay = new Label();
    lblDelay.setText("Delay (Miliseconds) :");
    lblDelay.setAlignment(2);
    lblDelay.setBounds(10, 10, 10, 10);
    lblDelay.setPreferredSize(new Dimension(125, 20));
    TextField tfDelay = new TextField();
    tfDelay.setName("_Delay");
    tfDelay.setBounds(10, 10, 10, 10);
    tfDelay.setPreferredSize(new Dimension(125, 20));
    tfDelay.setText("0");
    Label lblEndSeperator = new Label();
    lblEndSeperator.setText("End Seperator");
    lblEndSeperator.setAlignment(2);
    lblEndSeperator.setBounds(10, 10, 10, 10);
    lblEndSeperator.setPreferredSize(new Dimension(125, 20));
    TextField tfEndSeperator = new TextField();
    tfEndSeperator.setName("_EndSepeartor");
    tfEndSeperator.setBounds(10, 10, 10, 10);
    tfEndSeperator.setPreferredSize(new Dimension(125, 20));
    tfEndSeperator.setText("^");
    panel.add(lblLoopSize);
    panel.add(tfLoopSize);
    panel.add(lblDelay);
    panel.add(tfDelay);
    panel.add(lblEndSeperator);
    panel.add(tfEndSeperator);
    return panel;
  }
  
  public int getLoopSize() {
    int loopSize = 0;
    Component[] components = this._frame.getComponents();
    for (int i = 0; i < components.length; i++) {
      if (components[i] instanceof Panel && components[i].getName().equalsIgnoreCase("ConfigPanel")) {
        Component[] innerComponents = ((Panel)components[i]).getComponents();
        for (int j = 0; j < innerComponents.length; j++) {
          if (innerComponents[j] instanceof TextField) {
            TextField tf = (TextField)innerComponents[j];
            String tag = tf.getName();
            if (tag != null && tag.equalsIgnoreCase("_LoopCount")) {
              try {
                loopSize = Integer.parseInt(tf.getText().toString());
              } catch (Exception e) {
                e.printStackTrace();
              } 
              break;
            } 
          } 
        } 
      } 
    } 
    return loopSize;
  }
  
  public long getDelay() {
    long delay = 0L;
    Component[] components = this._frame.getComponents();
    for (int i = 0; i < components.length; i++) {
      if (components[i] instanceof Panel && components[i].getName().equalsIgnoreCase("ConfigPanel")) {
        Component[] innerComponents = ((Panel)components[i]).getComponents();
        for (int j = 0; j < innerComponents.length; j++) {
          if (innerComponents[j] instanceof TextField) {
            TextField tf = (TextField)innerComponents[j];
            String tag = tf.getName();
            if (tag != null && tag.equalsIgnoreCase("_Delay")) {
              try {
                delay = Long.parseLong(tf.getText().toString());
              } catch (Exception e) {
                e.printStackTrace();
              } 
              break;
            } 
          } 
        } 
      } 
    } 
    return delay;
  }
  
  public String getEndSeperator() {
    String endSeperator = "^";
    Component[] components = this._frame.getComponents();
    for (int i = 0; i < components.length; i++) {
      if (components[i] instanceof Panel && components[i].getName().equalsIgnoreCase("ConfigPanel")) {
        Component[] innerComponents = ((Panel)components[i]).getComponents();
        for (int j = 0; j < innerComponents.length; j++) {
          if (innerComponents[j] instanceof TextField) {
            TextField tf = (TextField)innerComponents[j];
            String tag = tf.getName();
            if (tag != null && tag.equalsIgnoreCase("_EndSepeartor")) {
              try {
                endSeperator = tf.getText().toString();
              } catch (Exception e) {
                e.printStackTrace();
              } 
              break;
            } 
          } 
        } 
      } 
    } 
    return endSeperator;
  }
  
  public TextField getTextField(String panelTagName, String tagName) {
    TextField requestedtf = null;
    Component[] components = this._frame.getComponents();
    for (int i = 0; i < components.length; i++) {
      if (components[i] instanceof Panel && 
        components[i].getName().equalsIgnoreCase(panelTagName)) {
        Component[] innerComponents = ((Panel)components[i]).getComponents();
        for (int j = 0; j < innerComponents.length; j++) {
          if (innerComponents[j] instanceof TextField) {
            TextField tf = (TextField)innerComponents[j];
            String tag = tf.getName();
            if (tag != null && tag.equalsIgnoreCase(tagName)) {
              requestedtf = tf;
              break;
            } 
          } 
        } 
      } 
    } 
    return requestedtf;
  }
}
