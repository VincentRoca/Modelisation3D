import org.junit.*;

import maths.Geometrie;
import maths.MatriceFloat;

public class GeometrieTest {
	MatriceFloat m1;
	@Before
	public void genereMatrice(){
		float [][]f1=new float[4][4];
		f1[0][0]=2; f1[0][1]=2; f1[0][2]=2; f1[0][3]=2;
		f1[1][0]=2; f1[1][1]=2; f1[1][2]=2; f1[1][3]=2;
		f1[2][0]=4; f1[2][1]=2; f1[2][2]=2; f1[2][3]=2;
		f1[3][0]=12; f1[3][1]=2; f1[3][2]=2; f1[3][3]=2;
		
		m1 = new MatriceFloat(f1);
	}

	@Test
	public void testTranslation() {
		//genereMatrice();
		m1.transformation(Geometrie.translation(1, 1, 1));
		System.out.println(m1);
		/*for (int i = 1; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				testTranslation2(i, j);	
			}
		}*/
	}
	
	public void testTranslation2(int i,int j) {
		System.out.println(i+" , "+j+" : "+m1.get(i, j)+"\n");
		//assertEquals(m1.get(i, j),i+1,0.1);
	}
	
	@Test
	public void testHomothetie() {
		//fail("Not yet implemented");
	}

	@Test
	public void testCadrage() {
		//fail("Not yet implemented");
	}

	@Test
	public void testRotationX() {
		//fail("Not yet implemented");
	}

	@Test
	public void testRotationZ() {
		//fail("Not yet implemented");
	}

	@Test
	public void testRotationY() {
		//fail("Not yet implemented");
	}

	@Test
	public void testIsobarycentre() {
		//fail("Not yet implemented");
	}

}
