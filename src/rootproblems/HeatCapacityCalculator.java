package rootproblems;

import chemicals.kijValues;
import fluidpackages.PengRobinsonCalcs;
import main.GenericMethods;
//Calculates heat capacities Either Ideal or Real (using peng robinson equation of state)
public class HeatCapacityCalculator extends PengRobinsonCalcs {

	private double[][] cpGasConst;
	private double[][] cpLiqConst;
	private double[] gasComp;
	private double[] liqComp;
	private boolean isReal;

	// REAL ONLY Cp Constucter
	public HeatCapacityCalculator(double[][] cpGasConst, double[][] cpLiqConst, double T, double P, double[] Tc,
			double[] Pc, double[] omega, kijValues kijGen) {
		super(T, P, Tc, Pc, omega, kijGen, true);
		//If isReal is true real Cp values are calculated
		this.isReal = true;
		this.cpGasConst = GenericMethods.clone2Ddoublearray(cpGasConst);
		this.cpLiqConst = GenericMethods.clone2Ddoublearray(cpLiqConst);
	}

	// IDEAL ONLY Cp Constructor
	public HeatCapacityCalculator(double[][] cpGasConst, double[][] cpLiqConst) {
		super(0, 0, null, null, null, null, false);
		//If isReal is true ideal Cp values are calculated
		this.cpGasConst = GenericMethods.clone2Ddoublearray(cpGasConst);
		this.cpLiqConst = GenericMethods.clone2Ddoublearray(cpLiqConst);
		this.isReal = false;
	}
	//Calculates ideal Cp values for gas, this is needed with real cp calc as well
	private double[] cpGasIdealCalc(double T) {
		double[] temp = new double[this.cpGasConst.length];
		for (int i = 0; i < this.cpGasConst.length; i++) {
			temp[i] = this.cpGasConst[i][0] + this.cpGasConst[i][1] * T + this.cpGasConst[i][2] * Math.pow(T, 2.)
					+ this.cpGasConst[i][3] * Math.pow(T, 4.);
		}
		return temp;
	}
	//updates the liquid composition for peng robins real Cp alculation, neeeded when iterating case 2
	public void setLiqComp(double[] liqComp) {
		if (isReal) {
			this.liqComp = liqComp.clone();
		}
	}
	//updates the gas composition for peng robins real Cp alculation, neeeded when iterating case 2
	public void setGasComp(double[] gasComp) {
		if (isReal) {
			this.gasComp = gasComp.clone();
		}
	}
	//Calculates ideal Cp values for liquid, this is needed with real cp calc as well
	private double[] cpLiqIdealCalc(double T) {
		double[] temp = new double[this.cpLiqConst.length];
		for (int i = 0; i < this.cpGasConst.length; i++) {
			temp[i] = this.cpLiqConst[i][0] + this.cpLiqConst[i][1] * T + this.cpLiqConst[i][2] * Math.pow(T, 2.)
					+ this.cpLiqConst[i][3] * Math.pow(T, 4.);
		}
		return temp;
	}
//Acquires Root to cubic equation of state for use with real cp Value calculation
	private double getZ(boolean isGas) {
		CubicEquationOfState EOS;
		double Z;
		//if is Gas is true then computes Vapour Z value, which is Max Value root
		if (isGas) {
			// Vapour
			EOS = new CubicEquationOfState(super.calcEOSCoefs(this.gasComp));
			Z = GenericMethods.findMaxDoubleArray(EOS.getRoots());
		} else {
			//if is Liquid is true then computes Vapour Z value, which is Min Value root
			// Liquid
			EOS = new CubicEquationOfState(super.calcEOSCoefs(this.liqComp));
			Z = GenericMethods.findMinDoubleArray(EOS.getRoots());
		}
		return Z;
	}
//Gets Value of M for Computing real Cp
	private double getM(boolean isGas) {
		double gasZ; 
		double liqZ ;
		double[] coefs;
		//Does gas check so proper Z is used in formula
		if (isGas) {
			gasZ = getZ(isGas);
			coefs = super.calcEOSCoefs(this.gasComp).clone();
			return (Math.pow(gasZ, 2) + 2 * coefs[1] * gasZ - Math.pow(coefs[1], 2)) / (gasZ - coefs[1]);
		} else {
			liqZ = getZ(isGas);
			coefs = super.calcEOSCoefs(this.liqComp).clone();
			return (Math.pow(liqZ, 2) + 2 * coefs[1] * liqZ - Math.pow(coefs[1], 2)) / (liqZ - coefs[1]);
		}
	}
//Gets Value of N for Computing real Cp
	private double[] getN(boolean isGas) {
		double[] temp = new double[Tc.length];
		double[] aTP = super.aTPrime();
		double[] coefs;
		double[] b_i = super.b_iCalc();
		//is Gas is used to use proper coeffiecnet
		if (isGas) {
			coefs = super.calcEOSCoefs(this.gasComp).clone();
			for (int i = 0; i < Tc.length; i++) {
				temp[i] = aTP[i] * coefs[1] / (b_i[i] * 8.314);
			}
		} else {
			coefs = super.calcEOSCoefs(this.liqComp).clone();
			for (int i = 0; i < Tc.length; i++) {
				temp[i] = aTP[i] * coefs[1] / (b_i[i] * 8.314);
			}
		}
		return temp;
	}
//Computes Real cp values for gas and Liquid
	private double[] cpRealCalc(double T, boolean isGas) {
		//acquires all needed values
		double[] temp = new double[Tc.length];
		double[] coefs;
		double[] cpIdeal;
		double[] aTDP = super.aTDoublePrime();
		double[] b_i = super.b_iCalc();
		double Z;
		double dTwo = Math.sqrt(2) + 1;
		double dOne = Math.sqrt(2) - 1;
		double M;
		double[] N;
		//Computes gas cp values
		if (isGas) {
			Z = getZ(isGas);
			cpIdeal = cpGasIdealCalc(T);
			coefs = super.calcEOSCoefs(this.gasComp).clone();
			N = getN(true);
			M = getM(true);
			for (int i = 0; i < this.cpGasConst.length; i++) {
				temp[i] = cpIdeal[i]
						+ (aTDP[i] * T / (2 * Math.sqrt(2 * b_i[i]))
								* Math.log((Z + dTwo * coefs[1]) / (Z - dOne * coefs[1]))
						+ 8.314 * Math.pow(M - N[i], 2) / (Math.pow(M, 2) - 2 * coefs[0] * (Z + coefs[1])) - 8.314)/1000;
			}
		} else {
			//computes liquid cp values
			Z = getZ(isGas);
			cpIdeal = cpLiqIdealCalc(T);
			coefs = super.calcEOSCoefs(this.liqComp).clone();
			N = getN(false);
			M = getM(false);
			for (int i = 0; i < this.cpLiqConst.length; i++) {
				temp[i] = cpIdeal[i]
						+ (aTDP[i] * T / (2 * Math.sqrt(2 * b_i[i]))
								* Math.log((Z + dTwo * coefs[1]) / (Z - dOne * coefs[1]))
						+ 8.314 * Math.pow(M - N[i], 2) / (Math.pow(M, 2) - 2 * coefs[0] * (Z + coefs[1])) - 8.314)/1000;
			}

		}
		//returns Real Cp values
		return temp;
	}
//Acquires Real or Ideal Cp values depending on if instance variable isReal is set to true orfalse for liquid
	public double[] getCpLiq(double T) {
		if (isReal) {
			super.setT(T);
			return cpRealCalc(T, false);
		} else {
			return cpLiqIdealCalc(T);
		}

	}
	//Acquires Real or Ideal Cp values depending on if instance variable isReal is set to true orfalse for gas
	public double[] getCpGas(double T) {
		if (isReal) {
			super.setT(T);
			return cpRealCalc(T, true);
		} else {
			return cpGasIdealCalc(T);
		}
	}
}
