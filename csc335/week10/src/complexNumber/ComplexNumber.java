package complexNumber;

public class ComplexNumber {
	
	private double r, i;
	private final double PRECISION = 0.00001;
	
	public ComplexNumber(double r, double i)
	{
		this.setReal(r);
		this.setImag(i);
	}

	public ComplexNumber(ComplexNumber rhs)
	{
		this.setReal(rhs.getReal());
		this.setImag(rhs.getImag());
	}

	public void setReal(double r)
	{
		this.r = r;
	}

	public void setImag(double i)
	{
		this.i = i;
	}
	
	public double getReal()
	{
		return r;
	}
	
	public double getImag()
	{
		return i;
	}
	
	@Override
    public String toString() 
    {
		String s = "";
        s = "" + this.getReal();
        s += (this.getImag() < 0) ? " - " + Math.abs(this.getImag()) + "i" : " + " + this.getImag() + "i";

		return s;
    }


	
	@Override
    public boolean equals(Object obj)
    {
		if (obj instanceof ComplexNumber) {
			ComplexNumber rhs = (ComplexNumber)obj;
			if ((Math.abs(this.getReal() - rhs.getReal()) < PRECISION) && 
					(Math.abs(this.getImag() - rhs.getImag()) < PRECISION)){
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
    }

	public ComplexNumber add(ComplexNumber rhs) 
	{
		double r = this.getReal() + rhs.getReal();
		double i = this.getImag() + rhs.getImag();
	    return new ComplexNumber(r, i);
	}

	public ComplexNumber sub(ComplexNumber rhs) 
	{
		double r = this.getReal() - rhs.getReal();
		double i = this.getImag() - rhs.getImag();
	    return new ComplexNumber(r, i);
	}

	public ComplexNumber mult(ComplexNumber rhs) 
	{
			double r = (this.getReal() * rhs.getReal()) - (this.getImag() * rhs.getImag());
			double i = (this.getReal() * rhs.getImag()) + (this.getImag() * rhs.getReal());
		    return new ComplexNumber(r, i);
	}

	public static void main(String[] args) {
		System.out.println("Double Version");
		ComplexNumber lhs = new ComplexNumber(1.0, 2.0);
		ComplexNumber rhs = new ComplexNumber(3.0, 4.0);
		ComplexNumber neg = new ComplexNumber(5.0, -1.5);

		ComplexNumber result;
		
		System.out.println(lhs);
		System.out.println(neg);
		
		result = lhs.add(rhs);
		System.out.print("(" + lhs + ") + (" + rhs + ") = ");
		System.out.println("(" + result + ")");
		
		result = lhs.sub(rhs);
		System.out.print("(" + lhs + ") - (" + rhs + ") = ");
		System.out.println("(" + result + ")");

		System.out.println("(" + lhs + ") == (" + lhs + ") : " + lhs.equals(lhs));
		System.out.println("(" + lhs + ") == (" + rhs + ") : " + lhs.equals(rhs));
		
		result = lhs.mult(rhs);
		System.out.print("(" + lhs + ") * (" + rhs + ") = ");
		System.out.println("(" + result + ")");

	}

}








