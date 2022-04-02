package userinput;
import java.util.Scanner;

import menuReadouts.MenuReadout;

public class GenericQuestion extends InterfaceHandler {
//FOR INT
	public GenericQuestion(int[] array) {
		super(array, true);
	}
//FOR DOUBLE
	public GenericQuestion(double ub, double lb) {
		super(ub, lb, false);
	}

//prints out the menuReadout questions
	public int acquireUserInput(Scanner input, MenuReadout mr) {
		if (super.getFirstRun()) {
			System.out.println(mr.introReadout());
			super.setFirstRun(false);
		}
		System.out.println(mr.questionReadout());
		System.out.println(mr.optionReadout());
		// textOption user choice
		return input.nextInt();

	}
	
	public double acquireUserInputDouble(Scanner input, MenuReadout mr) {
		if (super.getFirstRun()) {
			System.out.println(mr.introReadout());
			super.setFirstRun(false);
		}
		System.out.println(mr.questionReadout());
		System.out.println(mr.optionReadout());
		// textOption user choice
		return input.nextDouble();

	}
//creates a toString
	public String toString() {
		String temp = super.toString();
		return temp;
	}

}