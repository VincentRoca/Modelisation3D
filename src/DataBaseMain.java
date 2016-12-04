import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DataBaseMain {

	public static void main(String[] args) throws Exception{
		if(args.length==1) {
			if(args[0].equals("--all")){
				//Methode a modifier pour un affichage plus agréable
				DataBase.selectAll();
			}else if(args[0].equals("--add")){
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
				submit.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0){
						DataBase.addFile(text.getText());
					}
				});
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
		}else if(args.length==2){
			if(args[0].equals("--delete")){
				DataBase.delete(args[1]);
			}if(args[0].equals("--name")){
				DataBase.getInfoModel(args[1]);
			}
		}else if(args[0].equals("--find")){
			DataBase.find(args[1]);
		}
		else
			throw new Exception();
	}
}
