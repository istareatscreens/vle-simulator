package userinput;
import java.util.Scanner;

import menuReadouts.MenuReadout;

public abstract class InterfaceHandler {
	private boolean firstRun;
	private int[] array;
	private boolean usesInt;
	private double ub;
	private double lb;
	
	////////////////Constructors//////////////////
	public InterfaceHandler(int[] array, boolean usesInt) {
		this.array = array.clone();
		this.firstRun = true;
		this.usesInt = usesInt;
	}
	public InterfaceHandler(double ub, double lb, boolean b) {
		this.ub = ub;
		this.lb = lb;
		this.firstRun = true;
		this.usesInt = false;
	}
	/////////////Accessors & Mutators//////////////////
	protected int[] getArray() {
		return this.array;
	}
	protected void setArray(int[] array) {
		this.array = array.clone();
	}
	protected boolean getFirstRun() {
		return this.firstRun;
	}
	protected void setFirstRun(boolean b) {
		this.firstRun = b;
	}
	///////////////////////////////////////
	
	public abstract int acquireUserInput(Scanner input, MenuReadout mr);
	public abstract double acquireUserInputDouble(Scanner input, MenuReadout mr);

//toString returns an error message if needed
	public String toString() {	
		if(usesInt) {
		return ifIntErrorReadout();
		}else {
		return ifdoubleErrorReadout();
		}
	}
		
//error message for int problems
	private String ifIntErrorReadout() {
		String temp = "Error: Invalid input. Please input one of the following values:\n";
		for(int i=0;i<this.array.length; i++) {
			if(i<this.array.length-1) {
				temp += this.array[i] + ", ";
			}else {
				temp += this.array[i];
				
			}//end of else
		}//end of for
		return temp + "\n";
	}
//error message for double problems	
	private String ifdoubleErrorReadout() {
		return "Error: Invalid input. Please input a value within the specified range:\n";
	}
//checks to see if int values are within the options given	
	public boolean checkInput(int c) {
		
		for(int i=0; i<this.array.length; i++) {
			if(c == this.array[i]) {
				return true;
			}
			
		}
		return false;
		
	}
//checks to see if the double value is between the set bounds
	public boolean checkDouble(double d) {
		if(d < this.ub & d > this.lb) {
			return true;
		}
		return false;
	}
	
}//end of main part of stuff
