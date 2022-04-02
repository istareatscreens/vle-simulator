package numericalmethods;

import rootproblems.RootProblem;
//Used to solve root finding problems with ridder method
public class RiddersMethod implements RootFindingMethod {

	private double xRN;
	private double xL;
	private double xU;
	private double xRO;
	private double xMid;
	private double cError;

	//constructor with upper and lower bounds
	public RiddersMethod(double xL, double xU) {
		this.xL = xL;
		this.xU = xU;
	}
//Ridders method copy constructor
	public RiddersMethod(RiddersMethod rm) {
		this.xL = rm.xL;
		this.xU = rm.xU;
	}
 //Conducts ridders method
	public double findRoot(double error, RootProblem rp) {
		double count = 0;
		do {
			//Calculates mid point
			setxMid();
			//trys ridders method (ridders goes NaN if bound gives + + or - -)
			try{
			xRCalc(rp);
			}catch(Exception e){
				//if error method  continues on, if its 0 then it returns mid point, if its not zero returns old xR
				if(xRO!=0){
					this.xRN = this.xRO;
					break;
				}else{
					this.xRN = this.xMid;
					break;
				}
			}
			if (xMid < xRN) {
				if (0 > rp.eq(this.xL) * rp.eq(this.xMid)) {
					savexRN();
					this.xU = xMid;
				} else if (0 > rp.eq(this.xRN) * rp.eq(xU)) {
					savexRN();
					this.xL = xRN;
				} else {
					savexRN();
					this.xL = xMid;
					this.xU = xRN;
				}
			} else {
				if (0 > rp.eq(this.xL) * rp.eq(this.xRN)) {
					savexRN();
					xU = xRN;
				} else if (rp.eq(this.xMid) * rp.eq(this.xRN) < 0) {
					savexRN();
					xL = xRN;
					xU = xMid;
				} else {
					savexRN();
					xL = xMid;
				}
			}
			count++;
			//if runs too long it exits
			if(count>20000){
				break;
			}
			try{
			xRCalc(rp);
			}catch(Exception e1){
				this.xRN = this.xRO;
				break;
			}
			//calculates error
			calcError();
		} while (cError > error);
		//returns values (not always correct) but typically if this method diverges the entire program diverges and no solution is reported
		return this.xRN;
	}

	private void setxMid() {
		this.xMid = ((xU + xL) / 2);
	}

	private void calcError() {
		this.cError = Math.abs((xRN - xRO) / xRN);
	}
//comptues xR using ridders method if answer is NaN no bound is found between x upper and x lower, if error it throws exception which is caught and  cuases
//ridders method to end with an incomplete number
	private void xRCalc(RootProblem rp) throws Exception{
		this.xRN = this.xMid + (this.xMid - this.xL) * Math.signum(rp.eq(this.xL) - rp.eq(this.xU)) * rp.eq(this.xMid)
				/ Math.sqrt(Math.pow(rp.eq(this.xMid), 2) - (rp.eq(this.xL) * rp.eq(this.xU)));
		if (Double.isNaN(this.xRN)) {
			throw new Exception("Ridders cannot converge, No Solution Possible\n");
		}
	}
	//savesXRN to old for error calc and exit value
	private void savexRN() {
		this.xRO = this.xRN;
	}
	//copy constructor method
	public RootFindingMethod copyConstructor(RootFindingMethod fp) {
		return new RiddersMethod((RiddersMethod) fp);
	}
	//sets bounds of ridders method
	public void setBounds(double xL, double xU) {
		this.xL = xL;
		this.xU = xU;
	}
}
