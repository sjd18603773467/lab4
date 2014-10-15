package pop_menu_test;

public class PopupMenuTest {
	private TextArea ta = new TextArea(4, 30);
	private Frame f = new Frame("PopupMenu");
	PopupMenu pop = new PopupMenu();
	CheckboxMenuItem autoWrap = new CheckboxMenuItem("autowrap");
	MenuItem copyItem = new MenuItem("copy");
	MenuItem pasteItem = new MenuItem("paste");
	Menu format = new Menu("format");
	MenuItem commentItem = new MenuItem("comment", new MenuShortcut(KeyEvent.VK_A));
	MenuItem cancelItem = new MenuItem("cancel");
	public void init() {
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cmd = e.getActionCommand();
				ta.append("click " + cmd + "\n");
				if(cmd.equals("exit")) {
					System.exit(0);
				}
			}
		};
		commentItem.addActionListener(menuListener);
		pop.add(autoWrap);
		pop.addSeparator();
		pop.add(copyItem);
		pop.add(pasteItem);
		format.add(commentItem);
		format.add(cancelItem);
		pop.add(new MenuItem("-"));
		pop.add(format);
		final Panel p = new Panel();
		p.setPreferredSize(new Dimension(300, 160));
		p.add(pop);
		p.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()) {
					pop.show(p, e.getX(), e.getY());
				}
			}
		});
		f.add(p);
		f.add(ta, BorderLayout.NORTH);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.pack();
		f.setVisible(true);
	}
	public static void main(String[] args) {
		new PopupMenuTest().init();
	}
}


flsa;kdjfsldfjsldfj
