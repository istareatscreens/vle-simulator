package equations;

import chemicals.Chemical;
//Has methods shared by multiple objects
public class GeneralEquations {
	//Calculates Y values using psi
	public static double[] calculateExitLiquidComposition(double psi, double[] z, double[] K) {
		double[] temp = new double[K.length];
		for (int i = 0; i < K.length; i++) {
			temp[i] = z[i] / (1 + psi * (K[i] - 1));
		}
		return temp;
	}
	//Calculates X values using psi
	public static double[] calculateExitVapourComposition(double psi, double[] z, double[] K) {
		double[] temp = new double[K.length];
		for (int i = 0; i < K.length; i++) {
			temp[i] = (z[i] * K[i]) / (1 + psi * (K[i] - 1));
		}
		return temp;
	}
	//equation for antoines equation for calculating Psat
	public static double eqAntoine(double T, double[] constants) {
		double lnPsat = constants[0] - (constants[1] / (constants[2] + (T)));
		return Math.pow(Math.E, lnPsat)*100;
	}
	//converts from mass flow (grams) rate to mole molar flow rate
	public static double convertToMolarFlowRate(double[] massComp, double flowRate, Chemical[] chemicals) {
		double[] temp = new double[massComp.length];
		for (int i = 0; i < massComp.length; i++) {
			temp[i] = (massComp[i]) * flowRate / chemicals[i].getMolarMass();
		}
		return calculateTotalMolarFlow(temp);
	}
	//Converts fractions from mass fraction to mole freactions
	public static double[] convertFractionMassToMoles(double[] massComp, double flowRate, Chemical[] chemicals) {
		double[] temp = new double[massComp.length];
		double totalMolarFlow;
		for (int i = 0; i < massComp.length; i++) {
			temp[i] = (massComp[i]) * flowRate / chemicals[i].getMolarMass();
		}
		totalMolarFlow = calculateTotalMolarFlow(temp);
		for (int i = 0; i < massComp.length; i++) {
			temp[i] = temp[i] / totalMolarFlow;
		}
		return temp;
	}
	//computes molar flow summation
	private static double calculateTotalMolarFlow(double[] array) {
		double sum = 0;
		for (int i = 0; array.length > i; i++) {
			sum += array[i];
		}
		return sum;
	}

}
