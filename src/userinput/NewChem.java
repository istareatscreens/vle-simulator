package userinput;

import java.util.Scanner;

import chemicals.Chemical;
import chemicals.IdealChemical;
import chemicals.RealChemical;
import chemicals.Structure;
import chemsDatabase.Database;
import menuReadouts.MenuReadout;
import newChemReadouts.*;

public class NewChem extends UserUI {

	protected int chemNum;
	protected Chemical[] additionalChems;
	protected double[] z;

	Scanner input = new Scanner(System.in);

	public NewChem() {
		this.chemNum = 0;
		this.additionalChems = null;
	}

	// primary method, makes a new chemical
	public Chemical MakeNewChem(int ideality, int realCp) {

		Chemical newChem;
		// String arrays to ask looped questions
		String[] s = { "A", "B", "C", "D" };
		String[] ms = { "CH3", "CH2", "CH", "C", "CH4", "C2H6", "CH_aro", "C_aro", "C_fusedaromatic", "CH2_cyclic",
				"CH/C_cyclic", "CO2", "N2", "H2S", "SH", "H2O", "C2H4", "CH2_alkenic", "C_alkenic", "CH_cycloalkenic",
				"H2", "CO", "He", "Ar" };

		Database db = new Database();// makes a new Database object
		// sizes the arrays needed
		double[] cpG = new double[4];
		double[] cpL = new double[4];
		double[] aC = new double[3];
		double[] mc = new double[24];
		double[] cpGdivide = {10e3, 10e5, 10e8, 10e12};
		boolean isGas;
		int gasInt;
		

		System.out.println("You have chosen to add a custom chemical.\nPlease input the name of the chemical below:\n");
		db.setName(input.next());// sets a name for the chemical
		System.out.println("Please input the molar mass of your chemical in kg/mol:\n");

		db.setMolarMass(getUserInputDouble(new blankDoubleReadout(), 9999, 0));// sets a molar mass for the chemical

		if (ideality >= 1|| realCp==2) {// sets additional properties if the ideality of the system requires it
			System.out.println("Please input the critical temperature of your chemical in Kelvin:\n");
			db.setTc(getUserInputDouble(new blankDoubleReadout(), 9999.0, 0.0));
			System.out.println("Please input the critical pressure of your chemical in kPa:\n");
			db.setPc(getUserInputDouble(new blankDoubleReadout(), 9999.0, 0.0));
			System.out.println("Please input the acentric factor of your chemical:\n");
			db.setAcentricFactor(getUserInputDouble(new blankDoubleReadout(), 9999.0, 0.0));

		}
		
		
		// asks for heat capacity constants
		System.out.println(
				"Please input the Gas specific heat capacity constants for your chemical:\n(If the value is blank, input 0)\n");

		for (int i = 0; i < cpG.length; i++) {
			System.out.println(s[i] + ":\n");
			cpG[i] = getUserInputDouble(new blankDoubleReadout(), 9999, -9999)/cpGdivide[i];
		}
		System.out.println(
				"Please input the Liquid specific heat capacity constants for your chemical:\n(If the value is blank, input 0)\n");
		for (int i = 0; i < cpL.length; i++) {
			System.out.println(s[i] + ":\n");
			cpL[i] = getUserInputDouble(new blankDoubleReadout(), 9999, -9999)/cpGdivide[i];
		}
		// adds antoine constants
		if(ideality == 0) {
		System.out.println(
				"Please input the Antoine's equation constants for your chemical:\n(If the value is blank, input 0)\n");
		for (int i = 0; i < aC.length; i++) {
			System.out.println(s[i] + ":\n");
			aC[i] = getUserInputDouble(new blankDoubleReadout(), 9999, -9999);
		}
		}

		db.setAntoineConst(aC);
		db.setCpConstGas(cpG);
		db.setCpConstLiq(cpL);

		if (ideality == 1||realCp ==2) {// sets functional groups if the ideality of the system requires it
			for (int i = 0; i < mc.length; i++) {
				System.out.println("Is the functional group " + ms[i] + " present in your chemical?\n1. Yes\n2. No\n");
				int temp = getUserInput(new blankReadout());
				if (temp == 1) {
					System.out.println("How many groups of this type are present?\n");
					mc[i] = getUserInputDouble(new blankDoubleReadout(), 9999, 0);
				} else {
					mc[i] = 0;
				}
			} // end of for
		}

		db.setMolComponents(mc);

		System.out.println("Thank you, your new chemical: " + db.getName() + " has been added to the system.");

		// creates a different type of chemical depending on ideality
		if (ideality == 1) {
			newChem = new Structure(db);
		} else if (ideality == 0) {
			newChem = new IdealChemical(db);
		} else {
			newChem = new RealChemical(db);

		}
		

		return newChem;

	}
////////Accessors//////////
	public double[] getZ() {
		return z;
	}

	public int getChemNum() {
		return this.chemNum;
	}

	public Chemical[] getNewChems() {
		return this.additionalChems.clone();
	}
////////////////////////////////////////
	//performs error handling on inputs
	public int getUserInput(MenuReadout mr) {
		int i = -1;
		InterfaceHandler tQ = new GenericQuestion(mr.getArray());
		i = userErrorHandling(tQ, input, mr);
		System.out.println("User selected: " + i + "\n");
		return i;
	}

	public double getUserInputDouble(MenuReadout mr, double ub, double lb) {
		double d = -999.;
		InterfaceHandler tQ = new GenericQuestion(ub, lb);
		d = doubleErrorHandling(tQ, input, mr, ub, lb);
		System.out.println("User selected: " + d + "\n");
		return d;
	}
}
