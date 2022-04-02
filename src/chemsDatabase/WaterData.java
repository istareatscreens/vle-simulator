package chemsDatabase;
@SuppressWarnings("unused")

public class WaterData extends Database{
	private String name = "Water";
	private double molarMass = 18.015;//g/mol
	private double[] cpConstGas = {0.03346, 0.00000688, 7.604E-09,-3.593E-12};//J/gK
	private double[] cpConstLiq = {0.0754,0., 0.,0.};
	private double[] antoineConst = {11.6834, 3816.44, -46.13};
	private double tC = 647.13;//K
	private double pC = 21940;//kPa
	private double acentricFactor = 0.343;
	private double[] components = {0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 , 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0};
	private boolean isGas = false;
	private double boilingP = 0;
	
	public WaterData() {
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
