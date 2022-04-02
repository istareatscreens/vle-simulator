package cases;

import chemicals.Chemical;
import chemicals.kijValues;
import solver.Solver;
import fluidpackages.FluidPackage;
import rootproblems.EnergyBalanceC1;

public class Case1 extends Case {
	/*
	 *Constructor for case 1 for use with ideal Cp 
	 * @param  T is temperature, P is pressure, flowRate is inlet flowrate, chemComp is composition, fp is Fluidpackage used, ideality is case, chemicals is array of chemicals  , rfm is ridders or bissection, k_ijgen calculates binary interaction coefficents       
	 */
	public Case1(double T, double P, double flowRate, double[] chemComp, FluidPackage fp, int ideality, Chemical[] chemicals) {
		super(T, P, flowRate,chemComp, fp, ideality, chemicals);	
		double[] flowRates = solver.returnVessel().getFlowRates().clone();
		this.eb = new EnergyBalanceC1(chemicals, solver.returnVessel().getLiquidComposition(),
				solver.returnVessel().getGasCompositions(), flowRates);
		this.solver.setQinVessel(eb.iterateForTorGetQ(T));
		}


	/*
	 *Constructor for case 1 for use with Real Cp using Peng robinson equation of state
	 * @param  T is temperature, P is pressure, flowRate is inlet flowrate, chemComp is composition, fp is Fluidpackage used, ideality is case, chemicals is array of chemicals         
	 */
	public Case1(double T, double P, double flowRate, double[] chemComp, FluidPackage fp, int ideality, Chemical[] chemicals, double[] Tc, double[] Pc, double[] omega, kijValues kijGen) {
		super(T, P, flowRate,chemComp, fp, ideality, chemicals);	
		//gets flowrates from solved vessel in solver
		double[] flowRates = solver.returnVessel().getFlowRates().clone();
		//creates energy balance
		this.eb = new EnergyBalanceC1(chemicals, P, flowRates, Tc, Pc, omega, kijGen,solver.returnVessel().getLiquidComposition(),solver.returnVessel().getGasCompositions());
		//solves for Q
		this.solver.setQinVessel(eb.iterateForTorGetQ(T));	
		}

}
