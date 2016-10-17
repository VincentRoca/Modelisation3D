import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import org.junit.Test;

public class ModeleTest {
	
	@Test
	public void testPointEquals() {
		Point p1=new Point((float)2.5,(float)3,(float)4.2),
				p2=new Point((float)2.5,(float)3,(float)4.2);
		assertEquals(p1,p2);
		p2=new Point((float)2,(float)2.3,(float)4.2);
		assertNotEquals(p1, p2);
	}
	
	@Test
	public void testFaceEquals() {
		Point[] p1=new Point[]{new Point((float)2,(float)3.2,(float)4),new Point((float)101.5,(float)102.2,(float)1),
				new Point((float)10,(float)200,(float)10.35)},
				p2=new Point[]{new Point((float)101.5,(float)102.2,(float)1),new Point((float)2,(float)3.2,(float)4),
						new Point((float)10,(float)200,(float)10.35)};
		Face f1=new Face(p1), f2=new Face(p2);
		assertEquals(f1,f2);
		f1.getPoints()[2]=new Point((float)23.2,(float)31,(float)34.32);
		assertNotEquals(f1,f2);
	}
	
	@Test
	public void testLectureModele() {
		String[] contenuFile=new String[]{
				"ply",
				"format ascii 1.0",
				"element vertex 4",
				"property float32 x",
				"property float32 y",
				"property float32 z",
				"element face 3",
				"property list uint8 int32 vertex_indices",
				"end_header",
				"2.0 3 4.5",
				"4.3 3.4 100",
				"54 2.95 3.4",
				"78.2 43 1.2",
				"3 0 1 3",
				"3 1 3 2",
				"3 0 2 3"};
		File f=new File("fileTestLectureModele");
		try {
			Writer writer = new PrintWriter(f);
			for(String s : contenuFile) 
				writer.write(s+"\n");
			writer.close();
			f.createNewFile();
			Modele m=new Modele("fileTestLectureModele");
			Face[] faces=m.getFaces();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
