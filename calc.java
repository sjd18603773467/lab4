package calc;

import java.awt.*;
import java.awt.event.*;

public class calc {
	
	private static String[] s = {"seven", "eight", "nine", "add",
					  			 "four", "five", "six", "sub",
					  			 "one", "two", "three", "mul",
					  			 "point", "zero", "equal", "div"};
	private static String[] v = {"7", "8", "9", "+",
						  	 	 "4", "5", "6", "-",
						  	 	 "1", "2", "3", "*",
						  	 	 ".", "0", "=", "/"};
	static Label l_up = new Label("", Label.RIGHT);
	static Label l_down = new Label("", Label.RIGHT);
	static Frame frame = new Frame("Calc");
	
	public static void main(String[] argv) {
		new calc();
	}
	
	public calc() {
		Panel p_up = new Panel(new GridLayout(2,1));
		Panel p_down = new Panel(new GridLayout(4,4,2,2));
		Button[] b = new Button[16];
		
		l_up.setSize(100, 20);
		l_down.setSize(100, 20);
		
		for(int i=0; i<16; i++) {
			b[i] = new Button(v[i]);
			b[i].setActionCommand(s[i]);
			b[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					String cmd = e.getActionCommand();
					String label = l_up.getText();
					if(label.length() != 0 && label.charAt(label.length() - 1) == '=') {
						l_up.setText("");
						l_down.setText("");
						label = "";
					}
					for(int i=0; i<16; i++)
						if(cmd.equals(s[i])) {
							label += v[i];
							l_up.setText(label);
						}
					if(label.charAt(label.length() - 1) == '=') {
						for(int i=0; i<label.length(); i++) {
							if(label.charAt(i) == '+' || label.charAt(i) == '-' || label.charAt(i) == '*' || label.charAt(i) == '/') {
								float f1 = Float.parseFloat(label.substring(0, i));
								float f2 = Float.parseFloat(label.substring(i + 1, label.length() - 1));
								float f3 = 0;
								switch(label.charAt(i)) {
									case '+':
										f3 = f1 + f2;
										break;
									case '-':
										f3 = f1 - f2;
										break;
									case '*':
										f3 = f1 * f2;
										break;
									case '/':
										f3 = f1 / f2;
										break;
									default:
										break;
								}
								l_down.setText(Float.toString(f3));
							}
						}
					}
				}
			});
			p_down.add(b[i]);
		}
		p_up.add(l_up);
		p_up.add(l_down);
		
		frame.setLayout(new BorderLayout());
		frame.add(p_up, BorderLayout.NORTH);
		frame.add(p_down, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}


