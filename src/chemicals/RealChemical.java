package chemicals;

import chemsDatabase.*;

//Stores chemical properties for use with WilsonCorrelation

public class RealChemical extends Chemical {

	protected double Tc;
	protected double Pc;
	protected double acentricFactor;
	protected double Tb;

//Real Chemical Constructor
	public RealChemical(RealChemical rc){
		super( rc.name,  rc.molarMass, rc.cpConstGas, rc.cpConstLiq);
		this.Tc = rc.Tc;
		this.Pc = rc.Pc;
		this.acentricFactor = rc.acentricFactor;
	}
	//Makes real chemical from database object
	public RealChemical(Database cd) {
		super(cd);
		this.Tc = cd.getTc();
		this.Pc = cd.getPc();
		this.acentricFactor = cd.getAcentricFactor();
	}
	//Takes in name, molar mass, cpConstGas, cpConstLiq, Tc, Pc, acentric factor
	public RealChemical(String name, double molarMass, double[] cpConstGas, double[] cpConstLiq, double Tc, double Pc, double acentricFactor){
		super( name,  molarMass, cpConstGas, cpConstLiq);
		this.Tc = Tc;
		this.Pc = Pc;
		this.acentricFactor = acentricFactor;
	}
	//constructor
	public RealChemical(String name, double molarMass, double Tc, double Pc, double acentricFactor){
		super( name,  molarMass, null, null);
		this.Tc = Tc;
		this.Pc = Pc;
		this.acentricFactor = acentricFactor;
	}
	
	
	
	public double getTc(){
		return this.Tc;
	}
	
	public double getPc(){
		return this.Pc;
	}
	
	public double getAF(){
		return this.acentricFactor;
	}
	
	
	
}
