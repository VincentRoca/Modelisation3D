import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.JOptionPane;

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

	public static void main(String[] args){
		new DataBase().initDB();
	}

}



