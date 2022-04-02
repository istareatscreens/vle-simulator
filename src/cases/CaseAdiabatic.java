package cases;

import chemicals.Chemical;
import fluidpackages.FluidPackage;
import numericalmethods.RootFindingMethod;
import solver.Solver;

public abstract class CaseAdiabatic extends Case {
	
	protected RootFindingMethod rfm;
	//Parent for Case 1 and 2, for sharing common methods
	public CaseAdiabatic(double T, double P, double flowRate, double[] chemComp, FluidPackage fp, int ideality, Chemical[] chemicals, RootFindingMethod rfm) {
		super(T, P, flowRate,chemComp, fp, ideality, chemicals);
		solver.setQinVessel(0);
		this.rfm = rfm.copyConstructor(rfm);
	}
}
