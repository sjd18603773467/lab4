package Client;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Vector;
import java.awt.*;
import java.awt.event.*;

public class Client {
	private static Chat_format cf = new Chat_format();
	private static String myName;
	private static ObjectOutputStream oos;
	
    public static void main(String[] args) throws Exception {
    	Frame f = new Frame("client");
    	TextArea ta1 = new TextArea();
    	ta1.setEditable(false);
    	TextArea ta2 = new TextArea();
    	Button b = new Button("send");
    	Choice c = new Choice();
    	f.add(ta1);
    	f.add(ta2, BorderLayout.SOUTH);
    	f.add(c, BorderLayout.NORTH);
    	f.add(b, BorderLayout.EAST);
    	f.pack();
    	f.setVisible(true);
    	
    	Dialog d = new Dialog(f, "Please input name", false);
    	TextField tf = new TextField();
    	Button ok = new Button("OK");
    	d.add(tf);
    	d.add(ok, BorderLayout.SOUTH);
    	d.pack();
    	d.setVisible(true);
    	
        String host = "127.0.0.1";
        int port = 8888;
        Socket client = new Socket(host, port);
System.out.println("^_^");
        d.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
					System.exit(0);
			}
		});
    	ok.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {

    	    	System.out.println("^_^");
    			cf.from = tf.getText();
    			cf.to = "Server";
    			cf.model = 1;
				try {
					oos = new ObjectOutputStream(client.getOutputStream());
	        		oos.writeObject(cf);
				} catch (IOException e1) {
					
				}
    		}
    	});
        b.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cf.from = myName;
    			cf.to = c.getSelectedItem();
    			cf.content = ta2.getText();
    			cf.model = 2;
				try {
					oos = new ObjectOutputStream(client.getOutputStream());
	        		oos.writeObject(cf);
				} catch (IOException e1) {
					
				}
        	}
        });
        f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
		    		cf.model = 4;
		    		cf.content = myName;
		    		try {
						oos = new ObjectOutputStream(client.getOutputStream());
		        		oos.writeObject(cf);
					} catch (IOException e1) {
						
					}
			        client.close();
					System.exit(0);
				}
		        catch (Exception ee) {
		        	ee.printStackTrace();
		        }
			}
		});

		ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
        while(true) {
        	cf = (Chat_format)ois.readObject();
    		if(cf.from == "Server") {
    			if(cf.model == 1) {
    				myName = cf.content;
    				f.setTitle(myName);
    				d.dispose();
    			}
    			else if(cf.model == 2){
    				tf.setText("");
    			}
    			else if(cf.model == 3) {
    				c.add(cf.content);
    			}
    			else {
    				c.remove(cf.content);
    			}
    		}
    		else {
    			if(cf.to == myName) {
	    			ta1.append(cf.from + " ");
	    			ta1.append(cf.date.toString() + "\n");
	    			ta1.append(cf.content);
    			}
    		}
        }
    }
//    public class Chat_format implements Serializable {
//    	public String from = new String();
//    	public String to = new String();
//    	public String content = new String();
//    	public int model;
//    	public Date date = new Date();
//    }
}
class Chat_format implements Serializable {
	public String from ;
	public String to;
	public String content;
	public int model;
	public Date date;
	Chat_format(){
		from = new String();
		to = new String();
	    content = new String();
		date = new Date();
	}
}

add a line.
add a line.
add two lines.
dlajdslfjsdfl
askfjsaldkfjsdlkf
