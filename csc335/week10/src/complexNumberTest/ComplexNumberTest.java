package complexNumberTest;

import static org.junit.Assert.*;

import org.junit.Test;

import complexNumber.ComplexNumber;

public class ComplexNumberTest {
	private final double PRECISION = 0.000001;

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
	
	@Test 
	public void testAdd() {
		ComplexNumber lhs = new ComplexNumber(1.0, 2.0);
		ComplexNumber rhs = new ComplexNumber(3.0, 4.0);
		ComplexNumber result;
		
		result = lhs.add(rhs);
		
		assertTrue((Math.abs(result.getReal() - 4.0) < PRECISION) && (Math.abs(result.getImag() - 6.0) < PRECISION));
	}
}
