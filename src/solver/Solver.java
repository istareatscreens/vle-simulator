package solver;

import chemicals.Chemical;
import chemicals.IdealChemical;
import chemicals.RealChemical;
import chemicals.Structure;
import equations.GeneralEquations;
import flashvessel.Vessel;
import fluidpackages.FluidPackage;
import main.GenericMethods;
import numericalmethods.BisectionMethod;
import numericalmethods.RiddersMethod;
import numericalmethods.RootFindingMethod;
import rootproblems.RachfordRice;
import rootproblems.RootProblem;
//Solves the flash vessel problem, stores all inlet and outlet properties into vessel which can be returned to acquire solution
public class Solver {

	protected double psi;
	protected Vessel vessel;
	protected Chemical[] chemicals;
	protected FluidPackage fp;
	protected double[] K;
	protected int ideality;
//Constructor for solid takes in temperature, pressure, inlet flowrate, inlet mole composition, array of chemicals (stores proeprties of chemicals), fluid package (solves for K values)
	public Solver(double temperature, double pressure, double flowRate, double[] inletMoleComp, Chemical[] chemicals,
			FluidPackage fp, int ideality) {
		this.chemicals = new Chemical[chemicals.length];
		//saves fluidpackage (0=ideal, 1 = wilson, 2 = real)
		this.ideality = ideality;
		//Deep copies the chemical class for security reasons
		for (int i = 0; i < chemicals.length; i++) {
			if (ideality == 0) {
				this.chemicals[i] = new IdealChemical((convertToIdealChemical(chemicals[i])));
			} else if (ideality == 1) {
				this.chemicals[i] = new RealChemical(convertToRealChemical(chemicals[i]));
			}else if (ideality == 2) {
				this.chemicals[i] = new Structure((Structure) chemicals[i]);
			}
		}
		//makes a new vessel for solution to be solved in
		this.vessel = new Vessel(temperature, pressure, flowRate, inletMoleComp, getNames());
		//deep copies fluid package
		this.fp = fp.copyConstructor(fp);
		//Chekcs if system is in in sub cooled liquid or super heated vapour state throug bubble P calcs
		if (checkConvergence()) {
			solveVessel();
		}
	}

//copy constructor for solver
	public Solver(Solver s) {
		this.ideality = s.ideality;
		this.psi = s.psi;
		this.vessel = new Vessel(s.vessel);
		this.chemicals = new Chemical[s.chemicals.length];
		for (int i = 0; i < s.chemicals.length; i++) {
			switch (ideality) {
			case 0:
				this.chemicals[i] = new IdealChemical((convertToIdealChemical(s.chemicals[i])));
				break;
			case 1:
				this.chemicals[i] = new RealChemical(convertToRealChemical(s.chemicals[i]));
				break;
			case 2:
				this.chemicals[i] = new Structure((Structure) s.chemicals[i]);
				break;
			}
		}
		this.fp = s.fp.copyConstructor(s.fp);
		if (s.K != null) {
			this.K = s.K.clone();
		}
	}

	//up casts Chemical to ideal chemical
	public IdealChemical convertToIdealChemical(Chemical chemical) {
		return (IdealChemical) chemical;
	}
	//up casts Chemical to real chemical
	public RealChemical convertToRealChemical(Chemical chemical) {
		return (RealChemical) chemical;
	}

//Checks to see if system is in sub cooled liquid or super heated vapour state by computing bubble P and dew P
//If vessel pressure is greater than bubble point then inlet comp gets transfered to outlet liquid stream
//if vessel pressure is less than dew point than inlet comp gets transfered to outlet vapour
	protected boolean checkConvergence() {
		double vP = this.vessel.getPressure();
		double dP = this.getDewPointP();
		double bP = this.getBubblePointP();
		//If divergence occurs set dew point and bubble point negative so that it can be caught at program end
		if(dP > bP){
			dP = -1;
			bP = -1;
			//checks dewp point (dp) pressure in resepct to vessel pressure (vp)
		}else if (vP < dP) {
			// System.out.println("\nMixture is super-heated vapour");
			//sets vessel exit compositions and solver ends since false is returned
			this.vessel.setState(2);
			this.vessel.setGasComposition(2, this.vessel.getMoleCompositions()[0]);
			this.vessel.setFlowRates(2, this.vessel.getFlowRates()[0]);
			this.vessel.setFlowRates(1, 0);
			return false;
			//checks dewp point (bp) pressure in resepct to vessel pressure (dp)
		} else if (vP > bP) {
			// System.out.println("\nMixture is sub-cooled liquid");
			//sets vessel exit compositions and solver ends since false is returned
			this.vessel.setState(0);
			this.vessel.setLiquidComposition(1, this.vessel.getMoleCompositions()[0]);
			this.vessel.setFlowRates(1, this.vessel.getFlowRates()[0]);
			this.vessel.setFlowRates(2, 0);
			return false;
		}
		this.vessel.setState(1);
		return true;
	}
	
	//Solves for vessel if convergence method returns true, checks for error when running program
	public void solveVessel(){
		try {
			//gets Vapour equilibrium K value from fluid package
			solveForK();
			//gets Psi value for solving for compositions and Vessel flow rates (outlet)
			solveForPsi();
			//Solves for liquid outlet composition
			solveForLiquidExitComposition();
			//solves for vapour outlet composition
			solveForVapourExitComposition();
			//solves for vapour flow rate
			solveForVapourFlowRate();
			//solves for liquid flow rate
			solveForLiquidFlowRate();
		} catch (Exception e) {
			solveForK();
			//if system cannot solve it ends with this error message, worst case
			if ((checkConvergence())) {
				System.out.println("Solver Error System Does Not Converge in P_Dew < P < P_Bubble, Quiting Program");
				System.exit(0);
			}
		}
	}
//solves for k value using fluidpackages getK method
	protected void solveForK() {
		this.K = this.fp.getK();
	}
//Solves for Psi value for L and V calculations using rathford rice root finding method
	protected void solveForPsi() {
		RootProblem rp = new RachfordRice(this.vessel.getMoleCompositions()[0], this.K);
		RootFindingMethod rf = new RiddersMethod(0.001, 0.999);
		// RootFindingMethod rf = new BisectionMethod(0,1);
			this.psi = rf.findRoot(0.001, rp);
	}
//Solves for liquid exit composition by using  required formula
	protected void solveForLiquidExitComposition() {
			this.vessel.setLiquidComposition(1, GeneralEquations.calculateExitLiquidComposition(this.psi,
					this.vessel.getMoleCompositions()[0], this.K));

	}
	//Solves for vapour  exit composition by using  required formula
	protected void solveForVapourExitComposition() {
		this.vessel.setGasComposition(2, GeneralEquations.calculateExitVapourComposition(this.psi,
				this.vessel.getMoleCompositions()[0], this.K));
	}
//Returns String of names of chemicals in the system
	protected String[] getNames() {
		String[] temp = new String[this.chemicals.length];
		for (int i = 0; i < this.chemicals.length; i++) {
			temp[i] = this.chemicals[i].getName();
		}
		return temp;
	}

//Soles for the vapour out flow rate
	protected void solveForVapourFlowRate() {
		if(Double.isNaN(this.K[0])){
			this.vessel.setFlowRates(2, -1);
		}else{
		this.vessel.setFlowRates(2, this.vessel.getFlowRates()[0] * (this.psi));
		}
	}

//Solves for the liquid out flowrate
	protected void solveForLiquidFlowRate() {
		if(Double.isNaN(this.K[0])){
			this.vessel.setFlowRates(1, -1);
		}else{
		this.vessel.setFlowRates(1, this.vessel.getFlowRates()[0] - this.vessel.getFlowRates()[2]);
		}
	}
//changes the fluid package in the solver
	public void setFluidPackage(FluidPackage nfp) {
		this.fp = nfp.copyConstructor(nfp);
	}
//Sets the Vessel Temperature useful for case 2
	public void setVesselT(double T) {
		this.vessel.setTemperature(T);
		//solveVessel();
	}
//Returns vessel temperature
	public Vessel returnVessel() {
		// solveVessel();
		return new Vessel(this.vessel);
	}
//Used by peng robinson and pengrobins bubble point and dew point calculations to acquire new x and y values, this is used until
//fugacities are equal
	public void iterateK(double[] K) {
		this.K = K.clone();
		try {
			solveForPsi();
			solveForLiquidExitComposition();
			solveForVapourExitComposition();
			solveForVapourFlowRate();
			solveForLiquidFlowRate();
		} catch (Exception e) {
			if (checkConvergence()) {
				System.out.println("Error in solver: System does not converge at Dew Point Pressure < P < Bubble Point Pressure");
				System.exit(0);
			}
		}
	}

	//Acquires  kvalue from fluid package at specific temperature
	public double[] getKAtP(double P) {
		double Pold = this.vessel.getPressure();
		double[] newK = new double[this.chemicals.length];
		Pold = fp.getP();
		fp.setP(P);
		newK = fp.getK();
		fp.setP(Pold);
		return newK;
	}
//Gets dew point presssure from fluid package loaded into solver
	public double getDewPointP() {
		double dp = this.fp.getDewP();
		this.vessel.setDPP(dp);
		return dp;
	}
//Ges bubble point from fluid package loaded into solver
	public double getBubblePointP() {
		double bp = this.fp.getBubbleP();
		this.vessel.setBPP(bp);
		return bp;
	}
//Get the array of double values of K useful for iteration
	public double[] getK() {
		solveForK();
		return K.clone();
	}
//Sets vessel Q, 0 for case 2 and 3, solved for in case 1 (changes the Q in the vessel object
	public double getVesselT() {
		return this.vessel.getTemperature();
	}
//Set Q value present in vessel useful for case 1
	public void setQinVessel(double Q) {
		this.vessel.setQ(Q);
	}
//Sets feed temperature of vessel useful for all cases
	public void setFeedT(double FeedT) {
		this.vessel.setFeedT(FeedT);
	}

}