package cases;

import solver.Solver;
import chemicals.Chemical;
import flashvessel.Vessel;
import fluidpackages.FluidPackage;
import rootproblems.EnergyBalance;

public abstract class Case {
	//Variables
	protected double T;
	protected double P;
	protected Solver solver;
	protected Vessel vessel;
	protected EnergyBalance eb;

	/* Constructor for case parent objects
	 * @param  T = temperature outlet/inlet, P = pressure, flowrate is inlet flow rate, chemChomp is composition, ideality is fluidpackage, chemicals array of chemicals holding properties. 
	 */
	public Case(double T, double P, double flowRate, double[] chemComp, FluidPackage fp, int ideality, Chemical[] chemicals) {
		//double[] flowRates = new double[3];
		this.T = T;
		this.P = P;
		this.solver = new Solver(T,P, flowRate,chemComp,chemicals,fp,ideality);
	}
	/*
	 * @return vessel that has been solved by one of the cases
	 */
	public Vessel getVessel() {
		return new Vessel(this.solver.returnVessel());
	}

}
