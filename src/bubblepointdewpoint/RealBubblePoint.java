package bubblepointdewpoint;

import chemicals.Chemical;
import chemicals.kijValues;
import fluidpackages.FluidPackage;
import fluidpackages.PengRobinsonCalcs;
import fluidpackages.WilsonsCorrelation;
import main.GenericMethods;
import rootproblems.CubicEquationOfState;
//Calculates Peng Robinson child of PengRobinson Calcs
public class RealBubblePoint extends PengRobinsonCalcs {

	//Instance Variables
	private double[] K;
	private double[] X;
	private double[] Y;
	private WilsonsCorrelation fp;
//Copy constructor requires wilson correlation to estimate bubble P and dew P
	public RealBubblePoint(double T, double P, double[] Tc, double[] Pc, double[] omega, double[] K, double[] Z,
			FluidPackage fp, Chemical[] chemicals, kijValues kijGen) {
		super(T, P, Tc, Pc, omega,kijGen, true);
		this.K = K.clone();
		this.X = Z.clone();
		this.fp = (WilsonsCorrelation)fp.copyConstructor(fp);
		this.Y = new double[this.Pc.length];
		for (int i = 0; i < this.Pc.length; i++) {
			this.Y[i] = K[i] * X[i];
		}
	}
	//Copy constructor requires wilson correlation to estimate bubble P and dew P
	public RealBubblePoint(RealBubblePoint rbp) {
		super(rbp.T, rbp.P, rbp.Tc.clone(), rbp.Pc.clone(), rbp.omega.clone(), rbp.kijGen, true);
		this.K = rbp.K.clone();
		this.X = rbp.X.clone();
		this.Y = rbp.Y.clone();
		this.fp = (WilsonsCorrelation) rbp.fp.copyConstructor(rbp.fp);
	}
	//iterateBubbleP from an initial guess
	private void iterateBubbleP() {
		double[] phiV = new double[this.Pc.length];
		double[] phiL = new double[this.Pc.length];
		double[] Z = new double[2];
		double check;
		double count=0;
		// first run WilsonMethod establishing object
		CubicEquationOfState EOS = new CubicEquationOfState(calcEOSCoefs(this.Y));
		do {
			count++;
			Z = getZ(EOS);
			phiV = getPhi(this.Y, Z[0], calcEOSCoefs(this.Y));
			phiL = getPhi(this.X, Z[1], calcEOSCoefs(this.X));
			check = checkCriteria(phiV, phiL);
			if (check < 10e-10) {
				break;
			} else if (Double.isNaN(check)) {
				break;
			}else if(count>1000){
				break;
			}
			for (int i = 0; i < this.Pc.length; i++) {
				this.K[i] = phiL[i] / phiV[i];
			}
			this.P = calcNewPressure(phiV, phiL);
			calcY();
		} while (true);
	}
//Gets cubic equation of state meaning full roots for solving Peng Robinson
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
//calculates new pressure  using PsiV and PsiL in unison
	private double calcNewPressure(double[] psiV, double[] psiL) {
		double temp = 0;
		for (int i = 0; i < this.Pc.length; i++) {
			temp += ((psiL[i] * this.X[i] * this.P) / psiV[i]);
		}
		return temp;
	}
//Calculates new Y values for bubble
	private void calcY() {
		for (int i = 0; i < this.Pc.length; i++) {
			this.Y[i] = this.K[i] * this.X[i];
		}
	}
//Checks to see if fugacities are equal (sum of Y = 1);
	public double checkCriteria(double[] psiV, double[] psiL) {
		double temp = 0;
		for (int i = 0; i < this.Pc.length; i++) {
			temp += Math.pow(1 - (psiL[i] * this.X[i] * this.P) / (psiV[i] * this.Y[i] * this.P), 2.);
		}
		return temp;
	}
//Gets K values this is only for bubble point pressure so it will return an error and exit program
	public double[] getK() {
		System.out.println("This Method is for Bubble Point Only");
		System.exit(0);
		return null;
	}
//gets Bubble Point Pressure
	public double getBubblePressure() {
		iterateBubbleP();
		return this.P;
	}
	//Sets Temeprature not really used
	public void setT() throws Exception{
		super.setT(T);
		this.fp.setT(T);
	}

}
