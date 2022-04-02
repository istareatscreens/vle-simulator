package numericalmethods;

import rootproblems.RootProblem;
//Bisection Method can be used as possible write out
public class BisectionMethod implements RootFindingMethod {

	private double xMid;
	private double xU;
	private double xL;
	private int iterativeCount;
	private double xMidO;
	//Sets boudns to method
	public BisectionMethod(double xL, double xU){
		this.xL = xL;
		this.xU = xU;
	}
	//Copy constructor
	public BisectionMethod(BisectionMethod bm){
		this.xL = bm.xL;
		this.xU = bm.xU;
	}
	//Finds root using bisection method takes error in
	public double findRoot(double error, RootProblem rp){
		this.iterativeCount=0;
		do{
			savexMid();
			calcxMid();
			if(rp.eq(this.xL)*rp.eq(this.xMid) < 0){
				this.xU = this.xMid;
			}else if(this.iterativeCount > 50000){
				break;
			}else{
				this.xL = this.xMid;
			this.iterativeCount++;
			}
		}while(error < calcError());
		return this.xMid;
	}
	//computes error
	private double calcError(){
		return Math.abs((this.xMid - this.xMidO)/this.xMid);
	}
	//computes mid point of bounds
	private void calcxMid(){
		this.xMid = (xU + xL)/2;
	}
	//saves mid points of bound
	private void savexMid(){
		this.xMidO = this.xMid;
	}
	//copy constructor for root finding problem
	public RootFindingMethod copyConstructor(RootFindingMethod fp) {
		return new BisectionMethod((BisectionMethod)fp);
	}
	//sets bounds of bisection method
	public void setBounds(double xL, double xU) {
		this.xL = xL;
		this.xU = xU;
	}
	
}
