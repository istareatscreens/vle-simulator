package fluidpackages;

import bubblepointdewpoint.RealBubblePoint;
import bubblepointdewpoint.RealDewPoint;
import chemicals.Chemical;
import chemicals.kijValues;
import main.GenericMethods;
import rootproblems.CubicEquationOfState;
import solver.Solver;
//Peng robinson fluid package, uses wilson to guess an initial guess for K values then iterates from that 
public class PengRobinson extends PengRobinsonCalcs implements FluidPackage {

	// Input Variables
	//
	// Calculated Values
	private double[] K;
	private Solver solver;
	private RealDewPoint rdp;
	private RealBubblePoint rbp;

	// Constructor
	public PengRobinson(double T, double P, double[] Tc, double[] Pc, double[] omega, double flowRate, double[] Z, Chemical[] chemicals,kijValues kijGen, double[] Tb, boolean[] isGas) {
		super(T, P, Tc, Pc, omega, kijGen, true);
		//Creates fluid package to make initial guess of K and gas and liquid fractions
		FluidPackage fpW = new WilsonsCorrelation(T, P, Tc, Pc, omega, Z, Tb, isGas);
			//creates solver to iterate for K values and solve for x and y values of outlet
			this.solver = new Solver(T, P, flowRate, Z, chemicals, fpW, 1);
			//sets up package for calculating dew and bubble point for peng robinson
		this.rdp = new RealDewPoint(T, solver.getDewPointP(), Tc, Pc, omega, solver.getKAtP(solver.getDewPointP()),
				solver.returnVessel().getMoleCompositions()[0], fpW, chemicals, kijGen);
		this.rbp = new RealBubblePoint(T, solver.getBubblePointP(), Tc, Pc, omega, solver.getKAtP(solver.getBubblePointP()),
				solver.returnVessel().getMoleCompositions()[0],fpW, chemicals, kijGen);
		//Acquires initial K values generated from wilson correlation
		this.K = solver.getK();
	}
//peng robinson copy constructor
	public PengRobinson(PengRobinson pr) {
		super(pr.T, pr.P, pr.Tc, pr.Pc, pr.omega, pr.kijGen, true);
		this.solver = new Solver(pr.solver);
		this.rdp = new RealDewPoint(pr.rdp);
		this.rbp = new RealBubblePoint(pr.rbp);
		this.K = pr.K.clone();
	}

	// Methods
	
	//Calculates K value for peng robinson
	public double[] getK() {
		iterateK();
		return this.K;
	}

	// Stream 0, 1 Liquid, 2 Vapour, iterates for K until fugacities are roughly equal
	private void iterateK() {
		double[] phiV = new double[this.Pc.length];
		double[] phiL = new double[this.Pc.length];
		double[] Z = new double[2];
		double check;
		// first run WilsonMethod establishing object
		CubicEquationOfState EOS = new CubicEquationOfState(
		calcEOSCoefs(this.solver.returnVessel().getGasCompositions()[2]));
		do {
			//compute Z values from Equation of state
			Z = getZ(EOS);
			//Solve for psis to solve for K ratio
			phiV = getPhi(this.solver.returnVessel().getGasCompositions()[2], Z[0],
					calcEOSCoefs(this.solver.returnVessel().getGasCompositions()[2]));
			phiL = getPhi(this.solver.returnVessel().getLiquidComposition()[1], Z[1],
					calcEOSCoefs(this.solver.returnVessel().getLiquidComposition()[1]));
			//Computes new K values for all spcies
			for (int i = 0; i < this.Pc.length; i++) {
				this.K[i] = phiL[i] / phiV[i];
			}
			//reiterate solver with newly calculated K values
			solver.iterateK(this.K);
			//Checks for convergene
			check = checkCriteria(phiV, phiL);
			//exits loop if fugacities are roughly equal
		} while (check > 10e-10);

	}
	//checks fugacities
	protected double checkCriteria(double[] psiV, double[] psiL) {
		double temp = 0;
		for (int i = 0; i < this.Pc.length; i++) {
			temp += Math.pow(1 - (psiL[i] * this.solver.returnVessel().getLiquidComposition()[1][i] * this.P)
					/ (psiV[i] * this.solver.returnVessel().getGasCompositions()[2][i] * this.P), 2.);
		}
		return temp;
	}
//Solves cubic equation of state using cardanios method with A and B coffecients calculted, wit halceoSCoefs method
	protected double[] getZ(CubicEquationOfState EOS) {
		double[] temp = new double[2];
		// Vapour
		EOS.setCoef((calcEOSCoefs(this.solver.returnVessel().getGasCompositions()[2])));
		temp[0] = GenericMethods.findMaxDoubleArray(EOS.getRoots());
		// Liquid
		EOS.setCoef((calcEOSCoefs(this.solver.returnVessel().getLiquidComposition()[1])));
		temp[1] = GenericMethods.findMinDoubleArray(EOS.getRoots());
		return temp;
	}
	//Copy constructor to clone Peng Robinson
	public FluidPackage copyConstructor(FluidPackage fp) {
		return new PengRobinson((PengRobinson) fp);
	}
//Accessors gets the Dew point from peng robinson
	public double getBubbleP() {
		return this.rbp.getBubblePressure();
	}
//Accssor gets the Bubble point from peng robinson
	public double getDewP() {
		return this.rdp.getDewP();
	}
//Sets Pressure
	public void setP(double P){
		this.P = P;
	}
//gets pressure
	public double getP(){
		return this.P;
	}
//sets temperature and attempts to reset fluid package to solve for new T
public void setT(double T){
		super.setT(T);
	}

}