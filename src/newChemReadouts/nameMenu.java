package newChemReadouts;

import menuReadouts.MenuReadout;

public class nameMenu implements MenuReadout{//used to set up error handling and questions if needed

	public String introReadout() {
		return "";
	}

	public String questionReadout() {
		return "You have chosen to add a custom chemical.\\nPlease input the name of the chemical below:\\n";
	}

	public String optionReadout() {
		return "";
	}

	public int[] getArray() {
		int[] temp = {1, 2};
		return temp;
	}
}
