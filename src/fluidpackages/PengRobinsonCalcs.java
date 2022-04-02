package fluidpackages;

import chemicals.kijValues;
import main.GenericMethods;

//Does all main calculations for peng robinson equation of states, Parent class to all Peng Robinson problems (Dew Point, bubble point, peng robinson and real Cps)
public abstract class PengRobinsonCalcs {

	// Input Variables
	protected double[] Pc;
	protected double[] Tc;
	protected double[] omega;
	protected double P;
	protected double T;
	protected kijValues kijGen;

	// Calculated Values
	//Ideal gas constant
	protected static final double R_Gas = 8.3144598;
//Constructor for Pengrobinson
	public PengRobinsonCalcs(double T, double P, double[] Tc, double[] Pc, double[] omega, kijValues kijGen, boolean isNotIdeal) {
		if(isNotIdeal){
		this.Pc = Pc.clone();
		this.Tc = Tc.clone();
		this.omega = omega.clone();
		this.P = P;
		this.T = T;
		this.kijGen = new kijValues(kijGen);
		}
	}

//Computes kappa
	private double calcKapp(int i) {
		if (this.omega[i] < 0.491 || Math.abs(1 - this.omega[i]) < GenericMethods.getErrorFactor()) {
			return 0.37464 + 1.54226 * this.omega[i] - 0.26992 * Math.pow(this.omega[i], 2.);
		} else {
			return 0.379642 + 1.48503 * this.omega[i] - 0.164423 * Math.pow(this.omega[i], 2)
					+ 0.016666 * Math.pow(this.omega[i], 3);
		}
	}

//Computes alpha
	private double calcAlpha(int i) {
		return Math.pow((1. + calcKapp(i) * (1. - Math.pow(this.T / this.Tc[i], 0.5))), 2.);
	}
//Computes a_i values with alpha multiplied
	private double[] a_iCalc() {
		double[] a_i = new double[this.Pc.length];
		for (int i = 0; i < this.Pc.length; i++) {
			a_i[i] = 0.45724 * Math.pow(PengRobinsonCalcs.R_Gas * this.Tc[i], 2.) * calcAlpha(i) / (1000 * this.Pc[i]);
		}
		return a_i;
	}
//computes b_i calcs
	public double[] b_iCalc() {
		double[] b_i = new double[this.Pc.length];
		for (int i = 0; i < this.Pc.length; i++) {
			b_i[i] = 0.07780 * PengRobinsonCalcs.R_Gas * Tc[i] / (1000 * Pc[i]);
		}
		return b_i;
	}
//Computes equation of state A nad B values for coefficents of the equation of state
	protected double[] calcEOSCoefs(double[] fraction) {
		double[] temp = new double[2];
		temp[0] = aCalc(fraction) * (1000 * this.P) / Math.pow(PengRobinsonCalcs.R_Gas * this.T, 2.);
		temp[1] = bCalc(fraction) * (1000 * this.P) / (PengRobinsonCalcs.R_Gas * this.T);
		return temp;
	}
//Computes a value for phi formula
	private double aCalc(double[] fraction) {
		double[] a_i = a_iCalc();
		double[][] k_ij = new double[Pc.length][Pc.length];
		k_ij = this.kijGen.calcValues(T);
		double a = 0;
		for (int i = 0; i < this.Pc.length; i++) {
			for (int j = 0; j < this.Pc.length; j++) {
				a += Math.sqrt(a_i[i] * a_i[j]) * (1 - k_ij[i][j]) * fraction[i] * fraction[j];
			}
		}
		return a;
	}
//Computes b value for phi formula
	private double bCalc(double[] fraction) {
		double[] b_i = b_iCalc();
		double b = 0;
		for (int i = 0; i < this.Pc.length; i++) {
			b += b_i[i] * fraction[i];
		}
		return b;
	}
//Gets phi value for calculating K
	protected double[] getPhi(double[] fraction, double Z, double[] coefEOS) {
		double[] b_i = b_iCalc();
		double[] a_i = a_iCalc();
		double[] temp = new double[this.Pc.length];
		double delta_One = 1+Math.sqrt(2);
		double delta_Two = 1-Math.sqrt(2);
		double psiConst;
//		double a = aCalc(fraction);
		double b = bCalc(fraction);
		for (int i = 0; i < this.Pc.length; i++) {
			temp[i] = Math.exp((b_i[i] / b) * (Z - 1.) - Math.log(Z - coefEOS[1]) - (coefEOS[0]/ (coefEOS[1]*(delta_Two-delta_One)))* ((2. * calcPsi_i(fraction, a_i, i) /calcPsiConst(fraction,a_i)) - (b_i[i]/b))*Math.log((Z+delta_Two*coefEOS[1])/(Z+delta_One*coefEOS[1])));
		}
		return temp;
	}
	//Calculates Psi Constant for computing phi constants
	protected double calcPsiConst(double[] fraction, double[] a_i){
		double phiC=0;
		double[][] k_ij = new double[Pc.length][Pc.length];
		k_ij = this.kijGen.calcValues(T);
		for(int i = 0; i<Pc.length; i++){
			for(int j=0; j<Pc.length;j++){
				phiC += fraction[j]*fraction[i]*Math.sqrt(a_i[i]*a_i[j])*(1-k_ij[i][j]);
			}
		}
		return phiC;
	}
//calculates psi Constant for particular state
	protected double calcPsi_i(double[] fraction, double[] a_i, int i) {
		double temp = 0;
		double[][] k_ij = new double[Pc.length][Pc.length];
		k_ij = this.kijGen.calcValues(T);
		for (int j = 0; j < this.Pc.length; j++) {
			temp += fraction[j] * Math.sqrt(a_i[i] * a_i[j]) * (1 - k_ij[i][j]);
		}
		return temp;
	}
	//sets temperatuare
public void setT(double T){	
		this.T = T;
	}

//Real Heat capacity Calcs
//Computes a_c_iCalc
private double[] a_c_iCalc() {
	double[] a_i = new double[this.Pc.length];
	for (int i = 0; i < this.Pc.length; i++) {
		a_i[i] = 0.45724 * Math.pow(PengRobinsonCalcs.R_Gas * this.Tc[i], 2.) / (1000 * this.Pc[i]);
	}
	return a_i;
}
//Comptues a'(T) for cp calc
protected double[] aTPrime(){
	double[] temp = new double[Tc.length];
	double[] ac_i = a_c_iCalc();
	for(int i =0; i< Tc.length; i++){
		temp[i]=calcKapp(i)*ac_i[i]*((calcKapp(i)/this.Tc[i])-(1+calcKapp(i))/Math.sqrt(this.T*this.Tc[i]));
	}
	return temp;
}
//computes a''(T) for cp calc
protected double[] aTDoublePrime(){
	double[] temp = new double[Tc.length];
	double[] ac_i = a_c_iCalc();
	for(int i =0; i< Tc.length; i++){
		temp[i]=calcKapp(i)*ac_i[i]*(1+calcKapp(i))/(2*Math.sqrt(Math.pow(this.T,3)*this.Tc[i]));
	}
	return temp;
}

}
