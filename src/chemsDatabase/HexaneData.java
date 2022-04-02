package chemsDatabase;
@SuppressWarnings("unused")


public class HexaneData extends Database{

	private String name = "Hexane";
	private double molarMass = 86.177;//g/mol
	private double[] cpConstGas = {0.13744, 0.0004085, -2.392E-07, 5.766E-11};//J/gK
	private double[] cpConstLiq = {0.2163,0., 0., 0};//NO VALUES
	private double[] antoineConst = {9.2164, 2697.55, -48.78};
	private double tC = 507.6;//K
	private double pC = 3040;//kPa
	private double acentricFactor = 0.304;
	private double[] components = {2 ,4 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private boolean isGas = false;
	private double boilingP = 0;

	
	public HexaneData() {
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