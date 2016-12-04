import java.awt.Scrollbar;
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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DataBase {

	Connection con;


	//----------- ouvre la connection sue la DB-----------//
	private void open(){
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
	private void close(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//---------------- remplie une chaine de caractere pour qu'elle en fasse 30 au total----------//
	private String remplissage(String s){
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
	 * Fonction qui permet de mettre à jour un tuple dans la base de données.
	 * @param idSrc l'identifiant du tuple à modifier.
	 * @param valeur les mots clés qui décrivent le modele.
	 * @param path le chemin du modele contenu dans la base de données.
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
	 * Fonction qui permet de supprimer un tuple dans la base de données.
	 * @param idSrc l'identifiant du tuple à supprimer.
	 */
	public void delete(String idSrc){
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
	 * verifie que le chein vers le fichier plac� en parametre existe et si c'est c'est un type .ply, si c'est le cas
	 * il est copi� dans le dossier data du projet
	 * @param path
	 */
	public void addFile(String path){

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
	 * Selectionne tout les modeles et les affiches
	 */
	public void selectAll(){

		open();
		try {
			Statement stmt = con.createStatement();
			int nbLigne;

			ResultSet rs = stmt.executeQuery("select count(*) as count from modele ");
			rs.next();
			nbLigne=rs.getInt("count");
			String[] liste = new String[nbLigne];

			rs = stmt.executeQuery("select * from modele");

			JList<String> jliste= new JList<>(liste);

			int i =0;
			while(rs.next()) {
				liste[i]=remplissage(rs.getString(1))+remplissage(rs.getString(2))+remplissage(rs.getString(3))+rs.getString(4);
				i++;
			}
			JFrame frame = new JFrame("tout les modeles");
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

}





