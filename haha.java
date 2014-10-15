package frame_test_menu;

import java.awt.*;
import java.awt.event.MouseListener;

public class haha implements MouseListener {
	Frame f;
	Label l;
	PopupMenu pm;
	
	public static void main(String[] argv) {
		new haha();
	}
	
	public haha() {
		f = new Frame("menu");
		f.addMouseListener(this);
		MenuItem ite1_1 = new MenuItem("Ite1_1");
		MenuItem ite1_2 = new MenuItem("Ite1_2");
		MenuItem ite1_3 = new MenuItem("Ite1_3");
		ite1_1.addActionListener(this);
	}
}
