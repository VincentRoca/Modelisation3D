import java.awt.BorderLayout;
import java.awt.Dimension;
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
import java.util.Calendar;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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


	public void initDB(){
		String[] filelist = new File("data").list();
		open();

		try {

			Statement stmt = con.createStatement();
			stmt.execute("drop table if exists modele");
			stmt.execute("create table modele(id char(30), path text, date date, MotsCles text, constraint PK_id PRIMARY KEY (id))");

			for(int i =0;i<filelist.length;i++){
				if(filelist[i].endsWith(".ply")){
					String id=filelist[i].substring(0, filelist[i].length()-4);
					System.out.println(id);

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
	 * Fonction qui permet de mettre Ã  jour un tuple dans la base de donnÃ©es.
	 * @param idSrc l'identifiant du tuple Ã  modifier.
	 * @param valeur les mots clÃ©s qui dÃ©crivent le modele.
	 * @param path le chemin du modele contenu dans la base de donnÃ©es.
	 */
	public void update(String idSrc, String valeur, String path){
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

			//requete

			String s ="update modele set path='"+path+"', date='"+date2+ "' MotsCles='" + valeur + "', where id='"+idSrc+"'";

			stmt.executeUpdate(s);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}

	/**
	 * Fonction qui permet de supprimer un tuple dans la base de donnÃ©es.
	 * @param idSrc l'identifiant du tuple Ã  supprimer.
	 */
	public static void delete(String idSrc){
		open();

		try {

			Statement stmt = con.createStatement();
			stmt.execute("select * from modele");

			//requete
			String s ="delete from modele where id='"+idSrc+"'";
			stmt.executeUpdate(s);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}
	/**
	 * verifie que le chein vers le fichier placé en parametre existe et si c'est c'est un type .ply, si c'est le cas
	 * il est copié dans le dossier data du projet
	 * @param path
	 */
	public static void addFile(String path){

		try {
			InputStream sourceFile = new java.io.FileInputStream(path); 
			File destintation = new File("data/"+new File(path).getName());

			OutputStream destinationFile = new FileOutputStream(destintation);
			int nbLecture; 
			byte buffer[] = new byte[1024];
			while ((nbLecture = sourceFile.read(buffer)) != -1){ 
				destinationFile.write(buffer, 0, nbLecture); 
			}


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}



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
			liste[0] = remplissage("NAME                           EMPLACEMENT                           DATE");
			int i =1;
			while(rs.next()) {
				liste[i]=remplissage(rs.getString(1))+remplissage(rs.getString(2))+remplissage(rs.getString(3))/*+rs.getString(4)*/;
				i++;
			}
			JFrame frame = new JFrame("Modeles");
			JScrollPane pan = new JScrollPane();
			frame.add(pan);
			pan.setViewportView(jliste);
			frame.setVisible(true);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
	}
	
	/**
	 * Affiche une liste de modele qui selon un mot clé de recherche placé en arguments
	 * @param s
	 */
	//Le parametre doit etre une liste de string ( à refaire)
	public static void find(String s){
		JFrame frame= new JFrame("recherche de modele avec le mot clé : \""+s+"\"");
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
				JOptionPane.showMessageDialog(null, "Le modele n'est pas présent dans la base de données", "Attention", JOptionPane.WARNING_MESSAGE);
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





