package chemsDatabase;
@SuppressWarnings("unused")


public class EthaneData extends Database{

	private String name = "Ethane";
	private double molarMass = 30.07;//g/mol
	private double[] cpConstGas = {0.04937,0.0001392, -5.816E-08,7.28E-12};//J/gK
	private double[] cpConstLiq = {0., 0., 0.,0.};//THERE ARE NO VALUES FOR THIS
	private double[] antoineConst = {9.0435, 1511.42, -17.16};
	private double tC = 305.32;//K
	private double pC = 4850;//kPa
	private double acentricFactor = 0.098;
	private double[] components = {2 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private boolean isGas = false;
	private double boilingP = 0;

	
	public EthaneData() {
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