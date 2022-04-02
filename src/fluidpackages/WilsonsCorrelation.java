package fluidpackages;

import main.GenericMethods;
//Solves Wilson correlation and calculates dew points and bubble points, Or Uses Standing equation for volatiles
public class WilsonsCorrelation implements FluidPackage {
//Instance Variables
	private double[] Pc;
	private double[] Tc;
	private double[] omega;
	private double P;
	private double T;
	private double[] z;
	private int[] NOC;
	private boolean isBPCalc;
	private double[] Tb;
	private boolean[] isGas;
	// Dew Point Check
	double pU = 0;
	double pL = 0;

	//Initiating object of type Database
	
	//Constructor
	public WilsonsCorrelation(double T, double P, double[] Tc, double[] Pc, double[] omega, double[] z, double[] Tb, boolean[] isGas ) {
		this.T = T;
		this.P = P;
		this.Tc = Tc.clone();
		this.Pc = Pc.clone();
		this.omega = omega.clone();
		this.z = z.clone();
		this.Tb = Tb.clone();
		this.isGas = isGas.clone();
	}
//Copy Constructor
	public WilsonsCorrelation(WilsonsCorrelation wc) {
		this.Pc = wc.Pc.clone();
		this.Tc = wc.Tc.clone();
		this.omega = wc.omega.clone();
		this.P = wc.P;
		this.T = wc.T;
		this.z = wc.z.clone();
		this.Tb = wc.Tb.clone();
		this.isGas = wc.isGas.clone();
	}
//Gets K values calcuated with Standing or Wilson Correlation
	public double[] getK() {
		return calcK();
	}
//Sets Pressure
	public void setP(double P) {
		this.P = P;
	}
//Gets Pressure
	public double getP() {
		return this.P;
	}

	//Calculates a values for standing correlation
	private double calc_a(){
		return 1.2 + 0.00045*this.P + (15*(10e-08)*Math.pow(this.P, 2));
	}
	//Calculates c values for standing correlation
	private double calc_c(){
		return 0.89 - 0.00017*this.P -3.5*(10e-08)*Math.pow(this.P, 2);
	}
	//Calculates b value for standing correlation
	private double getb(int j){
		return Math.log(Pc[j]/14.7)/(1/this.Tb[j]-1/this.Tc[j]);
	}
	//Calculates F for standing correlation
	private double calc_F(int j){
		return getb(j)*((1./this.Tb[j]) - 1./(this.T));
	}
	//Calculates wilson correlation or standing correlation if it is a gas
	private double[] calcK() {
		double[] K = new double[this.Tc.length];
		for (int i = 0; i < this.Tc.length; i++) {
			if(isGas[i]){
				K[i] = (1./((this.P/1000)))*Math.pow(10., calc_a() + calc_c()*calc_F(i));
			}else{//Normal Wilson's
				K[i] = (this.Pc[i] / this.P)* Math.exp(5.37 * (1 + this.omega[i]) * (1. - (this.Tc[i] / this.T)));
			}
		}
		return K;
	}
//Calculates Dew Point Pressure
	private double calcDewP(){
		double sum=0;
		for (int i = 0; i < this.Tc.length; i++) {
			sum += this.z[i]/(this.Pc[i]*Math.exp(5.37 * (1 + this.omega[i]) * (1. - (this.Tc[i] / this.T))));
		}
		return (1/sum);
	}
	//Calculates Bubble Point Pressure
	private double calcBubbleP(){
		double sum=0;
		for (int i = 0; i < this.Tc.length; i++) {
			sum += this.z[i]*((this.Pc[i])*Math.exp( 5.37 * ((1 + this.omega[i]) * (1. - (this.Tc[i] / this.T)))));
		}
		return sum;
	}
//Copy constructor for fluidpackage
	public FluidPackage copyConstructor(FluidPackage fp) {

		return new WilsonsCorrelation((WilsonsCorrelation) fp);
	}
//Get bubble point pressure by iteration
	public double getBubbleP() {
		this.isBPCalc = false;
		double bpP;
		double pOld = this.P;
		double sumY;
		this.P = calcBubbleP();
		double count=0;
		do {
			count++;
			if(count>1000){
				break;
			}
			sumY = getZKProductSum();
		} while (!(doesYSumToOne(sumY)));
		this.pU = 0;
		this.pL = 0;
		bpP = this.P;
		this.P = pOld;
		return bpP;
	}
//Gets bubble point pressure via bisection method and incremental search
	private double PBubbleBisection(double sum) {
		double pR = (this.pU + this.pL) / 2;
		if (sum < 1) {
			if (Math.abs(this.pU) < GenericMethods.getErrorFactor()
					| Math.abs(this.pL) < GenericMethods.getErrorFactor()) {
				this.pL = this.P;
				return this.P = this.P - 0.05 * this.P;
			} else {
				this.pL = this.P;
				return pR;
			}
		} else {
			if (Math.abs(this.pU) < GenericMethods.getErrorFactor()
					| Math.abs(this.pL) < GenericMethods.getErrorFactor()) {
				this.pU = this.P;
				return this.P = this.P + 0.05 * this.P;
			} else {
				this.pU = this.P;
				return pR;
			}
		}
	}
//Gets Z ratio sum 
	private double getZKRatioSum() {
		double sum = 0;
		double[] K = calcK();
		for (int j = 0; j < this.Pc.length; j++) {
			sum += this.z[j] / K[j];
		}
		return sum;
	}
//Gets ZK product sum
	private double getZKProductSum() {
		double sum = 0;
		double[] K = calcK();
		for (int j = 0; j < this.Pc.length; j++) {
			sum += this.z[j] * K[j];
		}
		return sum;
	}
//gets Dew Point Pressure
	public double getDewP() {
		this.isBPCalc = true;
		double dpP;
		double pOld = this.P;
		double sumX;
		this.P = calcDewP();
		double count=0;
		do {
			count++;
			if(count>1000){
				break;
			}
			sumX = getZKRatioSum();
		} while (!(doesXSumToOne(sumX)));
		this.pL = 0;
		this.pU = 0;
		dpP = this.P;
		this.P = pOld;
		return dpP;
	}
//Does X sum to one
	public boolean doesXSumToOne(double sumX) {
		if (Math.abs(1 - sumX) < 0.1) {
			return true;
		} else if (sumX < 1) {
			this.P = PDewBisection(sumX); 
			return false;
		} else if (sumX > 1) {
			this.P = PDewBisection(sumX);
			return false;
		} else if (Double.isNaN(sumX)) {
			System.out.println("Loading... Please wait while your solution is found.");//"WARNING: Dew Point Pressure did not converge in Wilsons Correlation (NaN Error), T is too low;
			return true;
		} else {
			System.out.println("Loading... Please wait, checking the pockets of my winter coat.");//"WARNING: Dew Point Pressure did not converge inWilsons Correlation (Unknown Error)
			return true;
		}
	}
//PDewBisection
	private double PDewBisection(double sum) {
		double pR = (this.pU + this.pL) / 2;
		if (sum < 1) {
			if (Math.abs(this.pU) < GenericMethods.getErrorFactor()
					| Math.abs(this.pL) < GenericMethods.getErrorFactor()) {
				this.pL = this.P;
				return this.P = this.P + 0.05 * this.P;
			} else {
				this.pL = this.P;
				return pR;
			}
		} else {
			if (Math.abs(this.pU) < GenericMethods.getErrorFactor()
					| Math.abs(this.pL) < GenericMethods.getErrorFactor()) {
				this.pU = this.P;
				return this.P = this.P - 0.05 * this.P;
			} else {
				this.pU = this.P;
				return pR;
			}
		}

	}

	//Does Y Sum to one for bubble point calc
	public boolean doesYSumToOne(double sumY) {

		if (Math.abs(1 - sumY) < 0.1) {
			return true;
		} else if (sumY < 1) {
			this.P = PBubbleBisection(sumY);
			return false;
		} else if (sumY > 1) {
			this.P =PBubbleBisection(sumY);// this.P * 1.20;
			return false;
		} else if (Double.isNaN(sumY)) {
			System.out.println("WARNING: Dew Point Pressure did not converge in Wilsons Correlation (NaN Error)");
			return true;
		} else {
			System.out.println("WARNING: Dew Point Pressure did not converge in Wilsons Correlation (Unknown Error)");
			return true;
		}

	}

	// public boolean doesYSumToOne(double sumY) {
	//
	// if (Math.abs(1 - sumY) < GenericMethods.getErrorFactor()) {
	// return true;
	// } else {
	// return false;
	// }
	// }
	//Set Temperature
	public void setT(double T){
		this.T = T;
	}
//Sets Z values (inlet comp)
	public void setZ(double[] Z) {
		this.z = Z.clone();
	}

}
