package chemsDatabase;
@SuppressWarnings("unused")


public class NitrogenData extends Database{

	private String name = "Nitrogen";
	private double molarMass = 28.014;//g/mol
	private double[] cpConstGas = {0.029, 0.000002199 , 5.723E-09,-2.871E-12};//J/gK
	private double[] cpConstLiq = {0., 0. , 0.,0.};//NO VALUES
	private double[] antoineConst = {8.334, 588.72, -6.6};
	private double tC = 126.2;//K
	private double pC = 3390;//kPa
	private double acentricFactor = 0.037;
	private double[] components = {0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,1 , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private boolean isGas = true;
	private double boilingP =77;

	
	public NitrogenData() {
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