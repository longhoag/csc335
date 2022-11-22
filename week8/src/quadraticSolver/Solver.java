package quadraticSolver;

public class Solver {
	private static final double PRECISION =  0.000001;
	
	public static Roots solve(double a, double b, double c) {
		
		Roots roots = new Roots();
		//-- delta is the discriminant 
		
		//-- a!=0
		if(!(Math.abs(a) < PRECISION)) {
			double discriminant = b * b - 4 * a * c;
			//--delta = 0
			if(Math.abs(discriminant) < PRECISION) {
				double realRoot = (- b) / (2 * a);
				roots.setRroot1(realRoot);
				roots.setFlag(ROOTTYPE.ONEREAL);
			}
			//-- delta > 0
			if(discriminant > 0) {
				double root1 = (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
				double root2 = (-b - Math.sqrt(b * b - 4 * a * c)) / (2 * a);
				roots.setRroot1(root1);
				roots.setRroot2(root2);
				roots.setFlag(ROOTTYPE.TWOREAL);
			}
			//-- delta < 0
			if(discriminant < 0) {
				double real = (-b) / (2 * a);
				double ima = Math.sqrt(-(b * b - 4 * a * c)) / (2 * a);
				
				ComplexNumber cnum1 = new ComplexNumber(real, ima);
				//-- cnum2, ima is negative will be added in the output (-)
				ComplexNumber cnum2 = new ComplexNumber(real, -ima);
				
				roots.setCroot1(cnum1);
				roots.setCroot2(cnum2);
				roots.setFlag(ROOTTYPE.TWOCOMPLEX);
			}
		}
		//-- a = 0
		else if(Math.abs(a) < PRECISION) {
			//-- b!=0
			if(!(Math.abs(b) < PRECISION)) {
				double linear = -c / b;
				roots.setRroot1(linear);
				roots.setFlag(ROOTTYPE.ONELINEAR);
			}
			//-- b=0
			else if(Math.abs(b) < PRECISION) {
				//--c=0
				if(Math.abs(c) < PRECISION) {
					roots.setFlag(ROOTTYPE.INFINITE);
				}
				//--c!=0
				else if(!(Math.abs(c) < PRECISION)) {
					roots.setFlag(ROOTTYPE.NONE);
				}
			}
			
		}
	
		return roots;
		
	}
}
