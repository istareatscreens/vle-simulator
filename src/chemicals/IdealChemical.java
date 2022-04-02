package chemicals;

import chemsDatabase.*;

//stores properties for Raoult's law
public class IdealChemical extends Chemical {

	private double[] antoineEqConst;
	//Creates Ideal chemical from a data base class
	public IdealChemical(Database cd) {
		super(cd);
		if (cd.getAntoineConst() != null)
			this.antoineEqConst = cd.getAntoineConst().clone();
	}
	//Copy constructor for Ideal chemical
	public IdealChemical(IdealChemical ic) {
			super(ic.name, ic.molarMass, ic.cpConstGas, ic.cpConstLiq);
			this.antoineEqConst = ic.getAntoineConst().clone();
		
	}
	//Ideal chemical name, antoine Equation constants, Molar Mass
	public IdealChemical(String name, double[] antoineEqConst, double MolarMass) {
		super(name, MolarMass);
		this.antoineEqConst = antoineEqConst.clone();
	}
	//Ideal Chemical constructor for name, molar mass, antoine constants, cpGas constants cp Liqu constant
	public IdealChemical(String name, double molarMass, double[] antoineEqConst, double[] cpGasConst, double[]cpLiqConst) {
		super(name, molarMass, cpGasConst, cpLiqConst);
		this.antoineEqConst = antoineEqConst.clone();
	}

//Mutators
	public void setAntEqConst(double[] antoineEqConst) {
		if (isDoubleArrayProper(3, antoineEqConst))
			this.antoineEqConst = antoineEqConst.clone();
	}

	public String getName() {
		return this.name;
	}


//Accessors
	public double[] getAntoineConst() {
		return checkArray(this.antoineEqConst);
	}

	
}
