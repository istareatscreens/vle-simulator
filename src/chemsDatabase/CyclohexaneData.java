package chemsDatabase;
@SuppressWarnings("unused")

public class CyclohexaneData extends Database{

	private String name = "Cyclohexane";
	private double molarMass = 84.161;//g/mol
	private double[] cpConstGas = {0.09414, 0.0004962, -0.000000319, 8.063E-11};//kJ/molK
	private double[] cpConstLiq = {0.,0., 0.,0.};//J/gK
	private double[] antoineConst = {9.1325, 2766.63, -50.5};
	private double tC = 553.58;//K
	private double pC = 4100;//kPa
	private double acentricFactor = 0.212;
	private double[] components = {0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,6 ,0 ,0 ,0 , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private boolean isGas = false;
	private double boilingP = 0;

	
	public CyclohexaneData() {
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
