package App;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.LayoutFocusTraversalPolicy;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class MainWindow {

	private JFrame frame;
	Dimension frameDimension;
	private final JLayeredPane layeredPane = new JLayeredPane();
	private JPanel categoryPanel;
	private JScrollPane scrollPane;
	JPanel topPanel ;
	private JLabel logo;
	private JComboBox comboBox;
	private boolean isLogin = false ; 
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e ) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow() {
		initialize();
	}

	private void initialize() {
		frameDimension = (new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 4 /5 ) ,(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 4 /5 ) ));
		frame = new JFrame();
		frame.setTitle("E-Market");
		
		frame.setSize(frameDimension);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		ImageIcon imageIcon = new ImageIcon("logo.png");
		try {
			frame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/logo.png")));
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		frame.getContentPane().setLayout(null);
		initializeTopPanel();
		initializeButtomPanel();
	}
	
	private void initializeTopPanel() {
		topPanel = new JPanel();
		topPanel.setBackground(new Color(230,230,230));
		topPanel.setBounds(0,0,(int)(frameDimension.getWidth()), (int)(frameDimension.getHeight()*0.2));
		frame.getContentPane().add(topPanel);
		topPanel.setLayout(null);
		
		JLayeredPane logIn_layeredPanel = new JLayeredPane();
		logIn_layeredPanel.setBounds(873, 61, 218, 61);
		
		JPanel loggedIn_Panel = new JPanel();
		loggedIn_Panel.setBounds(0, 0, 0, 0);
		File file = new File("Image\\user_icon.jpg");
		JLabel user_icon = new JLabel();
		user_icon.setBounds(41, 24, 30, 30);
		try {
			user_icon.setIcon(new ImageIcon(scaleImage(file, user_icon.getWidth(), user_icon.getHeight())));
		}catch(NullPointerException e) {
//			e.printStackTrace();
			user_icon.setText("No Image");
		}
		loggedIn_Panel.setLayout(null);
		
		
		
		
		JLabel user_name = new JLabel("User123");
		user_name.setBounds(80, 30, 60, 20);
		
		user_name.setFont(new Font("Arial", Font.PLAIN, 16));

		
		loggedIn_Panel.add(user_name);
		loggedIn_Panel.add(user_icon);
		loggedIn_Panel.setVisible(true);
		logIn_layeredPanel.setLayout(null);
		logIn_layeredPanel.add(loggedIn_Panel);
		logIn_layeredPanel.repaint();
		logIn_layeredPanel.revalidate();
		topPanel.add(logIn_layeredPanel);
		
		logo = new JLabel("");
		logo.setBounds(54, 15, 218, 92);
		topPanel.add(logo);
		file = new File("Image\\logo.png") ;
		try {
			logo.setIcon(new ImageIcon(scaleImage(file, logo.getWidth(), logo.getHeight())));	
			
			
		}catch(NullPointerException e) {
			e.printStackTrace();
			logo.setFont(new Font("Arial",Font.PLAIN, 32));
			logo.setText("No Image");
		}
	
	}
	
	private void initializeButtomPanel() {
		layeredPane.setBackground(Color.WHITE);
		layeredPane.setBounds(0,(int)(frameDimension.getHeight() * 0.2),(int) frameDimension.getWidth(),(int)(frameDimension.getHeight()*0.75));
		frame.getContentPane().add(layeredPane);
		
		categoryPanel = new JPanel();
		categoryPanel.setSize(layeredPane.getSize());
		categoryPanel.setBackground(layeredPane.getBackground());
		layeredPane.add(categoryPanel);
		
		scrollPane = new JScrollPane(categoryPanel);
		categoryPanel.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Category", "Alphebetic"}));
		comboBox.setBounds((int)(frameDimension.getWidth() * 0.85) , (int)(frameDimension.getHeight() * 0.03), 100,20 );
		categoryPanel.add(comboBox);
		
		scrollPane.setSize(layeredPane.getSize());
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		layeredPane.add(scrollPane);
	}
	
	private Image scaleImage(File image,int width,int height) {
		if(width <= 0 || height <=0 ) {
			return null;
		}
		BufferedImage bufferedImage = null ;
		try {
			bufferedImage =ImageIO.read(image);
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		try {
			return bufferedImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		}catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
