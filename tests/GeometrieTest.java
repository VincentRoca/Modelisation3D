

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import maths.MatriceFloat;

public class GeometrieTest {
	MatriceFloat m1;
	@Before
	public void genereMatrice(){
		float [][]f1=new float[4][4];
		f1[0][0]=1; f1[0][1]=2; f1[0][2]=3; f1[0][3]=1;
		f1[1][0]=1; f1[1][1]=2; f1[1][2]=3; f1[1][3]=1;
		f1[2][0]=1; f1[2][1]=2; f1[2][2]=3; f1[3][2]=1;
		f1[3][0]=1; f1[3][1]=1; f1[3][2]=1; f1[3][3]=1;
		
		m1 = new MatriceFloat(f1);
	}

	@Test
	public void testTranslation() {
		
	}

	@Test
	public void testHomothetie() {
		fail("Not yet implemented");
	}

	@Test
	public void testCadrage() {
		fail("Not yet implemented");
	}

	@Test
	public void testRotationX() {
		fail("Not yet implemented");
	}

	@Test
	public void testRotationZ() {
		fail("Not yet implemented");
	}

	@Test
	public void testRotationY() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsobarycentre() {
		fail("Not yet implemented");
	}

}
