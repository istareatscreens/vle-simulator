package rootproblems;

import java.text.DecimalFormat;

import main.GenericMethods;
//Solves cubic equations using cardanios method
public class CardanoMethod {

	private double a;
	private double b;
	private double c;
	private double d;
	private DecimalFormat df = new DecimalFormat("#.#########");

	// https://proofwiki.org/wiki/Cardano%27s_Formula
	//Takes in coefficents constructor ax^3 + bx^2 + cx + d = 0
	public CardanoMethod(double a, double b, double c, double d) {
		if (a == 0) {
			System.out.println("a cannot equal 0! in Cardon's Method");
			System.exit(-1);
		}
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
//Comptues roots
	public double[] getRoots() {
		double[] oneRoot = new double[1];
		double[] threeRoots = new double[3];
		//if there are three real roots
		if (checkDeterminate()) {
			threeRoots[0] = getX1();//formatNumber(getX1());// Double.valueOf(df.format((2*Math.sqrt(-getQ())*Math.cos(getTheta()/3.)-this.b/(3.*this.a))));
			threeRoots[1] = getX2();//formatNumber(getX2());// Double.valueOf(df.format(2*Math.sqrt(-getQ())*Math.cos(getTheta()/3.
													// +
													// 2.*Math.PI/3.)-this.b/(3.*this.a)));
			threeRoots[2] = getX3();//formatNumber(getX3());// Double.valueOf(df.format(2*Math.sqrt(-getQ())*Math.cos(getTheta()/3.
													// +
													// 4.*Math.PI/3.)-this.b/(3.*this.a)));
			return threeRoots;
		} else {
			//if there is only one real root
			oneRoot[0] = getS() + getT() - this.b / (3. * this.a);
			return oneRoot;
		}
	}
//formats the number  not used though
	private double formatNumber(double value) {
		return Double.valueOf(df.format(value));
	}
//gets values to solve cardanios method
	private double getX1() {
		return 2 * Math.sqrt(-getQ()) * Math.cos(getTheta() / 3.) - this.b / (3. * this.a);
	}

	private double getX2() {
		return 2 * Math.sqrt(-getQ()) * Math.cos(getTheta() / 3. + 2. * Math.PI / 3.) - this.b / (3. * this.a);
	}

	private double getX3() {
		return 2 * Math.sqrt(-getQ()) * Math.cos(getTheta() / 3. + 4. * Math.PI / 3.) - this.b / (3. * this.a);
	}

	private double getTheta() {
		return Math.acos(getR() / (Math.sqrt(Math.pow(-getQ(), 3.))));
	}
//Checks to see if there are 3 real roots or 1 real root for the cubic equation
	private boolean checkDeterminate() {
		double D = getDeterminate();
		if (D < 0 || (D < GenericMethods.getErrorFactor() && D > -GenericMethods.getErrorFactor())) {
			return true;
		} else {
			return false;
		}
	}
//gets D value to determine if there are 3 or 1 real roots
	private double getDeterminate() {
		return Math.pow(getQ(), 3) + Math.pow(getR(), 2.);
	}

	private double getR() {
		return (9. * this.a * this.b * this.c - 27. * Math.pow(this.a, 2.) * this.d - 2. * Math.pow(this.b, 3.))
				/ (54. * Math.pow(this.a, 3.));
	}

	private double getQ() {
		return (3. * this.a * this.c - Math.pow(this.b, 2.)) / (9. * Math.pow(a, 2.));
	}

	private double getS() {
			return Math.cbrt(getR() + Math.pow(Math.pow(getQ(), 3.) + Math.pow(getR(), 2.), 1. / 2.));
	}

	private double getT() {
			return Math.cbrt(getR() - Math.pow((Math.pow(getQ(), 3.) + Math.pow(getR(), 2.)), 1. / 2.));
	}

}
