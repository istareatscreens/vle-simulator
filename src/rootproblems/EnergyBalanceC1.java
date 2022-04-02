package rootproblems;

import chemicals.Chemical;
import chemicals.kijValues;
import main.GenericMethods;
import solver.Solver;
//Solves case one, Finds Q in isothermal reactor
public class EnergyBalanceC1 extends EnergyBalance {

	
	private double[][] gasComp;
	private double[][] liqComp;
	//Constructor for ideal cp values
	public EnergyBalanceC1(Chemical[] chemicals, double[][] liqComp, double[][] gasComp, double[] flowRates) {
		super(chemicals, flowRates);
		this.gasComp = GenericMethods.clone2Ddoublearray(gasComp);
		this.liqComp = GenericMethods.clone2Ddoublearray(liqComp);
	}
	//Constructor for Real Cp values
	public EnergyBalanceC1(Chemical[] chemicals, double P, double[] flowRates, double[] Tc, double[] Pc, double[] omega, kijValues kijGen,double[][] liqComp, double[][] gasComp) {
		super(chemicals,  P,  flowRates,  Tc,  Pc, omega,  kijGen);
		this.gasComp = GenericMethods.clone2Ddoublearray(gasComp);
		this.liqComp = GenericMethods.clone2Ddoublearray(liqComp);
	}
	//iterates for Q, T is the vessel temperature simple algebric equation Q = Enthalpy balance
	public double iterateForTorGetQ(double T){
		return -hFCalc(T,this.liqComp)*super.F*(T-298)+hVCalc(T,this.gasComp)*super.V*(T-298)+hLCalc(T, this.liqComp)*super.L*(T-298);
	}
	
}
