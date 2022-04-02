package rootproblems;

import chemicals.Chemical;
import chemicals.IdealChemical;
import chemicals.RealChemical;
import chemicals.Structure;
import chemicals.kijValues;
import flashvessel.Vessel;
import fluidpackages.FluidPackage;
import fluidpackages.PengRobinson;
import fluidpackages.RouletsLaw;
import fluidpackages.WilsonsCorrelation;
import main.GenericMethods;
import solver.Solver;
//Solves for vessel tmeperature given feed temperature and adiabatic vessel, through an energy balance
public class EnergyBalanceC2 extends EnergyBalance {
//instance variables
	private double fTemp;
	private int ideality;
	private double[] comp;
	private double[] Tc;
	private double[] Pc;
	private double[] omega;
	private double[][] antoinesConst;
	private kijValues k_ijGen;
	private double P;
	private Chemical[] chemicals;
	private double[] flowRates;
	private Solver solver;
	private boolean[] isGas;
	private double[] Tb;


//Constructor for ideal Cp values, saves all values needed to make fluid package and solver object in order to iterate them
	public EnergyBalanceC2(Chemical[] chemicals, double[] flowRates, double fTemp, double[] comp, int ideality,
			kijValues k_ijGen, double P, boolean[] isGas, double[] Tb) {
		super(chemicals, flowRates);
		this.ideality = ideality;
		this.fTemp = fTemp;
		this.comp = comp.clone();
		this.P = P;
		this.flowRates = flowRates.clone();
		//Chooses chemical type based on ideality selected (0 is ideal, 1 is wilson, 2 is peng robinson) therefore needs to convert chemicals to proper 
		//object type to extract values then it needs to deep copy the them
		this.chemicals = new Chemical[chemicals.length];
		if (this.ideality == 0) {
			this.antoinesConst = new double[chemicals.length][];
			for (int i = 0; i < chemicals.length; i++) {
				this.antoinesConst[i] = (new IdealChemical((IdealChemical) chemicals[i])).getAntoineConst().clone();
				this.chemicals[i] = (Chemical) (new IdealChemical((IdealChemical) chemicals[i]));
			}
		} else if (this.ideality == 1) {
			this.Tc = new double[chemicals.length];
			this.Pc = new double[chemicals.length];
			this.omega = new double[chemicals.length];
			this.isGas = isGas.clone();
			this.Tb = Tb.clone();
			//acquiring array of chemicals
			for (int i = 0; i < chemicals.length; i++) {
				this.Tc[i] = (new RealChemical((RealChemical) chemicals[i])).getTc();
				this.Pc[i] = (new RealChemical((RealChemical) chemicals[i])).getTc();
				this.omega[i] = (new RealChemical((RealChemical) chemicals[i])).getAF();
				this.chemicals[i] = (Chemical) (new RealChemical((RealChemical) chemicals[i]));
			}
		} else if (this.ideality == 2) {
			this.Tc = new double[chemicals.length];
			this.Pc = new double[chemicals.length];
			this.omega = new double[chemicals.length];
			this.k_ijGen = new kijValues(k_ijGen);
			this.isGas = isGas.clone();
			this.Tb = Tb.clone();
			//acquiring arrays of properties
			for (int i = 0; i < chemicals.length; i++) {
				this.Tc[i] = (new Structure((Structure) chemicals[i])).getTc();
				this.Pc[i] = (new Structure((Structure) chemicals[i])).getTc();
				this.omega[i] = (new Structure((Structure) chemicals[i])).getAF();
				this.chemicals[i] = (Chemical) (new Structure((Structure) chemicals[i]));
			}

		}

	}
	
//Constructor for Real Cp values does same thing as other constructor but passes more vales to parent class so real Cp values can be calculated from
//Peng robinson EOS
	public EnergyBalanceC2(double fTemp, Chemical[] chemicals, double P, double[] flowRates, double[] Tc, double[] Pc,
			double[] omega, int ideality, kijValues kijGen, double[] comp, boolean[] isGas, double[] Tb) {
		super(chemicals, P, flowRates, Tc, Pc, omega, kijGen);
		this.ideality = ideality;
		this.fTemp = fTemp;
		this.comp = comp.clone();
		this.P = P;
		this.flowRates = flowRates.clone();
		this.chemicals = new Chemical[chemicals.length];
		if (this.ideality == 0) {
			this.antoinesConst = new double[chemicals.length][];
			for (int i = 0; i < chemicals.length; i++) {
				this.antoinesConst[i] = (new IdealChemical((IdealChemical) chemicals[i])).getAntoineConst().clone();
				this.chemicals[i] = (Chemical) (new IdealChemical((IdealChemical) chemicals[i]));
			}
		} else if (this.ideality == 1) {
			this.Tc = new double[chemicals.length];
			this.Pc = new double[chemicals.length];
			this.omega = new double[chemicals.length];
			this.isGas = isGas.clone();
			this.Tb = Tb.clone();
			for (int i = 0; i < chemicals.length; i++) {
				this.Tc[i] = (new RealChemical((RealChemical) chemicals[i])).getTc();
				this.Pc[i] = (new RealChemical((RealChemical) chemicals[i])).getTc();
				this.omega[i] = (new RealChemical((RealChemical) chemicals[i])).getAF();
				this.chemicals[i] = (Chemical) (new RealChemical((RealChemical) chemicals[i]));
			}
		} else if (this.ideality == 2) {
			this.Tc = new double[chemicals.length];
			this.Pc = new double[chemicals.length];
			this.omega = new double[chemicals.length];
			this.k_ijGen = new kijValues(kijGen);
			this.isGas = isGas.clone();
			this.Tb = Tb.clone();
			for (int i = 0; i < chemicals.length; i++) {
				this.Tc[i] = (new Structure((Structure) chemicals[i])).getTc();
				this.Pc[i] = (new Structure((Structure) chemicals[i])).getTc();
				this.omega[i] = (new Structure((Structure) chemicals[i])).getAF();
				this.chemicals[i] = (Chemical) (new Structure((Structure) chemicals[i]));
			}

		}
	}
//Iterates for Vessel T (Should be lower than Feed T) this is iterated with a RootFinding Method such as ridders or bisection
	public double iterateForTorGetQ(double T) {
		FluidPackage fp;
		Vessel v;
		//Based on idality new fluid package and solver will be created each iteration at given T value
		if (this.ideality == 0) {
			fp = new RouletsLaw(T, this.P, this.chemicals, this.comp);
			this.solver = new Solver(T, this.P, this.flowRates[0], this.comp, this.chemicals, fp, 0);
			super.setFlowRate(solver.returnVessel().getFlowRates());
		} else if (this.ideality == 1) {
			fp = new WilsonsCorrelation(T, this.P, this.Tc, this.Pc, this.omega, this.comp, this.Tb, this.isGas);
			this.solver = new Solver(T, this.P, this.flowRates[0], this.comp, this.chemicals, fp, 1);
			super.setFlowRate(solver.returnVessel().getFlowRates());
			
		} else {
			fp = new PengRobinson(T, this.P, this.Tc, this.Pc, this.omega, this.flowRates[0], this.comp, this.chemicals, this.k_ijGen, this.Tb, this.isGas);
			this.solver = new Solver(T, this.P, this.flowRates[0], this.comp, this.chemicals, fp, 2);
			super.setFlowRate(solver.returnVessel().getFlowRates());
		}
		//Saves generated vessel
		v = this.solver.returnVessel();
		//Updates flow rates in energy balance as a requirement for iteration
		//also acquires new compositions from vessel object
		super.setFlowRate(v.getFlowRates());
		return hFCalc(fTemp, v.getLiquidComposition()) * super.F * (fTemp - 298) - hVCalc(T,v.getGasCompositions()) * super.V * (T - 298)- hLCalc(T,v.getLiquidComposition()) * super.L * (T - 298);
	}
	
	//returns solved solver, from iteration done in iterateForTorGetQ
	public Solver getSolvedSolver(){
		return new Solver(this.solver);
	}

}
