package quadraticSolver;

public class Output {
	public static void output(Roots roots) {
//		ROOTTYPE flag = roots.getFlag();
//		ComplexNumber one = roots.getCroot1();
//		ComplexNumber two = roots.getCroot2();
		
		System.out.println(roots.toString());
		
//		switch (flag) {
//		case TWOCOMPLEX:
//			System.out.println(one.toString() + ", " + two.toString());
//			break;
//		case TWOREAL:
//			System.out.println(roots.toString());
//			break;
//		case ONEREAL:
//			System.out.println(roots.getRroot1());
//			break;
//		case ONELINEAR:
//			System.out.println("Linear Equation: " + roots.getRroot1());
//			break;
//		case NONE:
//			System.out.println("No Root Found");
//			break;
//		case INFINITE: 
//			System.out.println("Infinite Roots Found");
//			break;
//		default:
//			System.out.print("Pending Roots...");
//			break;
//		}
	}
	public static void output(String statement) {
		System.out.println(statement);
	}
}
