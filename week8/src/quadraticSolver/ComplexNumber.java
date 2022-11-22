package quadraticSolver;

public class ComplexNumber {
	private static final double PRECISION = 0.00001;
	private double re;
	private double im;
	
	public ComplexNumber() {
		this.re = this.getRe();
		this.im = this.getIm();
	}
	
	public ComplexNumber(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	public ComplexNumber(ComplexNumber complex) {
		this.re = complex.getRe();
		this.im = complex.getIm();
	}
	
	public double getRe() {
		return this.re;
	}
	
	public double getIm() {
		return this.im;
	}
	
	@Override
	public String toString() {
		String out = "";
		if(this.getIm() > 0) {
			out = this.getRe() + " + " + this.getIm() + "i";
		}
		else {
			out = this.getRe() + " - " + Math.abs(this.getIm()) + "i";
		}
		
		return out;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
            return true;
        }

        if (!(o instanceof ComplexNumber)) {
            return false;
        }

        ComplexNumber c = (ComplexNumber) o;

        return Double.compare(re, c.re) == 0 && Double.compare(im, c.im) == 0;   
        
	}
}
