import java.awt.Dimension;

public class References {
	static private Dimension dimension =java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	
	public static int SCREENX =(int) dimension.getWidth();
	public static int SCREENY =(int) dimension.getHeight();
	
	public static int origineX=(int)(dimension.getWidth()/2);
	public static int origineY=(int)(dimension.getHeight()/2);
	
	public static int echelleX=30;
	public static int echelleY=30;
	public static int echelleZ=15;
	


}