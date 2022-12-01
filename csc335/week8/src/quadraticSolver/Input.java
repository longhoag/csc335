package quadraticSolver;

import java.util.Scanner;

public class Input {
	private static double A;
	private static double B;
	private static double C;
	
	public static double getA() {
		return A;
	}
	
	public static double getB() {
		return B;
	}
	
	public static double getC() {
		return C;
	}
	
	public static void getInput() {
		
		//-- prompt user for input
		Scanner input = new Scanner(System.in);
		System.out.println("Input numeric coefficients A, B, C: ");
		
		try {
			A = input.nextDouble();
			B = input.nextDouble();
			C = input.nextDouble();
		} catch (Exception e) {
			System.out.println("The input is invalid!");
			System.exit(0);
		} 
		finally {
			input.close();
		}
	}

}
