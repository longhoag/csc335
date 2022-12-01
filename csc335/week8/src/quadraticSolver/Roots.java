package quadraticSolver;

public class Roots {
	private double rroot1;
	private double rroot2;
	private ROOTTYPE flag = ROOTTYPE.NONE;
	private ComplexNumber cnum1;
	private ComplexNumber cnum2;
	
	public Roots() {
		this.rroot1 = this.getRroot1();
		this.rroot2 = this.getRroot2();
		this.flag = this.getFlag();
		this.cnum1 = this.getCroot1();
		this.cnum2 = this.getCroot2();
	}
	
	public ROOTTYPE getFlag() {
		return flag;
	}
	
	public void setFlag(ROOTTYPE flag) {
		this.flag = flag;
	}
	
	public ComplexNumber getCroot1() {
		return cnum1;
	}
	
	public void setCroot1(ComplexNumber root1) {
		this.cnum1 = root1;
	}
	
	public ComplexNumber getCroot2() {
		return cnum2;
	}
	
	public void setCroot2(ComplexNumber root2) {
		this.cnum2 = root2;
	}
	
	public double getRroot1() {
		return rroot1;
	}
	
	public void setRroot1(double root1) {
		this.rroot1 = root1;
	}
	
	public double getRroot2() {
		return rroot2;
	}
	
	public void setRroot2(double root2) {
		this.rroot2 = root2;
	}
	
	@Override
	public String toString() {
//		String out = this.rroot1 + ", " + this.rroot2;
//		return out;
		String out = "";
		
		switch (getFlag()) {
		case TWOCOMPLEX:
			out = cnum1.toString() + ", " + cnum2.toString();
			break;
		case TWOREAL:
			out = rroot1 + ", " + rroot2;
			break;
		case ONEREAL:
			out = " " + getRroot1();
			break;
		case ONELINEAR:
			out = "Linear Equation: " + getRroot1();
			break;
		case NONE:
			out = "No Root Found";
			break;
		case INFINITE: 
			out = "Infinite Roots Found";
			break;
		default:
			out = "Pending Roots...";
			break;
		}
		
		return out;
	}
}
