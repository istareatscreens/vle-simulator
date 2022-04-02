package rootproblems;
//Declares required method for root finding problem, inherited by all methods that need to be iterated by ridders or bisection
public interface RootProblem {
//method for the root finding method
	public double eq(double x);
	
}
