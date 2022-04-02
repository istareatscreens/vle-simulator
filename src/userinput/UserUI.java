package userinput;
import java.util.Scanner;

import menuReadouts.MenuReadout;

public abstract class UserUI {
//performs error handling on inputs
	protected int userErrorHandling(InterfaceHandler ih, Scanner input, MenuReadout mr) {
		int c=-1;
		do {
			try {
				do {
					c = ih.acquireUserInput(input, mr);
					if(!ih.checkInput(c)) {
						System.out.println(ih);
					}
				}while(!ih.checkInput(c));
			}catch(Exception e) {
				System.out.println(ih);
				input.next();
			}
		}while(!ih.checkInput(c));
		return c;
	
	}//end of userErrorHandling
	//performs error handling on double inputs
	protected double doubleErrorHandling(InterfaceHandler ih, Scanner input, MenuReadout mr, double ub, double lb) {
		double d=-999.;
		do {
			try {
				do {
					d = ih.acquireUserInputDouble(input, mr);
					if(!ih.checkDouble(d)) {
						System.out.println(ih);
					}
				}while(!ih.checkDouble(d));
			}catch(Exception e) {
				System.out.println(ih);
				input.next();
			}
		}while(!ih.checkDouble(d));
		return d;
	
	}//end of userErrorHandling
}
