package rootproblems;

import chemicals.Chemical;
import chemicals.kijValues;
import main.GenericMethods;
//Energy balance Parent Objects shares common methods with other energy balance classes specifically enthalpy calcs
public abstract class EnergyBalance implements RootProblem {
//instance variables
	protected HeatCapacityCalculator hCC;
	protected double[][] cpGasConst;
	protected double[][] cpLiqConst;
	protected double h_L;
	protected double h_V;
	protected double h_F;
	protected double F;
	protected double L;
	protected double V;
	protected double Q;
	protected static final double R_Gas = 8.3144598;

//constructor for Ideal Cp Calcs
	public EnergyBalance(Chemical[] chemicals, double[] flowRates) {
		double[][] cpGasConst=new double[chemicals.length][4];
		double[][] cpLiqConst=new double[chemicals.length][4];
		
		for (int i = 0; i < chemicals.length; i++) {
			cpGasConst[i] = chemicals[i].getCpConstGas().clone();
			cpLiqConst[i] = chemicals[i].getCpConstLiq().clone();
		}
		this.F = flowRates[0];
		this.L = flowRates[1];
		this.V = flowRates[2];
		//hCC calculates ideal in this case
		this.hCC = new HeatCapacityCalculator(cpGasConst,cpLiqConst);
	}
	//Constructor for Real Cp Calcs
	public EnergyBalance(Chemical[] chemicals, double P, double[] flowRates, double[] Tc, double[] Pc, double[] omega, kijValues kijGen) {
		double[][] cpGasConst=new double[chemicals.length][4];
		double[][] cpLiqConst=new double[chemicals.length][4];
		for (int i = 0; i < chemicals.length; i++) {
			cpGasConst[i] = chemicals[i].getCpConstGas().clone();
			cpLiqConst[i] = chemicals[i].getCpConstLiq().clone();
		}
		//saves flow rates for use in energy balance
		this.F = flowRates[0];
		this.L = flowRates[1];
		this.V = flowRates[2];
		//hCC calculates Cp real in this case
		this.hCC = new HeatCapacityCalculator(cpGasConst,cpLiqConst, 298,P, Tc, Pc, omega,kijGen);
	}
	//Calculates Cp by calling heatcapacityCalculator for gases
	private double[] cpGasCalc(double T, double[] gasComp) {
		//saves gas comps before doing enthalpy calcs
		this.hCC.setGasComp(gasComp);
		return this.hCC.getCpGas(T);
	}
	//calculates Cp by calling Heatcapacity calculator for liquids
	private double[] cpLiqCalc(double T,double[] liqComp) {
		//saves liquid composiiton before doing enthalpy calcs
		this.hCC.setLiqComp(liqComp);
		return this.hCC.getCpLiq(T);
	}
//generic method that needs to be inherited by all classes
	public abstract double iterateForTorGetQ(double T);
//Enthalpy calculator for Vapour phase
	protected double hVCalc(double T,double[][] gasComp) {
		//gets vapour cp values either ideal or real depending on isReal boolean
		double[] cpV = cpGasCalc(T, gasComp[2]).clone();
		double sum=0;
		for(int i =0; i < gasComp[0].length; i++){
			//x*Cp*(T - Tref)
				sum+=gasComp[2][i]*cpV[i];
		}
		this.h_V =sum;
		return sum;
	}
//enthalpy calculator for liquid (sums all enthalpies)
	protected double hLCalc(double T,double[][] liqComp) {
		//gets cp values for liquid or real depending on isReal boolean in hCC
		double[] cpL = cpLiqCalc(T, liqComp[1]).clone();
		double sum=0;
		for(int i =0; i < liqComp[0].length; i++){
			//x*Cp*(T - Tref)
				sum+=liqComp[1][i]*cpL[i];
		}
		this.h_L =sum;
		return sum;
	}
	//calculates feed enthalpies (assumes feed is liquid)
	protected double hFCalc(double T, double[][] liqComp) {
		//gets real or ideal liquid cps depending on if isReal boolean is true or false in hCC
		double[] cpL = cpLiqCalc(T,liqComp[0]).clone();
		double sum=0;
		for(int i =0; i < liqComp[0].length; i++){
			//x*Cp*(T - Tref)
				sum+=liqComp[0][i]*cpL[i];
		}
		this.h_F=sum;
		return sum;
	}
	//Used for iterating cases with root finding method
	public double eq(double x) {
		return iterateForTorGetQ(x);
	}
	//used for reassinging flow rates for iterating energy balance, F is feed, L is liquid, V is vapour
	public void setFlowRate(double[] flowRates){
		this.F = flowRates[0];
		this.L = flowRates[1];
		this.V = flowRates[2];	
	}
	

}
