package quadraticSolver;

public class MainApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Input.getInput();
		Roots roots = Solver.solve(Input.getA(), Input.getB(), Input.getC());
		Output.output(roots);
	}

}
