package bubblepointdewpoint;

import equations.GeneralEquations;
import main.GenericMethods;
//Calculates Ideal using Raoults Law (I CANNOT SPELL)
public class IdealBubblePoint{
	//Instance variables
	private double[][] antoineConst;
	private double[] z;
	private double temperature;
	//Constructor
	public IdealBubblePoint(double temperature, double[] z, double[][] antoineConst) {
		this.temperature = temperature;
		this.z = z.clone();
		this.antoineConst = GenericMethods.clone2Ddoublearray(antoineConst);
	}
	//Gets saturated Pressure from antoines equation
	private double getSaturatedPressure(double[] antoineConst) {
		return GeneralEquations.eqAntoine(this.temperature, antoineConst);
	}
//Computes bubble point pressure
	public double getBubblePressure() {
		double temp = 0;
		for (int i = 0; i < this.z.length; i++) {
			temp +=  z[i]*getSaturatedPressure(this.antoineConst[i]);
		}
		return temp;
	}

}
