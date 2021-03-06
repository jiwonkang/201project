import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;

import com.mysql.jdbc.Statement;

import sun.security.util.Password;





public class FirstPageGUI extends JFrame{
	private Dimension dim;
	private JTextField userName;
	private JPasswordField password;
	private JLabel logo;
	private JLabel newUser;
	private JButton createNewUser;
	private JButton guest;
	private JButton login;
	private JPanel bottomColor;
	private JPanel topColor;
	private JLabel incorrectInput;
	private Font newfont;
	JLabel wholeScreen;
	private JLayeredPane pane;
	JPanel mainPanel;
	private JPanel main = new JPanel();
	final static Color color = new Color(0x0AB2D8);
	final static Color white = new Color(0xf7f7f7);
	final static Color green = new Color(0xAD89B);
	final static Color lightGrey = new Color(0xB2B0B0);
	final static Color grey = new Color(0xE4E4E4);
	final static Color darkGrey = new Color(0x696969);
	final static Font font = new Font("Helvetica Neue", Font.PLAIN, 18);
	final static Font smallFont = new Font("Helvetica Neue", Font.PLAIN, 14);
	final static Font smallFontBold = new Font("Helvetica Neue", Font.BOLD, 14);
	final static Font fontBold = new Font("Helvetica Neue", Font.BOLD, 18);
	final static Font fontTitle = new Font("Helvetica Neue", Font.PLAIN, 24);
	final static Font smallerFont = new Font("Helvetica Neue", Font.PLAIN, 12);
	
	public FirstPageGUI()
	{
		super("Login Screen");
		initializeComponents();
		createGUI();
		makePretty();
		//loading = new JLabel();

	    //loading.setIcon(new ImageIcon("data/loading.gif"));
	    //add(loading, BorderLayout.CENTER);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    dim = Toolkit.getDefaultToolkit().getScreenSize();
		//loading.setPreferredSize(new Dimension(dim.width/3, dim.height));
	   // loading = new JLabel();
	    //loading.setIcon(new ImageIcon("data/loading.gif"));
		setBounds(0,0,dim.width/3, dim.height-80);
	   // startLoading();
		setResizable(false);
		setVisible(true);
		setEventHandlers();
	}
	
//	private void startLoading(){
//	    final Component glassPane = getGlassPane();
//	    final JPanel panel = new JPanel();
//	    panel.setLayout(new BorderLayout());
//	    panel.add(loading, BorderLayout.CENTER);
//	    setGlassPane(panel);
//	    panel.setVisible(true);
//	    panel.setOpaque(false);
//	}

	private void initializeComponents(){
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		//loading = new JLabel();
		//loading.setPreferredSize(new Dimension(dim.width/3, dim.height));
		//loading.setOpaque(false);
		//loading.setContentAreaFilled(false);
		//loading.setBorderPainted(false);
		//loading.setIcon(new ImageIcon("data/loading.gif"));
		userName = new JTextField("Username");
		password = new JPasswordField("Password");
		logo = new JLabel("201Tunes");
		newUser = new JLabel("Not Signed Up?");
		createNewUser = new JButton("Create Account");
		guest = new JButton("Login as Guest");
		login = new JButton("Login");
		bottomColor = new JPanel();
		bottomColor.setPreferredSize(new Dimension(dim.width/3, dim.height/20));
		bottomColor.setBackground(color);
		topColor = new JPanel();
		topColor.setPreferredSize(new Dimension(dim.width/3, 2*dim.height/20));
		topColor.setBackground(color);
		URL fontUrl;
		try {
			fontUrl = new URL("http://www.webpagepublicity.com/free-fonts/a/Anagram.ttf");
			newfont = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
			newfont = newfont.deriveFont(Font.PLAIN,36);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//logo.setFont(newfont);
	}
	
	private void createGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,dim.width/3, dim.height-80);
		setResizable(false);
		userName.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		password.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		logo.setForeground(white);
		topColor.add(logo);
		login.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		createNewUser.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		guest.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		newUser.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		newUser.setHorizontalAlignment(SwingConstants.CENTER);
		
		main.setLayout(new FlowLayout(FlowLayout.CENTER, dim.width, dim.height/40));
		main.setPreferredSize(new Dimension(dim.width/3, 13*dim.height/20));
		add(new JTextField());//we do this because we don't want focus on first jtexfield initially
		main.add(userName);
		main.add(password);
		password.setEchoChar((char)0);
		main.add(login);
		main.add(newUser);
		main.add(createNewUser);
		main.add(guest);
		mainPanel = new JPanel();
		mainPanel.add(main, BorderLayout.CENTER);
		incorrectInput = new JLabel("");
		incorrectInput.setHorizontalAlignment(SwingConstants.CENTER);
		incorrectInput.setForeground(Color.RED);
		mainPanel.setBackground(FirstPageGUI.white);
		mainPanel.add(main);
		mainPanel.add(incorrectInput);
		incorrectInput.setPreferredSize(new Dimension(dim.width, dim.height/20));
		mainPanel.setPreferredSize(new Dimension(dim.width/3, 17*dim.height/20));
		add(topColor, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
		add(bottomColor, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
//	class MyThread extends Thread
//	{
//		public void run()
//		{
//			startLoading();
//		}
//	}
	
	private void makePretty(){
		logo.setFont(newfont);
		
		main.setBackground(white);
		password.setBorder(new RoundedBorder());
		password.setFont(font);
		userName.setBorder(new RoundedBorder());
		userName.setBackground(grey);
		userName.setFont(font);
		password.setBackground(grey);
		login.setBorder(new RoundedBorder());
		login.setBackground(green);
		login.setFont(font);
		createNewUser.setBorder(new RoundedBorder());
		createNewUser.setBackground(color);
		createNewUser.setFont(font);
		guest.setBorder(new RoundedBorder());
		guest.setOpaque(true);
		guest.setBackground(darkGrey);
		guest.setForeground(white);
		guest.setFont(font);
		newUser.setFont(font);
		createNewUser.setForeground(white);
		login.setOpaque(true);
		createNewUser.setOpaque(true);
		login.setForeground(white);
		password.setForeground(lightGrey);
		userName.setForeground(lightGrey);
		
	}
	
	private void setEventHandlers(){
		createNewUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new CreateUserGUI();
				dispose();
			}
		});
		guest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new GuestGUI();
			}
		});
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//startLoading();
				loginAction();
			}
		});
		
		userName.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if(userName.getText().equals("Username"))
				{
					userName.setText("");
					userName.setForeground(FirstPageGUI.darkGrey);
				}
			}
			@Override
			public void focusLost(FocusEvent e)
			{
				if(userName.getText().equals(""))
				{
					userName.setText("Username");
					userName.setForeground(FirstPageGUI.lightGrey);
				}
			}
		});
		
		userName.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
			
			@Override
			public void keyTyped(KeyEvent e)
			{
				if(e.getKeyChar() == KeyEvent.VK_ENTER)
				{
					
					//FirstPageGUI.this.removeAll();
//					bottomColor.setBackground(FirstPageGUI.grey);
//					topColor.setBackground(FirstPageGUI.grey);
					//setBackground(FirstPageGUI.grey);
//					login.setBackground(FirstPageGUI.grey);
//					newUser.setBackground(FirstPageGUI.grey);
//					guest.setBackground(FirstPageGUI.grey);
				//FirstPageGUI.this.add(loading);
				//topColor.setBackground(FirstPageGUI.grey);
				//FirstPageGUI.this.revalidate();
				//FirstPageGUI.this.repaint();
					loginAction();	
                }       
			}
		});
		
		password.addFocusListener(new FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				if(password.getText().equals("Password"))
				{
					password.setEchoChar(('*'));
					password.setText("");
				}
				password.setForeground(FirstPageGUI.darkGrey);
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if(password.getText().equals(""))
				{
					password.setText("Password");
					password.setEchoChar((char)0);
				}
				password.setForeground(FirstPageGUI.lightGrey);
			}
		});
		
		password.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
			
			@Override
			public void keyTyped(KeyEvent e)
			{
				if(e.getKeyChar() == KeyEvent.VK_ENTER)
				{
					//new MyThread().start();
					loginAction();
                }       
			}
		});
	}
	
	class MyThread extends Thread{
		
		private int id;
		public MyThread(int id)
		{
			this.id = id;
		}
		public void run(){
			new LoggedInDriverGUI(id);
			dispose();
		}
	}
	//this is the function that logs us in
	public void loginAction() {
		//FirstPageGUI.this.removeAll();
		//startLoading();
		String theUserName=userName.getText();
		String thePassword=PasswordHash.hash(password.getText());//returning the password hashed
		try
		{
			Statement stat = (Statement) ConnectionClass.conn.createStatement();
			String sql = "Select * from user_table Where username='" + theUserName + "' and password='"+thePassword+"'";
			ResultSet rs = stat.executeQuery(sql);
			if (rs.next() && theUserName.equals(rs.getString("username")) && thePassword.equals(rs.getString("password")))
            {
				//JPanel p = new LoggedInDriverGUI(rs.getInt("iduser_table"));
				//System.out.println(rs.getInt("iduser_table"));
				//conn.close();
				//setVisible(false);
				remove(topColor);
				remove(mainPanel);
				remove(bottomColor);
				revalidate();
				repaint();
				wholeScreen = new JLabel();
				wholeScreen.setPreferredSize(new Dimension(dim.width/3, dim.height-100));
				ImageIcon load = new ImageIcon("data/LOADSLOW.gif");
				//Image img2 = load.getImage().getScaledInstance(dim.width/3, dim.height-100, Image.SCALE_SMOOTH);
				//profilePic = new ImageIcon(img2);
				wholeScreen.setIcon(load);
				add(wholeScreen);
				revalidate();
				repaint();
				int id = rs.getInt("iduser_table");
				new MyThread(id).start();
				//dispose();
				stat.close();
            }
            else
            {
            	System.out.println("incorrect username password combo");
            	incorrectInput.setText("incorrect username or password");
            }
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}				
	}
	
	public void loginAction(ActionEvent e) {
		removeAll();
		String theUserName=userName.getText();
		String thePassword=PasswordHash.hash(password.getText());//returning the password hashed
		try
		{
			Statement stat = (Statement) ConnectionClass.conn.createStatement();
			String sql = "Select * from user_table Where username='" + theUserName + "' and password='"+thePassword+"'";
			ResultSet rs = stat.executeQuery(sql);
			if (rs.next() && theUserName.equals(rs.getString("username")) && thePassword.equals(rs.getString("password")))
            {
				//JPanel p = new LoggedInDriverGUI(rs.getInt("iduser_table"));
				stat.close();
				removeAll();
				//conn.close();
				//setVisible(false);
//				wholeScreen = new JLabel();
//				wholeScreen.setPreferredSize(new Dimension(dim.width/3, dim.height-100));
//				ImageIcon load = new ImageIcon("data/loginLoader.gif");
//				//Image img2 = load.getImage().getScaledInstance(dim.width/3, dim.height-100, Image.SCALE_SMOOTH);
//				//profilePic = new ImageIcon(img2);
//				wholeScreen.setIcon(load);
//				add(wholeScreen);
				revalidate();
				repaint();
				//dispose();
            }
            else
            {
            	System.out.println("incorrect username password combo");
            	incorrectInput.setText("incorrect username or password");
            }
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}				
	}
}