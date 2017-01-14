package database;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DataBaseMain {
	
	public static void main(String[] args) throws Exception{
		if(args.length==0)
			DataBase.selectAll();
		if(args.length==1) {
			/*if(args[0].equals("")){
				//Methode a modifier pour un affichage plus agreable
				DataBase.selectAll();
			}else */if(args[0].equals("--add")){
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
				JLabel label = new JLabel("Entrez le nom du fichier que vous voulez ajouter : ");
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
			//Verification du nombre d'arguments (ici, ne nom de la commande suivi d'un parametre
		}else if(args.length==2){
			if(args[0].equals("--delete")){
				DataBase.delete(args[1]);
			}if(args[0].equals("--name")){
				DataBase.getInfoModel(args[1]);
			}
			if(args[0].equals("--edit")){
				/*** FRAME ***/
				JFrame frame = new JFrame("Edit");
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
				id.setText(args[1]);
				
				id.setEditable(false); //Le champ id ne peut etre modifié 
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
				
				frame.pack();
				frame.setVisible(true);
			}

			else if(args[0].equals("--find")){
				DataBase.find(args[1]);
			}
		}
		/*else{
			DataBase.selectAll();
			JOptionPane.showMessageDialog(null, "Mauvaise utilisation", "Attention", JOptionPane.WARNING_MESSAGE);
		}*/
			
		
	}
}
