import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mysql.jdbc.PreparedStatement;


public class ProfileGUI extends JPanel{
	private Dimension dim;
	private File pictureFile;
	private ImageIcon profilePic;
	private JButton saveButton;
	private JButton cancelButton;
	private JLabel name;
	private JLabel email;
	private JTextArea bio;
	private Set<JButton> followersButtons;
	private Set<JButton> followingButtons;
	private Set<JButton> favoritesButtons;
	private JScrollPane jspFollowers;
	private JScrollPane jspFollowing;
	private JScrollPane jspFavorites;
	private JPanel jpFollowers;
	private JPanel jpFollowing;
	private JPanel jpFavorites;
	private JPanel buttonP;
	private JButton edit;
	private JButton unFollow;
	private JButton follow;
	private JPanel picture;
	private JPanel namePanel;
	private JPanel emailPanel;
	private JTextField editFirstName;
	private JTextField editLastName;
	private JTextField editEmail;
	private JFileChooser jfl;
	private JLabel picturePic;
	private JButton pictureButton;
	private String previousBioText;
	//private JButton backButton;
	//private ActionListener forBackButton;
	private String key;
	private Integer userId;
	//private Connection conn;
	private LoggedInDriverGUI mainPage;
	//socket for server
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String username;
//	public ProfileGUI(Dimension d, String key, int userID)
//	{
//		dim = d;
//	//	this.mainPage = mainPage;
//		this.key = key;
//		this.userId=userID;
//		//this.conn=conn;
//		//backButton = new JButton("Back");
//		//this.forBackButton = forBackButton;
//		//backButton.addActionListener(forBackButton);
//		//profilePic = new ImageIcon("data/MomAndMoose.jpg");
//		ImageIcon newIcon2 = new ImageIcon("data/MomAndMoose.jpg");
//		Image img2 = newIcon2.getImage().getScaledInstance(dim.width/2, dim.height/4, Image.SCALE_SMOOTH);
//		profilePic = new ImageIcon(img2);
//		this.setPreferredSize(dim);
//		initializeComponents();
//		setEventHandlers();
//		setVisible(true);
//	}
	
	public ProfileGUI(LoggedInDriverGUI mainPage, Dimension d, String key, int userID)
	{
		dim = d;
		this.mainPage = mainPage;
		this.key = key;
		this.userId=userID;
		//backButton = new JButton("Back");
		//this.forBackButton = forBackButton;
		//backButton.addActionListener(forBackButton);
		//profilePic = new ImageIcon("data/MomAndMoose.jpg");
		

		this.setPreferredSize(dim);
		initializeComponents();
		setEventHandlers();
		setVisible(true);
		
		//Server Side Profile Picture Upload
		
	}
	
	private void initializeComponents()
	{
		name = new JLabel("Name");
		email = new JLabel("Email");
		bio = new JTextArea("My Bio");
		picturePic = new JLabel("");

		//picturePic.setBorder(new RoundedBorder());
		picturePic.setPreferredSize(new Dimension(dim.width/2, dim.height/4));
		//picturePic.setBorder(new RoundedBorder());
		picturePic.setIcon(profilePic);
		//buttons depending on user
		edit = new JButton("Edit Profile");
		unFollow = new JButton("Unfollow");
		follow = new JButton("Follow");
		saveButton = new JButton("Save");
		cancelButton = new JButton("Cancel");
		pictureButton = new JButton("");
		pictureButton.setPreferredSize(new Dimension(dim.width/2, dim.height/4));
		//pictureButton.setBorder(new RoundedBorder());
		editFirstName = new JTextField();
		editLastName = new JTextField();
		editEmail = new JTextField();
		jfl = new JFileChooser();
		//jfl = new JFileChooser();
	    jfl.setCurrentDirectory(new File("."));
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	            "jpg", "jpeg");
	     	jfl.setFileFilter(filter);
	    
	    editEmail.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	    editLastName.setPreferredSize(new Dimension(dim.width/5, dim.height/16));
	    editFirstName.setPreferredSize(new Dimension(dim.width/5, dim.height/16));
	    cancelButton.setPreferredSize(new Dimension(dim.width/5, dim.height/18));
	   // editFirstName.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	   // edit.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	    email.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	    name.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	    follow.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	    edit.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	    saveButton.setPreferredSize(new Dimension(dim.width/5, dim.height/18));
	    unFollow.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	    email.setFont(FirstPageGUI.smallFont);
	    name.setFont(FirstPageGUI.font);
	    edit.setFont(FirstPageGUI.smallFont);
	    email.setForeground(FirstPageGUI.darkGrey);
	    name.setForeground(FirstPageGUI.darkGrey);
	    name.setHorizontalAlignment(SwingConstants.CENTER);
		email.setHorizontalAlignment(SwingConstants.CENTER);
		followersButtons = new HashSet<JButton>();
		followingButtons = new HashSet<JButton>();
		favoritesButtons = new HashSet<JButton>();
		jpFollowers = new JPanel();
		jpFollowing = new JPanel();
		jpFavorites = new JPanel();
		jpFollowing.setPreferredSize(new Dimension(dim.width/3, dim.height/2));
		jpFollowers.setPreferredSize(new Dimension(dim.width/3, dim.height/2));
		jpFavorites.setPreferredSize(new Dimension(dim.width/3, dim.height/2));
		jpFollowing.setBackground(FirstPageGUI.white);
		jpFollowers.setBackground(FirstPageGUI.white);
		jpFavorites.setBackground(FirstPageGUI.white);
		jpFollowers.setBackground(FirstPageGUI.white);
		jpFollowing.setBackground(FirstPageGUI.white);
		jpFavorites.setBackground(FirstPageGUI.white);
		jspFollowing = new JScrollPane(jpFollowing);
		jspFollowers = new JScrollPane(jpFollowers);
		jspFavorites = new JScrollPane(jpFavorites);
		//jpFollowers.setLayout()
		//jpFollowing.setLayout(new BoxLayout(jpFollowing, BoxLayout.Y_AXIS));
		//jpFavorites.setLayout(new BoxLayout(jpFavorites, BoxLayout.Y_AXIS));
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(FirstPageGUI.color);
		bottomPanel.setLayout(new GridLayout(1, 3));
		JPanel buttonLabelsPanel = new JPanel();
		buttonLabelsPanel.setBackground(FirstPageGUI.white);
		buttonLabelsPanel.setPreferredSize(new Dimension(dim.width, dim.height/14));
		buttonLabelsPanel.setLayout(new GridLayout(1, 3));
		JLabel followers = new JLabel("Followers");
		JLabel following = new JLabel("Following");
		JLabel favorites = new JLabel("Favorites");
		followers.setFont(FirstPageGUI.fontTitle);
		following.setFont(FirstPageGUI.fontTitle);
		favorites.setFont(FirstPageGUI.fontTitle);
		followers.setForeground(FirstPageGUI.darkGrey);
		following.setForeground(FirstPageGUI.darkGrey);
		favorites.setForeground(FirstPageGUI.darkGrey);
		followers.setHorizontalAlignment(SwingConstants.CENTER);
		following.setHorizontalAlignment(SwingConstants.CENTER);
		favorites.setHorizontalAlignment(SwingConstants.CENTER);
		buttonLabelsPanel.add(followers);
		buttonLabelsPanel.add(following);
		buttonLabelsPanel.add(favorites);
		buttonLabelsPanel.setBackground(FirstPageGUI.white);
		bottomPanel.setPreferredSize(new Dimension(dim.width, 7*dim.height/16));
		bottomPanel.add(jspFollowers);
		bottomPanel.add(jspFollowing);
		bottomPanel.add(jspFavorites);
		JPanel topPanel = new JPanel();
		JPanel middlePanel = new JPanel();
		middlePanel.add(bio);
		bio.setPreferredSize(new Dimension(dim.width, dim.height/5));
		bio.setEditable(false);
		bio.setBackground(FirstPageGUI.color);
		bio.setForeground(FirstPageGUI.white);
		edit.setBorder(new RoundedBorder());
		edit.setBackground(FirstPageGUI.green);
		follow.setBorder(new RoundedBorder());
		unFollow.setBorder(new RoundedBorder());
		follow.setForeground(FirstPageGUI.white);
		unFollow.setForeground(FirstPageGUI.white);
		follow.setBackground(FirstPageGUI.green);
		unFollow.setBackground(FirstPageGUI.green);
		edit.setForeground(FirstPageGUI.white);
		saveButton.setBorder(new RoundedBorder());
		cancelButton.setBorder(new RoundedBorder());
		saveButton.setBackground(FirstPageGUI.green);
		cancelButton.setBackground(FirstPageGUI.color);
		saveButton.setForeground(FirstPageGUI.white);
		cancelButton.setForeground(FirstPageGUI.white);
		editEmail.setBorder(new RoundedBorder());
		editFirstName.setBorder(new RoundedBorder());
		editLastName.setBorder(new RoundedBorder());
		editEmail.setBackground(FirstPageGUI.grey);
		editEmail.setOpaque(true);
		editFirstName.setBackground(FirstPageGUI.grey);
		editFirstName.setOpaque(true);
		editLastName.setBackground(FirstPageGUI.grey);
		editLastName.setOpaque(true);
		cancelButton.setOpaque(true);
		saveButton.setOpaque(true);
		edit.setOpaque(true);
		unFollow.setOpaque(true);
		follow.setOpaque(true);
		editEmail.setText("email");
		
		editEmail.setFont(FirstPageGUI.smallFont);
		editFirstName.setText("first name");
		editFirstName.setFont(FirstPageGUI.smallFont);
		editLastName.setText("last name");
		editLastName.setFont(FirstPageGUI.smallFont);
		editEmail.setForeground(FirstPageGUI.lightGrey);
		editLastName.setForeground(FirstPageGUI.lightGrey);
		editFirstName.setForeground(FirstPageGUI.lightGrey);
		topPanel.setPreferredSize(new Dimension(dim.width, 3*dim.height/10));
		JPanel info = new JPanel();
		info.setBackground(FirstPageGUI.white);
		info.setPreferredSize(new Dimension(dim.width/2, dim.height/4));
		buttonP = new JPanel();
		buttonP.setBackground(FirstPageGUI.white);
		buttonP.setPreferredSize(new Dimension(dim.width, dim.height/14));
		emailPanel = new JPanel();
		emailPanel.setBackground(FirstPageGUI.white);
		emailPanel.setPreferredSize(new Dimension(dim.width, dim.height/14));
		namePanel = new JPanel();
		namePanel.setBackground(FirstPageGUI.white);
		namePanel.setPreferredSize(new Dimension(dim.width, dim.height/14));
		namePanel.add(name);
		setBackground(FirstPageGUI.white);
		emailPanel.add(email);
		//JPanel allInfoP = new JPanel();
		info.setPreferredSize(new Dimension(new Dimension(dim.width/2, dim.height/4)));
		//picture.add()
		if (key.equals("not friends"))
		{
			buttonP.add(follow);
			//buttonP.add(backButton);
		}
		else if (key.equals("friends"))
		{
			buttonP.add(unFollow);
			//buttonP.add(backButton);
		}
		else if (key.equals("current user"))
		{
			buttonP.add(edit);
			
		}
		info.add(namePanel);
		info.add(emailPanel);
		info.add(buttonP);
		picture = new JPanel();
		bio.setFont(FirstPageGUI.font);
		bio.setWrapStyleWord(true);
		bio.setLineWrap(true);
		picture.setBackground(FirstPageGUI.white);
		picture.setPreferredSize(new Dimension(dim.width/2, dim.height/4));
		picture.add(picturePic);
		topPanel.setLayout(new GridLayout(1, 2));
		topPanel.add(picture);
		//picture.setBorder(border);
		topPanel.add(info);
		topPanel.setBackground(FirstPageGUI.white);
		add(topPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		middlePanel.setBackground(FirstPageGUI.white);
		setBackground(FirstPageGUI.white);
		JPanel mid = new JPanel();
		mid.setBackground(FirstPageGUI.white);
		mid.setPreferredSize(new Dimension(dim.width, dim.height/2));
		mid.add(buttonLabelsPanel, BorderLayout.NORTH);
		mid.add(bottomPanel, BorderLayout.CENTER);
		add(mid, BorderLayout.SOUTH);//, BorderLayout.SOUTH);
		//add(new JLabel("OHAI!!!!!"));
		//setBackground(FirstPageGUI.color);
		repaint();
		setVisible(true);
		
	}
	
	public void make()
	{
		try{
			s = new Socket("104.236.176.180",5000);
			oos = new ObjectOutputStream(s.getOutputStream());
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
	        String sql="select profile_picture from user_table where iduser_table=?";
	        PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement(sql);
	        ps.setInt(1,userId);
	        //ps.setInt(2,userId);
	        ResultSet RS = ps.executeQuery();
	        int columns = RS.getMetaData().getColumnCount();
	        String finalPathToPicture = null;
	        while (RS.next())
	        {
	    
	        	for (int i = 1; i <= columns; i++)
	        	{
	        		//System.out.println(RS.getString(i) + "GOOZ");
	        		finalPathToPicture = RS.getString(i);
	        	}
	        }
	        if(!finalPathToPicture.equals(null)){
	        	URL imageurl = new URL(finalPathToPicture);
	            BufferedImage img = ImageIO.read(imageurl);
	    		ImageIcon newIcon2 = new ImageIcon(img);
	    		System.out.println(finalPathToPicture+"GOOOOOOOOZE");
	    		Image img2 = newIcon2.getImage().getScaledInstance(dim.width/2, dim.height/4, Image.SCALE_SMOOTH);
	    		profilePic = new ImageIcon(img2);
	        }
	        
	        else{
	    		ImageIcon newIcon2 = new ImageIcon("data/headphone_default.jpg");
	    		Image img2 = newIcon2.getImage().getScaledInstance(dim.width/2, dim.height/4, Image.SCALE_SMOOTH);
	    		profilePic = new ImageIcon(img2);
	        }
	        

		}
		catch(Exception e4){
			//e4.printStackTrace();
    		ImageIcon newIcon2 = new ImageIcon("data/headphone_default.jpg");
    		Image img2 = newIcon2.getImage().getScaledInstance(dim.width/2, dim.height/4, Image.SCALE_SMOOTH);
    		profilePic = new ImageIcon(img2);

		}
		
		pictureButton.setIcon(profilePic);
		picturePic.setIcon(profilePic);
		try
		{
			Statement stat = (Statement) ConnectionClass.conn.createStatement();
			String sql = "Select * from user_table Where iduser_table='" + userId+"'";
			ResultSet rs = stat.executeQuery(sql);
			if (rs.next())
            {
				name.setText(rs.getString("first_name")+" "+rs.getString("last_name"));
				email.setText(rs.getString("email"));
				System.out.println(rs.getString("bio"));
				bio.setText(rs.getString("bio"));
				username = rs.getString("username");
				rs.close();
				stat.close();
            }
            else
            {
            	System.out.println("something wrong");
            }
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		populate();
		
	}
	
	private void populate()
	{
		try
		{
			//ConnectionClass.conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
			
			Statement st = ConnectionClass.conn.createStatement();
			//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
			String queryCheck = "SELECT user_being_followed FROM friend_relationship WHERE user = " + Integer.toString(userId);
			ResultSet rs = st.executeQuery(queryCheck);
			int columns = rs.getMetaData().getColumnCount();
			Vector<JButton> buttonVector = new Vector<JButton> ();
			Vector<Integer> userIDVector = new Vector<Integer> ();
			while (rs.next())
			{
				//System.out.println("CHECK");
				JButton temp = new JButton(Integer.toString(rs.getInt(1)));
				temp.addActionListener(new ActionListenerProfile(rs.getInt(1), "friends"));
				userIDVector.add(rs.getInt(1));
				temp.setBackground(FirstPageGUI.green);
				temp.setForeground(FirstPageGUI.white);
				temp.setFont(FirstPageGUI.smallFont);
				temp.setBorder(new RoundedBorder());
				temp.setPreferredSize(new Dimension(dim.width/4, dim.height/20));
				temp.setOpaque(true);
				buttonVector.add(temp);
				temp.addActionListener(new ActionListenerProfile(rs.getInt(1), "friends"));
				//jpFollowing.add(temp);
			}
			//System.out.println(userIDVector.size());
			for (int i = 0; i < userIDVector.size(); i++)
			{
				//System.out.println("USer " +userIDVector.get(i));
				queryCheck = "SELECT first_name, last_name FROM user_table WHERE iduser_table = " + userIDVector.get(i);
				rs = st.executeQuery(queryCheck);
				columns = rs.getMetaData().getColumnCount();
				int index = 0;
				while (rs.next())
				{
					String name = "";
					JButton temp = buttonVector.get(i);
					for (int j = 1; j <= columns; j++)
					{
						name = name + rs.getString(j) + " ";
						//System.out.println(rs.getString(i));
					}
					System.out.println("Name " + name);
					temp.setText(name);
					jpFollowing.add(temp);
				}

			}
			st.close();
			
		}
		catch (Exception e) {}
		
		try
		{
			//ConnectionClass.conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
			
			Statement st = ConnectionClass.conn.createStatement();
			//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
			String queryCheck = "SELECT user FROM friend_relationship WHERE user_being_followed = " + Integer.toString(LoggedInDriverGUI.userID);
			ResultSet rs = st.executeQuery(queryCheck);
			int columns = rs.getMetaData().getColumnCount();

			System.out.println("after query");
			Vector<JButton> buttonVector = new Vector<JButton> ();
			Vector<Integer> userIDVector = new Vector<Integer> ();

			while (rs.next())
			{
				for (int i = 1; i <= columns; i++)
				{
					JButton temp = new JButton(Integer.toString(rs.getInt(1)));
					//temp.addActionListener(new ActionListenerProfile(rs.getInt(1), "friends"));
					userIDVector.add(rs.getInt(1));
					temp.setBackground(FirstPageGUI.green);
					temp.setForeground(FirstPageGUI.white);
					temp.setFont(FirstPageGUI.smallFont);
					temp.setBorder(new RoundedBorder());
					temp.setPreferredSize(new Dimension(dim.width/4, dim.height/20));
					temp.setOpaque(true);
					
					buttonVector.add(temp);
				}
			}
			for (int i = 0; i < userIDVector.size(); i++)
			{
				//System.out.println("USer " +userIDVector.get(i));
				queryCheck = "SELECT first_name, last_name FROM user_table WHERE iduser_table = " + Integer.toString(userIDVector.get(i));
				rs = st.executeQuery(queryCheck);
				columns = rs.getMetaData().getColumnCount();
				int index = 0;
				while (rs.next())
				{
					String name = "";
					JButton temp = buttonVector.get(i);
					for (int j = 1; j <= columns; j++)
					{
						name = name + rs.getString(j) + " ";
						//System.out.println(rs.getString(i));
					}
					System.out.println("Name " + name);
					temp.setText(name);
					jpFollowers.add(temp);
				}
			}
			for ( int i = 0; i <userIDVector.size(); i++)
			{
				queryCheck = "SELECT user FROM friend_relationship WHERE user_being_followed = " + Integer.toString(userIDVector.get(i));
				rs = st.executeQuery(queryCheck);
				columns = rs.getMetaData().getColumnCount();
				JButton button = buttonVector.get(i);
				if (rs.next())
				{
					System.out.println("friends");
					//ProfileGUI newProfile;
					//newProfile = new ProfileGUI(mainPage, dim, "friends", userIDVector.get(i));
					button.addActionListener(new ActionListenerProfile(userIDVector.get(i), "friends"));
				}
				else
				{
					System.out.println("not friends");
				//	ProfileGUI newProfile;
					//newProfile = new ProfileGUI(mainPage, dim, "not friends", userIDVector.get(i));
					button.addActionListener(new ActionListenerProfile(userIDVector.get(i), "not friends"));
				}
			}
			st.close();
			
			
		}
		catch (Exception e) {}
		for (int i = 0; i <LoggedInDriverGUI.numFavoriteSongs; i++)
		{
			MusicModel MusicObject = LoggedInDriverGUI.favoriteSongNames.get(i);
			JButton temp2 = new JButton(MusicObject.getSongName());
			temp2.addActionListener(new ActionListenerPlayer(MusicObject));
			favoritesButtons.add(temp2);
		}
		
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			//conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
			Statement st = ConnectionClass.conn.createStatement();
			//String queryCheck = "";
			//boolean check_found = false;

			String queryCheck = "SELECT song_id FROM favorite_songs WHERE user_id = " + userId;
			ResultSet rs = st.executeQuery(queryCheck);
		//	columns = rs.getMetaData().getColumnCount();
			Vector<Integer> songIds = new Vector<Integer>();
			while(rs.next())
			{
				songIds.add(rs.getInt(1));
			}
			for (int i = 0; i < songIds.size(); i++)
			{
				queryCheck = "SELECT song_name from music_table where idmusic_table = " + songIds.get(i);
				rs = st.executeQuery(queryCheck);
				//columns = rs.getMetaData().getColumnCount();
				while(rs.next())
				{
					JButton temp = new JButton("");
					temp.setText(rs.getString(1));
					MusicModel model = LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().get(rs.getString(1));
					temp.addActionListener(new ActionListenerPlayer(model));
					temp.setBackground(FirstPageGUI.green);
					temp.setForeground(FirstPageGUI.white);
					temp.setFont(FirstPageGUI.smallFont);
					temp.setBorder(new RoundedBorder());
					temp.setPreferredSize(new Dimension(dim.width/4, dim.height/20));
					temp.setOpaque(true);
					jpFavorites.add(temp);
				}
			}
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		
	}
	
	private void setEventHandlers()
	{
		edit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				bio.setEditable(true);
				namePanel.remove(name);
				previousBioText = bio.getText();
				name.setVisible(false);
				emailPanel.remove(email);
				email.setVisible(false);
				buttonP.remove(edit);
				edit.setVisible(false);
				buttonP.add(saveButton);
				saveButton.setVisible(true);
				buttonP.add(cancelButton);
				cancelButton.setVisible(true);
				emailPanel.add(editEmail);
				editEmail.setVisible(true);
				namePanel.add(editFirstName);
				editFirstName.setVisible(true);
				namePanel.add(editLastName);
				editLastName.setVisible(true);
				picture.remove(picturePic);
				picture.add(pictureButton);
				pictureButton.setVisible(true);
				repaint();
			}
			
		});
		pictureButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int temp1 = jfl.showOpenDialog(pictureButton);
				if (temp1 == JFileChooser.APPROVE_OPTION)
				{
					//get the file
					pictureFile = jfl.getSelectedFile();
					try{
						BufferedImage buffImage = ImageIO.read(pictureFile);
						
						UserPicture thePictureObject = new UserPicture(buffImage, userId);
						oos.writeObject(thePictureObject);
						oos.flush();
						ois = new ObjectInputStream(s.getInputStream());
						String AnswerFromServer = (String)ois.readObject();
						System.out.println(AnswerFromServer);
						System.out.println("SENT THE FILE");
						if(AnswerFromServer.equals("Yes")){
		                    String sql="update user_table set profile_picture=? where iduser_table=?";
		                    PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement(sql);
		                    ps.setString(1,"http://104.236.176.180/CS201/profile_pictures/"+userId+".jpg");
		                    ps.setInt(2,userId);
		                    ps.execute();
						}
					}catch(Exception e1){
						e1.printStackTrace();
					}
					
					
					
					
					ImageIcon newIcon2 = new ImageIcon(pictureFile.getPath());
					Image img2 = newIcon2.getImage().getScaledInstance(dim.width/2, dim.height/4, Image.SCALE_SMOOTH);
					pictureButton.setIcon(new ImageIcon(img2));
					//profilePic = new ImageIcon(img2);
					
				}
			}
			
		});
		
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				bio.setEditable(false);
				namePanel.remove(editFirstName);
				editFirstName.setVisible(false);
				namePanel.remove(editLastName);
				editLastName.setVisible(false);
				emailPanel.remove(editEmail);
				editEmail.setVisible(false);
				buttonP.remove(saveButton);
				saveButton.setVisible(false);
				buttonP.remove(cancelButton);
				cancelButton.setVisible(false);
				bio.setText(previousBioText);
				buttonP.add(edit);
				edit.setVisible(true);
				emailPanel.add(email);
				email.setVisible(true);
				namePanel.add(name);
				name.setVisible(true);
				pictureFile = null;
				picture.remove(pictureButton);
				picture.add(picturePic);
				pictureButton.setVisible(false);
				repaint();
			}

		});
		
		saveButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean fn=true;
				boolean ln=true;
				boolean em=true;
				
				if(editFirstName.getText().length()>11 || editFirstName.getText().length()<5) fn=false;
				if(editLastName.getText().length()>11 || editLastName.getText().length()<5) ln=false;
				if(editEmail.getText().length()>30 || editEmail.getText().length()<5) em=false;
				
				if(fn && ln && em)
				{
					String newFirstName = editFirstName.getText();
					String newLastName = editLastName.getText();
					String newEmail = editEmail.getText();
					
					//System.out.println("the id is"+userId);
					//System.out.println("the first name is"+newFirstName);
					//System.out.println("the last name is"+newLastName);
					//System.out.println("the email is"+newEmail);
					
					try
					{
						PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("UPDATE user_table SET first_name= ?, last_name=?, email=?, bio=?" + "WHERE iduser_table = ?");
						ps.setString(1, newFirstName);
						ps.setString(2, newLastName);
						ps.setString(3, newEmail);
						ps.setString(4, bio.getText());
						ps.setInt(5, userId);
						ps.execute();
						ps.close();
					} catch (SQLException e1)
					{
						e1.printStackTrace();
					}
					
					editFirstName.setText("first name");
					editLastName.setText("last name");
					editEmail.setText("email");
					editFirstName.setForeground(FirstPageGUI.lightGrey);
					editLastName.setForeground(FirstPageGUI.lightGrey);
					editEmail.setForeground(FirstPageGUI.lightGrey);
					if (pictureFile != null)
					{
						ImageIcon newIcon2 = new ImageIcon(pictureFile.getPath());
						Image img2 = newIcon2.getImage().getScaledInstance(dim.width/2, dim.height/4, Image.SCALE_SMOOTH);
						picturePic.setIcon(new ImageIcon(img2));
						profilePic = new ImageIcon(img2);
					}
					name.setText(newFirstName + " "+newLastName);
					email.setText(newEmail);
					bio.setEditable(false);
					namePanel.remove(editFirstName);
					editFirstName.setVisible(false);
					namePanel.remove(editLastName);
					editLastName.setVisible(false);
					emailPanel.remove(editEmail);
					editEmail.setVisible(false);
					buttonP.remove(saveButton);
					saveButton.setVisible(false);
					buttonP.remove(cancelButton);
					cancelButton.setVisible(false);
					buttonP.add(edit);
					edit.setVisible(true);
					emailPanel.add(email);
					email.setVisible(true);
					namePanel.add(name);
					name.setVisible(true);
					picture.remove(pictureButton);
					picture.add(picturePic);
					pictureButton.setVisible(false);
					repaint();
				}
				else System.out.println("incorrect number of characters!");
				
			}
			
		});
		
		editFirstName.addFocusListener(new FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				if(editFirstName.getText().equals("first name"))
				{
					//editFirstName.setEchoChar(('*'));
					editFirstName.setText("");
				}
				editFirstName.setForeground(FirstPageGUI.darkGrey);
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if(editFirstName.getText().equals(""))
				{
					editFirstName.setText("first name");
					//editFirstName.setEchoChar((char)0);
					editFirstName.setForeground(FirstPageGUI.lightGrey);
				}
				
			}
		});
		editLastName.addFocusListener(new FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				if(editLastName.getText().equals("last name"))
				{
					//editFirstName.setEchoChar(('*'));
					editLastName.setText("");
				}
				editLastName.setForeground(FirstPageGUI.darkGrey);
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if(editLastName.getText().equals(""))
				{
					editLastName.setText("last name");
					//editFirstName.setEchoChar((char)0);
					editLastName.setForeground(FirstPageGUI.lightGrey);
				}
				
			}
		});
		editEmail.addFocusListener(new FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				if(editEmail.getText().equals("email"))
				{
					//editFirstName.setEchoChar(('*'));
					editEmail.setText("");
				}
				editEmail.setForeground(FirstPageGUI.darkGrey);
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if(editEmail.getText().equals(""))
				{
					editEmail.setText("email");
					//editFirstName.setEchoChar((char)0);
					editEmail.setForeground(FirstPageGUI.lightGrey);
				}
				
			}
		});
		//follow user
		follow.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO friend_relationship (user, user_being_followed)" + "VALUES (?, ?)");
					ps.setInt(1, LoggedInDriverGUI.userID);
					ps.setInt(2, userId);
					System.out.println(userId);
					ps.executeUpdate();
					ps.close();
					buttonP.remove(follow);
					buttonP.add(unFollow);
					buttonP.revalidate();
					buttonP.repaint();
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		unFollow.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("DELETE FROM friend_relationship WHERE " + "user = ?" + " and " + "user_being_followed = ?");
					ps.setInt(1, LoggedInDriverGUI.userID);
					ps.setInt(2, userId);
					ps.executeUpdate();
					ps.close();
					buttonP.remove(unFollow);
					buttonP.add(follow);
					buttonP.revalidate();
					buttonP.repaint();
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
	}
	
	class ActionListenerProfile implements ActionListener{
		private int id;
		private String relation;
		public ActionListenerProfile(int id, String relationship)
		{
			relation = relationship;
			this.id = id;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			//System.out.println("in actionlistener");
			String sqlQuery = "SELECT COUNT(1) FROM friend_relationship WHERE EXISTS user = "+ userId+" AND user_being_followed = "+id+")";
			ProfileGUI newProfile;
			newProfile = new ProfileGUI(mainPage, dim, relation, id);
			newProfile.make();
			mainPage.addCurrent(newProfile);
		}
		
	}
	
//	class ActionListenerProfileComplicated implements ActionListener{
//		private int id;
//		private String relation;
//		public ActionListenerProfileComplicated(int id, String relationship)
//		{
//			relation = relationship;
//			this.id = id;
//		}
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			//System.out.println("in actionlistener");
//			String sqlQuery = "SELECT COUNT(1) FROM friend_relationship WHERE EXISTS user = "+ userId+" AND user_being_followed = "+id+")";
//			ProfileGUI newProfile;
//			newProfile = new ProfileGUI(mainPage, dim, relation, id, ConnectionClass.conn, new ActionListenerComplicatedProfile());
//			mainPage.addNext(newProfile);
//		}
//		
//	}
	
	class ActionListenerPlayer implements ActionListener{
		private MusicModel song;
		public ActionListenerPlayer(MusicModel song)
		{
			this.song = song;
		}
		public void actionPerformed(ActionEvent e) {
				//IndpMusicPlayer player = new IndpMusicPlayer
			IndpMusicPlayer player = new IndpMusicPlayer(song, dim);
				mainPage.addCurrent(player);
			
		}
		
	}
	
	public String getName()
	{
		return username;
	}
	public void refresh()
	{
		System.out.println("In refresh for profile");
		jpFollowers.removeAll();
		jpFollowing.removeAll();
		jpFavorites.removeAll();
		populate();
		jpFollowers.revalidate();
		jpFollowers.repaint();
		jpFollowing.revalidate();
		jpFollowing.repaint();
		jpFavorites.revalidate();
		jpFavorites.repaint();
	}

}