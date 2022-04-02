package cases;

import chemicals.Chemical;
import chemicals.kijValues;
import fluidpackages.FluidPackage;
import fluidpackages.PengRobinson;
import fluidpackages.RouletsLaw;
import fluidpackages.WilsonsCorrelation;
import numericalmethods.RootFindingMethod;
import rootproblems.EnergyBalanceC2;
import rootproblems.EnergyBalanceC3;
import rootproblems.RootProblem;
import solver.Solver;

public class Case2 extends CaseAdiabatic {

	private double P;
	private Chemical chemicals[];
	private int ideality;
	private double[] inletComp;
	private double[] flowRates;
	private double[] Tc;
	private double[] Pc;
	private double[] omega;
	
	/*
	 *Constructor for case 2 for use with ideal Cp 
	 * @param  T is feed temperature, P is pressure, flowRate is inlet flowrate, chemComp is composition, fp is Fluidpackage used, ideality is case, chemicals is array of chemicals , rfm is ridders or bissection, k_ijgen calculates binary interaction coefficents
	 */
	public Case2(double T, double P, double flowRate, double[] chemComp, FluidPackage fp, int ideality,
			Chemical[] chemicals, RootFindingMethod rfm, kijValues k_ijGen,double[] Tb, boolean[] isGas) {
		super(T, P, flowRate, chemComp, fp, ideality, chemicals, rfm);
		//Pressure
		this.P=P;
		//Ideality
		this.ideality = ideality;
		//Energy Balance created with everything needed to make a new solver and fluid package
		EnergyBalanceC2 eb = new EnergyBalanceC2(chemicals, super.solver.returnVessel().getFlowRates(), T, chemComp,  ideality,  k_ijGen,  P, isGas, Tb);
		//root finding method solves energy balance
		rfm.findRoot(0.01,eb);
		//gets solved solver from rootfinding method
		super.solver = eb.getSolvedSolver();
		//sets Feed temp to user input feed temp
		solver.setFeedT(T);
		//sets Q to 0 (adiabatic vessel);
		solver.setQinVessel(0);	
	}
		
	/*
	 *Constructor for case 2 for use with Real Cp 
	 * @param  T is feed temperature, P is pressure, flowRate is inlet flowrate, chemComp is composition, fp is Fluidpackage used, ideality is case, chemicals is array of chemicals , rfm is ridders or bissection, k_ijgen calculates binary interaction coefficents
	 */
	public Case2(double T,  double P, double flowRate, double[] chemComp, FluidPackage fp, int ideality,
			Chemical[] chemicals, RootFindingMethod rfm, kijValues k_ijGen, double[] Pc, double[] Tc, double[] omega,double[] Tb, boolean[] isGas) {
		super(T, P, flowRate, chemComp, fp, ideality, chemicals, rfm);
		//saves pressure
		this.P=P;
		//saves ideality
		this.ideality = ideality;
		//Energy Balane is made to be solved
		EnergyBalanceC2 eb = new EnergyBalanceC2(T, chemicals, P, super.solver.returnVessel().getFlowRates(), Tc, Pc, omega, ideality, k_ijGen, chemComp, isGas, Tb);
		//Solves for T in energy balance
		rfm.findRoot(0.01,eb);
		//gets solver from energy balanced because its the one that has the correct Vessel T
		super.solver = eb.getSolvedSolver();
		//Saves Feed temperature to vessel so it can be retrieved later
		solver.setFeedT(T);
		//Saves Q value to 0 in vessel adiabatic
		solver.setQinVessel(0);	
	}
	

}
