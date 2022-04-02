package chemsDatabase;

public class Database {

	protected String name = null;
	protected double molarMass = 0;
	protected double Tc= 0;
	protected double Pc= 0;
	protected double acentricFactor= 0;
	protected double[] cpConstGas = null;
	protected double[] cpConstLiq= null;
	protected double[] antoineConst= null;
	protected double[] molComponents= null;
	protected boolean isGas = false;
	protected double boilingP=0;
	
	
	public Database() {
		
	}
	
	public Database(Database d) {
		this.name = d.getName();
		this.molarMass = d.getMM();
		this.Tc = d.getTc();
		this.Pc = d.getPc();
		this.acentricFactor = d.getAcentricFactor();
		this.cpConstGas = d.getCpGas().clone();
		this.cpConstLiq = d.getCpLiq().clone();
		this.antoineConst = d.getAntoineConst().clone();
		this.molComponents = d.getMolComponents().clone();
		this.isGas = d.isGas;
		this.boilingP = d.boilingP;
	}
	
	

	

	

	

	///////////////Accessors////////////////
	public String getName() {
		return this.name;
	}
	public boolean isGas() {
		return isGas;
	}
	public double getBoilingP() {
		return boilingP;
	}
	public double getMM() {
		return this.molarMass;
	}
	public double getTc() {
		return this.Tc;
	}
	public double getPc() {
		return this.Pc;
	}
	public double getAcentricFactor() {
		return this.acentricFactor;
	}
	public double[] getCpGas() {
		return this.cpConstGas.clone();
	}
	public double[] getCpLiq() {
		return this.cpConstLiq.clone();
	}
	public double[] getAntoineConst() {
		return this.antoineConst.clone();
	}
	public double[] getMolComponents() {
		return this.molComponents.clone();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMolarMass(double molarMass) {
		this.molarMass = molarMass;
	}

	public void setTc(double tc) {
		this.Tc = tc;
	}

	public void setPc(double pc) {
		this.Pc = pc;
	}

	public void setAcentricFactor(double acentricFactor) {
		this.acentricFactor = acentricFactor;
	}

	public void setCpConstGas(double[] cpConstGas) {
		this.cpConstGas = cpConstGas;
	}

	public void setCpConstLiq(double[] cpConstLiq) {
		this.cpConstLiq = cpConstLiq;
	}

	public void setAntoineConst(double[] antoineConst) {
		this.antoineConst = antoineConst;
	}

	public void setMolComponents(double[] mc) {
		this.molComponents = mc;
	}
	public void setGas(boolean isGas) {
		this.isGas = isGas;
	}
	public void setBoilingP(double boilingP) {
		this.boilingP = boilingP;
	}
	
}
