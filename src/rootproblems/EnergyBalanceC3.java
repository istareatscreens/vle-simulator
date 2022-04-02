package rootproblems;

import chemicals.Chemical;
import chemicals.kijValues;
import main.GenericMethods;

//Energy Balance used for case 3 solves for Feed temperature (should be higher than vessel temperature)
public class EnergyBalanceC3 extends EnergyBalance {

	private double vTemp;
	private double[][] gasComp;
	private double[][] liqComp;
	//constructor for energy balance case 3 used to calculate ideal Cp values
	public EnergyBalanceC3(Chemical[] chemicals, double[][] liqComp, double[][] gasComp, double[] flowRates, double vTemp) {
		super(chemicals, flowRates);
		this.vTemp = vTemp;
		this.gasComp = GenericMethods.clone2Ddoublearray(gasComp);
		this.liqComp = GenericMethods.clone2Ddoublearray(liqComp);
	}
	//Constructor for Energybalance case 3 used to calculate Real cp values
	public EnergyBalanceC3(Chemical[] chemicals, double P, double[] flowRates, double[] Tc, double[] Pc, double[] omega, kijValues kijGen,double[][] liqComp, double[][] gasComp, double vTemp) {
		super(chemicals,  P,  flowRates,  Tc,  Pc, omega,  kijGen);
		this.vTemp = vTemp;
		this.gasComp = GenericMethods.clone2Ddoublearray(gasComp);
		this.liqComp = GenericMethods.clone2Ddoublearray(liqComp);
	}
	//Using ar oot finding method such as bisection or ridders program solves for T feed, vTemp is the vessel temperature
	public double iterateForTorGetQ(double T) {
		return -hVCalc(this.vTemp, gasComp)*super.V*(this.vTemp-298)-hLCalc(this.vTemp, liqComp)*super.L*(this.vTemp-298)+(hFCalc(T,liqComp)*super.F)*(T-298);
	}

}
