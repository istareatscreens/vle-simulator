package chemicals;

import java.util.Scanner;
import chemsDatabase.*;
//Parent class to chemical
public class Chemical {

	protected String name;
	protected double molarMass;
	protected double[] cpConstLiq;
	protected double[] cpConstGas;
	protected double boilingPoint;
	protected boolean isGas;
	

	// Constructors
	//Creates a chemical from the data base class
	public Chemical(Database cd) {
		this.name = cd.getName();
		this.molarMass = cd.getMM();
		this.boilingPoint = cd.getBoilingP();
		this.isGas = cd.isGas();
			this.cpConstGas = cd.getCpGas().clone();
			this.cpConstLiq = cd.getCpLiq().clone();
		
	}
	//Chemical copy constructor
	public Chemical(Chemical chem){
		this.name = chem.name;
		this.molarMass = chem.molarMass;
		this.boilingPoint = chem.boilingPoint;
		this.isGas = chem.isGas;
		this.cpConstLiq = chem.cpConstLiq.clone();
		this.cpConstGas = chem.cpConstGas.clone();
	}
	public double getBoilingPoint() {
		return boilingPoint;
	}
	public void setBoilingPoint(double boilingPoint) {
		this.boilingPoint = boilingPoint;
	}
	public boolean isGas() {
		return isGas;
	}
	public void setGas(boolean isGas) {
		this.isGas = isGas;
	}
	//Constructor requires chemical name, molar mass
	public Chemical(String name, double molarMass) {
		setMolarMass(molarMass);
		setName(name);
	}	
	
	//takes in name and molar mass and cpgas constants and cp liquid constants
	public Chemical(String name2, double molarMass2, double[] cpConstGas2, double[] cpConstLiq2) {
		this.name = name2;
		this.molarMass = molarMass2;
		if (cpConstGas2 != null)
			this.cpConstGas = cpConstGas2.clone();
		if (cpConstLiq2 != null)
			this.cpConstLiq = cpConstLiq2.clone();
		
	}
//mutators
	public void setName(String name) {
		this.name = name;
	}

	public void setMolarMass(double MolarMass) {
		this.molarMass = MolarMass;
	}
//checks to makes ure array is proper size
	protected boolean isDoubleArrayProper(int arraySize, double[] array) {
		if (array == null) {
			return false;
		} else if (array.length == arraySize) {
			return true;
		} else {
			System.out.println("Improper number of Arguments for Constant Array");
			System.exit(0);
			return false;
		}
	}
	//Sets cpLiq
	public void setCpConstLiq(double[] cpConstLiq) {
		if (isDoubleArrayProper(3, cpConstLiq))
			this.cpConstLiq = cpConstLiq.clone();
	}
	//set Cp gas constant
	public void setCpConstGas(double[] cpConstGas) {
		if (isDoubleArrayProper(4, cpConstGas))
			this.cpConstGas = cpConstGas.clone();
	}

	// Accessors
	public double[] getCpConstGas() {
		return checkArray(this.cpConstGas).clone();
	}

	public double[] getCpConstLiq() {
		return checkArray(this.cpConstLiq).clone();
	}
	
	
	public double getMolarMass() {
		return this.molarMass;
	}

	public String getName() {
		return this.name;
	}

	// For checking access to constant arrays
	protected double[] checkArray(double[] array) {
		if (array != null) {
			return array.clone();
		}
		System.out.println("Cannot access Chemical.class array -> null pointer Exception");
		System.exit(-1);
		return null;
	}


}
