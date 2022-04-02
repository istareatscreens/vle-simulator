package chemsDatabase;
@SuppressWarnings("unused")


public class PentaneData extends Database{

	private String name = "Pentane";
	private double molarMass = 72.15;//g/mol
	private double[] cpConstGas = {0.1148, 0.0003409, -1.90E-07, 4.226E-11};//J/gK
	private double[] cpConstLiq = {0.1554, 0.0004368, 0., 0.};//NO VALUES FOR THIS
	private double[] antoineConst = {9.2131, 2477.07, -39.94};
	private double tC = 469.7;//K
	private double pC = 3360;//kPa
	private double acentricFactor = 0.251;
	private double[] components = {2 ,3 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private boolean isGas = false;
	private double boilingP = 0;

	
	public PentaneData() {
		super.name = this.name;
		super.molarMass = this.molarMass;
		super.cpConstGas = this.cpConstGas.clone();
		super.cpConstLiq = this.cpConstLiq.clone();
		super.antoineConst = this.antoineConst.clone();
		super.Tc = this.tC;
		super.Pc = this.pC;
		super.acentricFactor = this.acentricFactor;
		super.molComponents = this.components.clone();
		super.boilingP = this.boilingP;
		super.isGas = this.isGas;
	}
	
	
	
}