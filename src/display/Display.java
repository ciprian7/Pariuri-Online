package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public abstract class Display {

	protected JFrame frame;
	protected JPanel panel;
	protected JPanel container;
	protected JFrame ticketWindow;
	protected String title = "";
	protected JPanel matchesPanel;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public final int width = 720;
	public final int height = 480;
	
	protected int x = (int)((screenSize.getWidth() - width)/2);
	protected int y = (int)((screenSize.getHeight() - height)/2);
	
	protected final int smallSize = (width/height)*15;
	protected final int mediumSize = (width/height)*20;
	protected final int largeSize = (width/height)*30;
	
	protected final Font smallFont = new Font("Arial",Font.BOLD,smallSize);
	protected final Font mediumFont = new Font("Arial",Font.BOLD,mediumSize);
	protected final Font largeFont = new Font("Arial",Font.BOLD,largeSize);
	

	/**
	 * Create the application.
	 */
	public Display() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, width, height);
		frame.setLocation(x, y);
		frame.setTitle(title);
		frame.setAlwaysOnTop(true);
		
		container = new JPanel();
		container.setBounds(0,0,width,height);
		container.setBackground(Color.green);
		container.setLayout(new BorderLayout());
		
		panel = new JPanel();
		panel.setBounds(0,0,width,height/8);
		panel.setBackground(Color.orange);
		panel.setPreferredSize(new Dimension(width,height/8));
		
		
		matchesPanel = new JPanel();
		matchesPanel.setBackground(Color.yellow);
		matchesPanel.setLayout(new BoxLayout(matchesPanel,BoxLayout.Y_AXIS));
		
		JScrollPane scroll = new JScrollPane(matchesPanel);
		//scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(0, height/8, width, height/4);
		scroll.setPreferredSize(new Dimension(width,height/4));
		
		container.add(panel,BorderLayout.PAGE_START);
		container.add(scroll, BorderLayout.CENTER);
		frame.add(container);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
	}

}
