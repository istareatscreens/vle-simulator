package rootproblems;


//takes in A and B values from peng robinson and computes coefficents for Z^3 + b*Z^2 + c*Z + d = 0 for cardanios method to solve for Z values
public class CubicEquationOfState{

	private double A, B;
	//Takes in A and B values from peng robinson
	public CubicEquationOfState(double[] coefEOS){
		this.A = coefEOS[0];
		this.B = coefEOS[1];
	}
	//mutates coefficents
	public void setCoef(double[] coefEOS){
		this.A = coefEOS[0];
		this.B = coefEOS[1];
	}
	//Computes coefficents for Equation of state
	private double C2(){
		return (this.B-1);
	}
	
	private double C1(){
		return this.A-3*Math.pow(this.B,2)-2*B;
	}
	
	private double C0(){
		return Math.pow(this.B,3)+Math.pow(this.B, 2)-this.A*this.B;
	}
	
	public double[] getRoots(){
	 return CardanosFormula();	
	}
	
	//solves for roots with cardanios method
	private double[] CardanosFormula(){
		CardanoMethod cm = new CardanoMethod(1, C2(), C1(), C0());
		return cm.getRoots();
	}
	
	
}