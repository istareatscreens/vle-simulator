package fluidpackages;
//Interface for fluidpackage
public interface FluidPackage {
	//gets equilibrium constant
	public double[] getK();
//declares copy constructor for cloning
	public FluidPackage copyConstructor(FluidPackage fp);
//gets bubble P method
	public double getBubbleP();
	//get dew P method
	public double getDewP();
//sets pressure
	public void setP(double P);
	//gets pressure
	public double getP();
	//sets temperature
	public void setT(double T);
	
}
