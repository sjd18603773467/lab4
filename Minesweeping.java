slkdfjsldfj

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Minesweeping {
	private static Frame f = new Frame("Minesweeping");
	private static Panel p1 = new Panel();
	private static Panel p2 = new Panel();
	
	private static int width;
	private static int height;
	private static int mine;
	
	private static boolean over = false;
	private static Button[] b_mine = null;
	private static Label l_time = new Label();
	private static Label l_mine = new Label();
	
	public static void main(String[] args) {
		Button[] b = new Button[4];
		b[0] = new Button("9*9-10");
		b[1] = new Button("16*16-40");
		b[2] = new Button("16*30-99");
		b[3] = new Button("user-define");
		p1.setLayout(new GridLayout(2, 2));
		for(int i=0; i<4; i++)
			p1.add(b[i]);
		f.add(p1);
		f.pack();
		f.setVisible(true);
		b[0].addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		width = height = 9;
        		mine = 10;
        		new_panel();
        	}
		});
		b[1].addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		width = height = 16;
        		mine = 40;
        		new_panel();
        	}
		});
		b[2].addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		width = 30;
        		height = 16;
        		mine = 99;
        		new_panel();
        	}
		});
		b[3].addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Dialog d = new Dialog(f);
        		Label l1 = new Label("width:");
        		Label l2 = new Label("height:");
        		Label l3 = new Label("mine:");
        		TextField t_width = new TextField();
        		TextField t_height = new TextField();
        		TextField t_mine = new TextField();
        		Button b_ok = new Button("ok");
        		d.setLayout(new GridLayout(4, 2));
        		d.add(l1);
        		d.add(t_width);
        		d.add(l2);
        		d.add(t_height);
        		d.add(l3);
        		d.add(t_mine);
        		d.add(b_ok);
        		b_ok.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
            			width = Integer.parseInt(t_width.getText());
        				height = Integer.parseInt(t_height.getText());
        				mine = Integer.parseInt(t_mine.getText());
        				d.dispose();
        				new_panel();
        			}
        		});
        		d.pack();
        		d.setVisible(true);
        	}
		});
        f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
        });
	}
	
	public static void new_panel() {
		p2 = new Panel();
		over = false;
		l_mine.setText("");
		Panel p_up = new Panel();
		Panel p_down = new Panel();
		Button b_again = new Button("again");
		b_again.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.remove(p2);
				f.add(p1);
				f.pack();
				f.setVisible(true);
			}
		});
		p_up.setLayout(new FlowLayout());
		p_up.add(l_time);
		p_up.add(b_again);
		p_up.add(l_mine);
		p_down.setLayout(new GridLayout(height, width));
		p2.setLayout(new BorderLayout());
		p2.add(p_up, BorderLayout.NORTH);
		p2.add(p_down, BorderLayout.CENTER);
		b_mine = new Button[width*height];
		int[] i_mine = new int[width*height];
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				int index = i * width + j;
				i_mine[index] = 10;
				b_mine[index] = new Button();
				b_mine[index].setBackground(Color.gray);
				b_mine[index].addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						if(over)
							return;
						if(e.getButton() == e.BUTTON1) {
							for(int i=0; i<width*height; i++) {
								if(e.getSource() == b_mine[i]) {
									unfold(b_mine, i_mine, i, true);
								}
							}
						}
						if(e.getButton() == e.BUTTON3) {
							if(over)
								return;
							for(int i=0; i<width*height; i++) {
								if(e.getSource() == b_mine[i]) {
									if(b_mine[i].getBackground() == Color.gray) {
										b_mine[i].setBackground(Color.red);
										i_mine[i] = 13;
									}
									else if(b_mine[i].getBackground() == Color.red) {
										b_mine[i].setBackground(Color.gray);
										i_mine[i] = 11;
									}
									break;
								}
							}
						}
					}
				});
				p_down.add(b_mine[index]);
			}
		}
		Random ra = new Random();
		for(int i=0; i<mine; ) {
			int t = ra.nextInt(width*height);
			if(i_mine[t] == 10) {
				i_mine[t] = 11;
				i ++;
			}
		}
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				if(i_mine[i*width+j] == 11)
					continue;
				i_mine[i*width+j] = 0;
				if(i > 0 && j > 0 && i_mine[(i-1)*width+j-1] == 11)
					i_mine[i*width+j] ++;
				if(i > 0 && i_mine[(i-1)*width+j] == 11)
					i_mine[i*width+j] ++;
				if(i > 0 && j < width-1 && i_mine[(i-1)*width+j+1] == 11)
					i_mine[i*width+j] ++;
				if(j < width-1 && i_mine[i*width+j+1] == 11)
					i_mine[i*width+j] ++;
				if(i < height-1 && j < width-1 && i_mine[(i+1)*width+j+1] == 11)
					i_mine[i*width+j] ++;
				if(i < height-1 && i_mine[(i+1)*width+j] == 11)
					i_mine[i*width+j] ++;
				if(i < height-1 && j > 0 && i_mine[(i+1)*width+j-1] == 11)
					i_mine[i*width+j] ++;
				if(j > 0 && i_mine[i*width+j-1] == 11)
					i_mine[i*width+j] ++;
			}
		}
		f.remove(p1);
		f.add(p2);
		f.pack();
		f.setVisible(true);
	}
	
	public static void unfold(Button[] b_mine, int[] i_mine, int index, boolean hit) {
		int i = index / width;
		int j = index % width;
		if(hit && i_mine[index] == 11) {
			over = true;
			l_mine.setText("Game Over!");
		}
		else if(b_mine[index].getBackground() == Color.white) {
			/*int sum = 0;
			if(i > 0 && j > 0 && i_mine[(i-1)*width+j-1] == 13)
				sum ++;
			if(i > 0 && i_mine[(i-1)*width+j] == 13)
				sum ++;
			if(i > 0 && j < width-1 && i_mine[(i-1)*width+j+1] == 13)
				sum ++;
			if(j < width-1 && i_mine[i*width+j+1] == 13)
				sum ++;
			if(i < height-1 && j < width-1 && i_mine[(i+1)*width+j+1] == 13)
				sum ++;
			if(i < height-1 && i_mine[(i+1)*width+j] == 13)
				sum ++;
			if(i < height-1 && j > 0 && i_mine[(i+1)*width+j-1] == 13)
				sum ++;
			if(j > 0 && i_mine[i*width+j-1] == 13)
				sum ++;
			if(sum == i_mine[index]) {
				if(i > 0 && j > 0 && i_mine[(i-1)*width+j-1] != 13)
					unfold(b_mine, i_mine, (i-1)*width+j-1, true);
				if(i > 0 && i_mine[(i-1)*width+j] != 13)
					unfold(b_mine, i_mine, (i-1)*width+j, true);
				if(i > 0 && j < width-1 && i_mine[(i-1)*width+j+1] != 13)
					unfold(b_mine, i_mine, (i-1)*width+j+1, true);
				if(j < width-1 && i_mine[i*width+j+1] != 13)
					unfold(b_mine, i_mine, i*width+j+1, true);
				if(i < height-1 && j < width-1 && i_mine[(i+1)*width+j+1] != 13)
					unfold(b_mine, i_mine, (i+1)*width+j+1, true);
				if(i < height-1 && i_mine[(i+1)*width+j] != 13)
					unfold(b_mine, i_mine, (i+1)*width+j, true);
				if(i < height-1 && j > 0 && i_mine[(i+1)*width+j-1] != 13)
					unfold(b_mine, i_mine, (i+1)*width+j-1, true);
				if(j > 0 && i_mine[i*width+j-1] != 13)
					unfold(b_mine, i_mine, i*width+j-1, true);
			}*/
		}
		else if(i_mine[index] == 0) {
			b_mine[index].setBackground(Color.white);
			if(i > 0)
				unfold(b_mine, i_mine, index-width, false);
			if(j > 0)
				unfold(b_mine, i_mine, index-1, false);
			if(i < height-1)
				unfold(b_mine, i_mine, index+width, false);
			if(j < width-1)
				unfold(b_mine, i_mine, index+1, false);
			if(i > 0 && j > 0)
				unfold(b_mine, i_mine, index-width-1, false);
			if(i > 0 && j < width-1)
				unfold(b_mine, i_mine, index-width+1, false);
			if(i < height-1 && j > 0)
				unfold(b_mine, i_mine, index+width-1, false);
			if(i < height-1 && j < width-1)
				unfold(b_mine, i_mine, index+width+1, false);
		}
		else if(i_mine[index] == 12)
			;
		else {
			b_mine[index].setBackground(Color.white);
			b_mine[index].setLabel(i_mine[index] + "");
		}
	}
}
