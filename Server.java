package Server;

public class Server {
	private static Frame f = new Frame("serve");
	private static Label label = new Label("Users List:");
	private static List l = new List();
	private static Socket socket;
	private static Chat_format cf = null;
	private static boolean send_signal = false;
	
    public static void main(String[] arg) throws IOException {
    	boolean had_receive = false;
    	f.add(label, BorderLayout.NORTH);
    	f.add(l, BorderLayout.CENTER);
        int port = 8888;
        ServerSocket server = new ServerSocket(port);
    	f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					socket.close();
			        server.close();
					System.exit(0);
				}
		        catch (Exception ee) {
		        	
		        }
			}
		});
    	f.pack();
    	f.setVisible(true);
        while(true) {
        	socket = server.accept();
        	if(had_receive == false) {
        		had_receive = true;
        		new ServerReceiverThread().start();
        	}
        	new ServerThread().start();
        }
    }
    
    static class ServerThread extends Thread {
    	public void run() {
    		ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
				while(true) {
					if(send_signal == true) {
						send_signal = false;
		        		oos.writeObject(cf);
					}
				}
			} catch (IOException e1) {
				
			}
    	}
    }
    
    static class ServerReceiverThread extends Thread {
    	public void run() {
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
	            while(true) {
	            	try {
						cf = (Chat_format)ois.readObject().clone();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
	        		if(cf.model == 1) {
	        			cf.from = "Server";
	        			boolean same_name = false;
	        			for(int i=0; i<l.getRows(); i++) {
	        				if(l.getItem(i) == cf.content) {
	        					same_name = true;
	        					break;
	        				}
	        			}
	        			if(same_name == true) {
	        				cf.model = 2;
	        				send_signal = true;
	        				while(true) {
	        					if(send_signal == false) {
	        						send_signal = true;
	        						cf.model = 3;
	        						break;
	        					}
	        				}
	        			}
	        			else {
	        				cf.model = 1;
	        				send_signal = true;
	        			}
	        		}
	        		else if(cf.model == 2){
	        			cf.model = 3;
	        			cf.date = new Date();
	        			send_signal = true;
	        		}
	        		else {
	        			while(true) {
	        				if(send_signal == false) {
	        					send_signal = true;
	        					cf.model = 4;
	        					break;
	        				}
	        			}
	        		}
	            }
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public class Chat_format implements Serializable, Cloneable {
    	public String from;
    	public String to;
    	public String content;
    	public int model;
    	public Date date;
    	
    	public Object clone() {
    		Object o = null;
    		try {
    			o = (Chat_format)super.clone();
    		}
    		catch(CloneNotSupportedException e) {
    			e.printStackTrace();
    		}
    		return o;
    	}
    }
}
