import javax.swing.JFrame;

public class Fenetre extends JFrame{
	public Fenetre(){
		JFrame fen = new JFrame();
		fen.setTitle("Projet Modélisation");
		fen.setSize(400, 100);
		fen.setLocationRelativeTo(null);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setVisible(true); 
	} 
}
