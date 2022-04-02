package bubblepointdewpoint;

import equations.GeneralEquations;
import main.GenericMethods;
//Calculates Ideal Bubble point pressure with antoines equation for Raoults (I CANNOT SPELL)
public class IdealDewPoint{

	private double[][] antoineConst;
	private double[] z;
	private double temperature;
	//Constructor for ideal dew point
	public IdealDewPoint(double temperature, double[] z, double[][] antoineConst) {
		this.temperature = temperature;
		this.z = z.clone();
		this.antoineConst = GenericMethods.clone2Ddoublearray(antoineConst);
	}
	//Gets saturate 
	private double getSaturatedPressure(double[] antoineConst){
		return GeneralEquations.eqAntoine(this.temperature, antoineConst);
	}
	//gets Dew Pressure
	public double getDewPressure() {
		double temp=0;
		for(int i=0; i < this.z.length; i++){
			temp += this.z[i]/getSaturatedPressure(this.antoineConst[i]);
		}
		return 1/temp;
	}

	
	
}
