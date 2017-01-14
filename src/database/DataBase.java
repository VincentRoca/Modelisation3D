package database;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import affichage.Affichage3D;

public class DataBase {

	static Connection con;


	//----------- ouvre la connection sue la DB-----------//
	private static void open(){
		// --------load the sqlite-JDBC driver---------//

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Driver sqlite not load");
		}
		try {
			con = DriverManager.getConnection("jdbc:sqlite:data/database.db");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 


	}



	//---------------- ferme la connection de la DB----------//
	private static void close(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//---------------- remplie une chaine de caractere pour qu'elle en fasse 30 au total----------//
	private static String remplissage(String s){
		for(int i =s.length();i<30;i++)
			s=s+" ";
		return s;
	}


	public static void initDB(){
		String[] filelist = new File("data").list();
		open();

		try {

			Statement stmt = con.createStatement();
			stmt.execute("drop table if exists modele");
			stmt.execute("create table modele(id char(30), path text, date date, MotsCles text, constraint PK_id PRIMARY KEY (id))");

			for(int i =0;i<filelist.length;i++){
				if(filelist[i].endsWith(".ply")){
					String id=filelist[i].substring(0, filelist[i].length()-4);
					//System.out.println(id);

					//recupere la date
					Calendar c = Calendar.getInstance();
					java.util.Date date =  c.getTime();
					int year = date.getYear();
					int month =date.getMonth();
					int day = date.getDay();
					Date date2 = new Date(year , month, day);

					//requete
					String s ="insert into modele values('"+id+"','data/"+filelist[i]+"','"+date2+"','"+id+"')";

					stmt.executeUpdate(s);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}
	
	

	/**
	 * Fonction qui permet de mettre a jour un tuple dans la base de donnees.
	 * @param idSrc l'identifiant du tuple a modifier.
	 * @param valeur les mots cles qui decrivent le modele.
	 * @param path le chemin du modele contenu dans la base de donnees.
	 */
	public static void update(String idSrc, String valeur, String path){
		open();

		try {

			Statement stmt = con.createStatement();

			stmt.execute("select * from modele");

			//recupere la date
			Calendar c = Calendar.getInstance();
			java.util.Date date =  c.getTime();
			int year = date.getYear();
			int month =date.getMonth();
			int day = date.getDay();
			Date date2 = new Date(year , month, day);
			
			//affichage
			JFrame frame = new JFrame("Edit");
			
			//requete
			//Verifie si le champ est vide ou nom, si oui, la donnee est modifier sinon il ne se passe rien
			if(!path.equals(""))
				stmt.executeUpdate("update modele set path= '"+path+"' where id like '"+idSrc+"';");
			stmt.executeUpdate("update modele set date = '"+date2+"' where id like '"+idSrc+"';");
			if(!valeur.equals(""))
				stmt.executeUpdate("update modele set MotsCles = '"+valeur+"' where id like '"+idSrc+"';");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}
	
	
	public void AfficheUpdate(){
		JFrame frame = new JFrame("Edit");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 290);
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		pane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(pane);
		
		JPanel panel = new JPanel();
		pane.add(panel, BorderLayout.CENTER);
		GridBagLayout layout= new GridBagLayout();
		layout.columnWidths = new int[]{0, 0, 0, 2};
		layout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 4};
		layout.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		layout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(layout);
		
		JLabel lblId = new JLabel("Id :");
		lblId.setPreferredSize(new Dimension(50, 25));
	
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.gridwidth = 2;
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.anchor = GridBagConstraints.EAST;
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 1;
		panel.add(lblId, gbc_lblId);
		
		final JTextField id = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		//id.setText(args[1]);
		
		
		panel.add(id, gbc_textField);
		id.setColumns(10);
		
		JLabel lblChemin = new JLabel("Chemin :");
		lblChemin.setPreferredSize(new Dimension(50, 25));
		GridBagConstraints gbc_lblChemin = new GridBagConstraints();
		gbc_lblChemin.insets = new Insets(0, 0, 5, 5);
		gbc_lblChemin.gridx = 0;
		gbc_lblChemin.gridy = 3;
		panel.add(lblChemin, gbc_lblChemin);
		
		final JTextField chemin = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 3;
		panel.add(chemin, gbc_textField_1);
		chemin.setColumns(10);
		
		JLabel lblMotscl = new JLabel("Mots-Cl\u00E9");
		lblMotscl.setPreferredSize(new Dimension(50, 25));
		GridBagConstraints gbc_lblMotscl = new GridBagConstraints();
		gbc_lblMotscl.insets = new Insets(0, 0, 5, 5);
		gbc_lblMotscl.gridx = 0;
		gbc_lblMotscl.gridy = 5;
		panel.add(lblMotscl, gbc_lblMotscl);
		
		final JTextField keyword = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 5;
		panel.add(keyword, gbc_textField_2);
		keyword.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 2;
		gbc_panel_1.gridy = 7;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setPreferredSize(new Dimension(150, 25));
		panel_1.add(btnCancel, BorderLayout.WEST);
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.exit(0);
			}
		});
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setPreferredSize(new Dimension(150, 25));
		panel_1.add(btnSubmit, BorderLayout.EAST);
		btnSubmit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				DataBase.update(id.getText(), keyword.getText(), chemin.getText());
				System.exit(0);
			}
		});
	}
	

	/**
	 * Fonction qui permet de supprimer un tuple dans la base de donnees, supprime egalement le fichier contenu dans le dossier data
	 * @param idSrc l'identifiant du tuple aï¿½ supprimer.
	 */
	
	private static String id;
	public static void delete(){

		open();

		try {

			final Statement stmt = con.createStatement();
			stmt.execute("select * from modele");
			
			
			int nbLigne;

			ResultSet rs = stmt.executeQuery("select count(*) as count from modele ");
			rs.next();
			nbLigne=rs.getInt("count");
			String[] liste = new String[nbLigne+1];

			rs = stmt.executeQuery("select * from modele");

			JList<String> jliste= new JList<>(liste);
			liste[0] = remplissage("NAME                           EMPLACEMENT                           DATE                           KEYWORD");
			int i =1;
			final List<String> emplacements=new ArrayList<>();
			final List<String> name=new ArrayList<>();
			while(rs.next()) {
				liste[i]=remplissage(rs.getString(1))+remplissage(rs.getString(2))+remplissage(rs.getString(3))+rs.getString(4);
				emplacements.add(rs.getString(2));
				name.add(rs.getString(1));
				i++;
			}
			JFrame frame = new JFrame("Modeles");
			frame.setPreferredSize(new Dimension(600,200));
			JScrollPane pan = new JScrollPane();
			frame.add(pan);
			pan.setViewportView(jliste);
			frame.setVisible(true);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jliste.addListSelectionListener(new ListSelectionListener() {
				
				public void valueChanged(ListSelectionEvent e) {
					id = name.get(e.getFirstIndex()-1);
					System.out.println(id);
					//requete
					String s ="delete from modele where id='"+id+"'";

					try {
						Class.forName("org.sqlite.JDBC");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						System.out.println(e1.getMessage());
						JOptionPane.showMessageDialog(null, "Driver sqlite not load");
					}
					try {
						con = DriverManager.getConnection("jdbc:sqlite:data/database.db");
						stmt.executeUpdate(s);
						File f = new File("data/"+id+".ply");
						
						if(f.exists())
							f.delete();
						else
							JOptionPane.showMessageDialog(null, "Le modele n'est pas present dans la base de donnees, suppression impossible", "Attention", JOptionPane.WARNING_MESSAGE);
	
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally{
						try {
							con.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
						
					
					
					
				}
			});

			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}
	/**
	 * verifie que le chein vers le fichier place en parametre existe et si c'est c'est un type .ply, si c'est le cas
	 * il est copie dans le dossier data du projet, il est egalement ajoutï¿½ a la base de donnees
	 * @param path
	 * @throws SQLException 
	 */
	public static void addFile(String path) throws SQLException{
		open();
		try {
			Statement stmt = con.createStatement();
			
			InputStream sourceFile = new java.io.FileInputStream(path); 
			File destintation = new File("data/"+new File(path).getName());

			OutputStream destinationFile = new FileOutputStream(destintation);
			int nbLecture; 
			byte buffer[] = new byte[1024];
			while ((nbLecture = sourceFile.read(buffer)) != -1){ 
				destinationFile.write(buffer, 0, nbLecture); 
			}
			initDB();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		close();

	}
	
	public void AfficheAdd(){

		//Construction de la fenetre
		JFrame frame= new JFrame("Ajout");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(400,200));
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout(0, 0));
		final JTextField text = new JTextField();
		text.setPreferredSize(new Dimension(100,50));
		JLabel label = new JLabel("Entrez le chemin du fichier que vous voulez ajouter : ");
		label.setPreferredSize(new Dimension(100, 100));
		JPanel boutons = new JPanel();
		boutons.setLayout(new BorderLayout(0,0));
		JButton submit = new JButton("Submit");
		submit.setPreferredSize(new Dimension(200, 30));
		JButton cancel = new JButton("Cancel");
		cancel.setPreferredSize(new Dimension(200, 30));
		
		//Ajout du fichier a la base de donnees vie la méthode dataBase.addFile(String s)
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				try {
					DataBase.addFile(text.getText());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		});
		
		//En cas d'annulation de la part de l'utilisateur, le programme est quitté
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.exit(0);
			}
		});
		pane.add(label, BorderLayout.NORTH);
		boutons.add(submit, BorderLayout.EAST);
		boutons.add(cancel, BorderLayout.WEST);
		pane.add(boutons, BorderLayout.SOUTH);
		pane.add(text, BorderLayout.CENTER);
		frame.getContentPane().add(pane);
		frame.setVisible(true);
	
	}

	/**
	 * Selectionne tout les modeles et les affiche
	 */
	public static void selectAll(){

		open();
		try {
			Statement stmt = con.createStatement();
			int nbLigne;

			ResultSet rs = stmt.executeQuery("select count(*) as count from modele ");
			rs.next();
			nbLigne=rs.getInt("count");
			String[] liste = new String[nbLigne+1];

			rs = stmt.executeQuery("select * from modele");

			JList<String> jliste= new JList<>(liste);
			liste[0] = remplissage("NAME                           EMPLACEMENT                           DATE                           KEYWORD");
			int i =1;
			final List<String> emplacements=new ArrayList<>();
			while(rs.next()) {
				liste[i]=remplissage(rs.getString(1))+remplissage(rs.getString(2))+remplissage(rs.getString(3))+rs.getString(4);
				emplacements.add(rs.getString(2));
				i++;
			}
			JFrame frame = new JFrame("Modeles");
			frame.setPreferredSize(new Dimension(600,200));
			JScrollPane pan = new JScrollPane();
			frame.add(pan);
			pan.setViewportView(jliste);
			frame.setVisible(true);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jliste.addListSelectionListener(new ListSelectionListener() {
				
				public void valueChanged(ListSelectionEvent e) {
					Affichage3D.affichage(emplacements.get(e.getFirstIndex()-1));
				}
			});

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}
	
	/**
	 * Affiche une liste de modele qui selon un mot cle de recherche place en arguments
	 * @param s
	 */
	//Le parametre doit etre une liste de string ( a refaire)
	public static void find(String s){
		JFrame frame= new JFrame("recherche de modele avec le mot clï¿½ : \""+s+"\"");
		frame.setPreferredSize(new Dimension(400,200));
		JScrollPane scrollpan = new JScrollPane();
	
		try{
			open();
		Statement stmt = con.createStatement();
		int nbLigne;

		ResultSet rs = stmt.executeQuery("select count(*) as count from modele ");
		rs.next();
		nbLigne=rs.getInt("count");
		final String[] liste= new String[nbLigne];

		rs = stmt.executeQuery("select * from modele");

		int i =0;
		while(rs.next()) {
			String data=remplissage(rs.getString(1))+remplissage(rs.getString(2))+remplissage(rs.getString(3))+rs.getString(4);
			if(rs.getString(4).contains(s)){
				liste[i]=data;
			}
			i++;
		}
		
		JList<String> jliste= new JList<>(liste);
		scrollpan.setViewportView(jliste);
		
		close();
				

		frame.setLayout(new BorderLayout());
		frame.add(scrollpan,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		}catch(SQLException e){
		System.out.println(e.getMessage());	
		} 


	}
	/**
	 * Methode qui construit une fenetre contenant les informations sur un fichier
	 * @param file, fichier sur lequel portent les informations
	 */
	
	public static void getInfoModel(String file){
		open();
		try {
			String name="";
			String emplacement="";
			String date="";
			
			Statement stmt = con.createStatement();
			String[] liste = new String[2];
			JList<String> jliste= new JList<>(liste);

			ResultSet rs = stmt.executeQuery("select * from modele where id like '"+file+"'");
			

			while(rs.next()){
				name = rs.getString(1);
				emplacement = rs.getString(2);
				date = rs.getString(3);
			}
			
			if(name.equals("") && emplacement.equals("") && date.equals("")){
				JOptionPane.showMessageDialog(null, "Le modele n'est pas present dans la base de donnees", "Attention", JOptionPane.WARNING_MESSAGE);
				System.exit(0);
			}

			JFrame frame = new JFrame("Informations sur le fichier : "+file);
			JPanel pane = new JPanel();
			pane.setLayout(new BorderLayout(0,0));
			JPanel nom = new JPanel();
			nom.setLayout(new BorderLayout(0,0));
			nom.setPreferredSize(new Dimension(300,50));
			nom.add(new JLabel("Nom :"), BorderLayout.WEST);
			nom.add(new JLabel(name), BorderLayout.EAST);
			JPanel emp = new JPanel();
			emp.setLayout(new BorderLayout(0,0));
			emp.setPreferredSize(new Dimension(300,50));
			emp.add(new JLabel("Emplacement :"), BorderLayout.WEST);
			emp.add(new JLabel(emplacement), BorderLayout.EAST);
			JPanel dat = new JPanel();
			dat.setLayout(new BorderLayout(0,0));
			emp.setPreferredSize(new Dimension(300,50));
			dat.add(new JLabel("Date :"), BorderLayout.WEST);
			dat.add(new JLabel(date), BorderLayout.EAST);
			pane.add(nom, BorderLayout.NORTH);
			pane.add(emp, BorderLayout.CENTER);
			pane.add(dat, BorderLayout.SOUTH);
			frame.getContentPane().add(pane);
			frame.setPreferredSize(new Dimension(400,150));
			frame.setVisible(true);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		
	}
	

}





