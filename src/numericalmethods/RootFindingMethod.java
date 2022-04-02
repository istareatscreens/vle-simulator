package numericalmethods;

import rootproblems.RootProblem;
//declares methods that need to be declared for a root finding method (bisection or ridders)
public interface RootFindingMethod{
//finds the root for a rootproblem
	public double findRoot(double error, RootProblem rp);
	//Copy constructor method
	public RootFindingMethod copyConstructor(RootFindingMethod fp);
	//Sets bounds of root finding method
	public void setBounds(double xL, double xU);

}
