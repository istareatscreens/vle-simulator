package fluidpackages;

import bubblepointdewpoint.IdealBubblePoint;
import bubblepointdewpoint.IdealDewPoint;
import chemicals.*;
import chemicals.IdealChemical;
import equations.GeneralEquations;
import main.GenericMethods;
//Fluid package for ideal case, P^sat/P = K
public class RouletsLaw implements FluidPackage {

	private double temperature;
	private double[][] antoineConst;
	private double pressure;
	double[] z;

	//Constructor for ideal
	public RouletsLaw(double temperature, double pressure, Chemical[] chemical, double[] z) {
		
		this.temperature = temperature;
		this.pressure = pressure;
		this.antoineConst = new double[chemical.length][3];
		//getting antione equations through casting
		for(int i = 0; i < chemical.length; i++) {
			this.antoineConst[i] = new IdealChemical((IdealChemical)chemical[i]).getAntoineConst().clone();
		}
		this.antoineConst = GenericMethods.clone2Ddoublearray(antoineConst);
		this.z = z.clone();
	}
	
	//Copy constructor for RouletsLaw
	public RouletsLaw(RouletsLaw rl){
		this.temperature = rl.temperature;
		this.pressure = rl.pressure;
		this.antoineConst = GenericMethods.clone2Ddoublearray(rl.antoineConst);
		this.z = rl.z.clone();
	}

	//Computes K from antiones equation and pressure
	public double[] getK() {	
		double[] temp = new double[antoineConst.length];
		for (int i = 0; i < antoineConst.length; i++) {
			temp[i] = GeneralEquations.eqAntoine(this.temperature, this.antoineConst[i])/ (this.pressure);
		}
		return temp;
	}
	//used to create clone of fluid package
	public FluidPackage copyConstructor(FluidPackage fp){
		return new RouletsLaw((RouletsLaw)fp);
	}
//used to get bubble Point pressure Ideal case
	public double getBubbleP() {
		IdealBubblePoint bp = new IdealBubblePoint(this.temperature, this.z, this.antoineConst);
		return bp.getBubblePressure();
	}
//Used to get dew point pressure IDeal case
	public double getDewP() {
		IdealDewPoint dp = new IdealDewPoint(this.temperature, this.z, this.antoineConst);
		return dp.getDewPressure();
	}
	//Mutators
	public void setP(double P){
		this.pressure = P;
	}
	
	public void setT(double T){
		this.temperature = T;
	}
	
	//Accessors
	public double getP(){
		return this.pressure;
	}

	
	
}
