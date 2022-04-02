package bubblepointdewpoint;

import chemicals.Chemical;
import chemicals.kijValues;
import fluidpackages.FluidPackage;
import fluidpackages.PengRobinsonCalcs;
import fluidpackages.WilsonsCorrelation;
import main.GenericMethods;
import rootproblems.CubicEquationOfState;
//compute Real Dew Point with Peng Robinson by inheriting Peng Robinson Calculations class
public class RealDewPoint extends PengRobinsonCalcs {

	private double[] X;
	private double[] Y;
	private double[] K;
	private WilsonsCorrelation fp;
	private double initialP;
//Constructor for Real dew point requries willsons correlation for initial guess
	public RealDewPoint(double T, double P, double[] Tc, double[] Pc, double[] omega, double[] K, double[] Z,
			FluidPackage fp, Chemical[] chemicals, kijValues kijGen) {
		super(T, P, Tc, Pc, omega, kijGen, true);
		this.K = K.clone();
		this.Y = Z.clone();
		this.fp = (WilsonsCorrelation) fp.copyConstructor(fp);
		this.X = new double[this.Pc.length];
		for (int i = 0; i < this.Pc.length; i++) {
			this.X[i] = Y[i] / K[i];
		}
		this.initialP = P;
	}
//Copy constructor for Real dew point
	public RealDewPoint(RealDewPoint rdp) {
		super(rdp.T, rdp.P, rdp.Tc.clone(), rdp.Pc.clone(), rdp.omega.clone(), rdp.kijGen, true);
		this.K = rdp.K.clone();
		this.Y = rdp.Y.clone();
		this.X = rdp.X.clone();
		this.fp = (WilsonsCorrelation) rdp.fp.copyConstructor(rdp.fp);
		this.initialP = rdp.initialP;
	}
//Iterates Dew point until fugacities are equal or X = 1
	private void iterateDewP() {
		double[] phiV = new double[this.Pc.length];
		double[] phiL = new double[this.Pc.length];
		double[] Z = new double[2];
		double check;
		double count = 0;
		// first run WilsonMethod establishing object
		CubicEquationOfState EOS = new CubicEquationOfState(calcEOSCoefs(this.Y));
		do {
			Z = getZ(EOS);
			phiV = getPhi(this.Y, Z[0], calcEOSCoefs(this.Y));
			phiL = getPhi(this.X, Z[1], calcEOSCoefs(this.X));
			check = checkCriteria(phiV, phiL);
			if (check < 10e-10) {
				break;
			} else if (Double.isNaN(check)) {
				break;
			} else if (count > 1000) {
				break;
			}
			for (int i = 0; i < this.Pc.length; i++) {
				this.K[i] = phiL[i] / phiV[i];
			}
			this.P = calcNewPressure(phiV, phiL);
			calcX();

		} while (true);
	}
//Checks criteria to see if fugacities are equal or X = 1
	public double checkCriteria(double[] psiV, double[] psiL) {
		double temp = 0;
		for (int i = 0; i < this.Pc.length; i++) {
			temp += Math.pow(1 - (psiL[i] * this.X[i] * this.P) / (psiV[i] * this.Y[i] * this.P), 2.);
		}
		return temp;
	}
//Gets solution to cubic equation of state for both Vapour and Liqud
	protected double[] getZ(CubicEquationOfState EOS) {
		double[] temp = new double[2];
		// Vapour
		EOS.setCoef((calcEOSCoefs(this.Y)));
		temp[0] = GenericMethods.findMaxDoubleArray(EOS.getRoots());
		// Liquid
		EOS.setCoef((calcEOSCoefs(this.X)));
		temp[1] = GenericMethods.findMinDoubleArray(EOS.getRoots());
		return temp;
	}
//calculates New Dew Point Pressure
	private double calcNewPressure(double[] psiV, double[] psiL) {
		double temp = 0;
		for (int i = 0; i < this.Pc.length; i++) {
			temp += ((psiV[i] * this.Y[i] * this.P) / psiL[i]);
		}
		return temp;
	}
//Calculates New X For iteration
	private void calcX() {
		for (int i = 0; i < this.Pc.length; i++) {
			this.X[i] = this.Y[i] / this.K[i];
		}
	}
//THis object is only for computing Real Dew Point thus exits the program
	public double[] getK() {
		System.out.println("This Method is For Dew Point Only");
		System.exit(0);
		return null;
	}
//Gets Dew Point Pressure from peng robinson
	public double getDewP() {
		iterateDewP();
		return this.P;
	}
//Sets Temperature of dew Point, not really used
	public void setT() throws Exception {
		super.setT(T);
		this.fp.setT(T);
	}
}
