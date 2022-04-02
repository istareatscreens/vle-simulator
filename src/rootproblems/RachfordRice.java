package rootproblems;
//Root finding rathford rice equation, for solving Flash vessel, iterated with root finding method such as bisection or ridders method
public class RachfordRice implements RootProblem {

	private double[] z;
	private double[] K;
//Constructor
	public RachfordRice(double[] z, double[] K) {
		this.z = z.clone();
		this.K = K.clone();
	}
//Method iterated by Root finding method
	public double eq(double x) {
		//Calls rathford rice equation
		return eqRachfordRice(x);
	}
//Rathford rice equation, typically where the program breaks (the main site of nonconvergence)
	public double eqRachfordRice(double psi) {
		double sum = 0;
		double temp = 0;
		//Computes sum that should add up to 0, The value of each sum component can be [-1,1], therefore bounded each term in the sum between -1 and 1 to prevent
		//acquiring an impossible root
		for (int i = 0; i < this.z.length; i++) {
			temp = this.z[i] * ( this.K[i]-1) / (1 + (psi) * (this.K[i] - 1));
			if (temp < -1) {
				temp = -1;
			} else if (temp > 1) {
				temp = 1;
			}
			sum += temp;
		}
		//returns usm of rathford rice
		return sum;
	}

}
