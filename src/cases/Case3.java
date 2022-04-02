package cases;

import chemicals.Chemical;
import chemicals.kijValues;
import fluidpackages.FluidPackage;
import numericalmethods.RootFindingMethod;
import rootproblems.EnergyBalanceC3;
import solver.Solver;

public class Case3 extends CaseAdiabatic {
	/*
	 *Constructor for case 3 for use with ideal Cp  solves for Feed T
	 * @param  T is temperature, P is pressure, flowRate is inlet flowrate, chemComp is composition, fp is Fluidpackage used, ideality is case, chemicals is array of chemicals , rfm is ridders or bissection, k_ijgen calculates binary interaction coefficents
	 */
	public Case3(double T, double P, double flowRate, double[] chemComp, FluidPackage fp, int ideality, Chemical[] chemicals, RootFindingMethod rfm) {
		super(T, P, flowRate,chemComp, fp, ideality, chemicals, rfm);
		double TGuess= Math.log(-1);
		//saves upper bound
		double upT = T;
		//get flowrates from solver with the vapour, liquid, vapour
		double[] flowRates = solver.returnVessel().getFlowRates().clone();
		//Makes Energy balance 
		this.eb = new EnergyBalanceC3(chemicals, solver.returnVessel().getLiquidComposition(),
				solver.returnVessel().getGasCompositions(), flowRates, T);
		//sets up loop since multiple roots exist for energy balance for feed T therefore assumes lowest root is the right answer
		double count=0;
		do{
			//increases upper bound of root finding method
			upT+=5;
			//Sets bounds of root finding method
			rfm.setBounds(T,upT);
			//computers the root
			TGuess =rfm.findRoot(10e-10, eb);
			count++;
			if(count>100000) {
				break;
			}
			//exits loop when energybalance = 0
		}while(Math.abs(eb.eq(TGuess))>0.001);
		//Sets solver temperature
		this.solver.setFeedT(TGuess);
	}

	/*
	 *Constructor for case 3 for use with Real Cp solves for Feed T
	 * @param  T is temperature, P is pressure, flowRate is inlet flowrate, chemComp is composition, fp is Fluidpackage used, ideality is case, chemicals is array of chemicals , rfm is ridders or bissection, k_ijgen calculates binary interaction coefficents
	 */
		public Case3(double T, double P, double flowRate, double[] chemComp, FluidPackage fp, int ideality, Chemical[] chemicals, RootFindingMethod rfm, double[] Tc, double[] Pc, double[] omega, kijValues kijGen) {
		super(T, P, flowRate,chemComp, fp, ideality, chemicals, rfm);
		double TGuess= Math.log(-1);
		double upT = T;
		double[] flowRates = solver.returnVessel().getFlowRates().clone();
		//DOES SAME as upper constructor just using real Cps
		this.eb = new EnergyBalanceC3(chemicals, P, flowRates, Tc, Pc, omega, kijGen, solver.returnVessel().getLiquidComposition(),solver.returnVessel().getGasCompositions(), T);
		double count=0;
		do{
			count++;
			upT+=5;
			rfm.setBounds(T,upT);
			TGuess =rfm.findRoot(10e-10, eb);
			if(count>100000) {
				break;
			}
		}while(Math.abs(eb.eq(TGuess))>0.001);
		
		this.solver.setFeedT(TGuess);
	}
	
}
