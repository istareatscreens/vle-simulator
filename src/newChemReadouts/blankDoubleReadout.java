package newChemReadouts;

import menuReadouts.MenuReadout;
//generic version for doubles
public class blankDoubleReadout implements MenuReadout{//used to set up error handling and questions if needed

	public String introReadout() {
		return "";
	}

	public String questionReadout() {
		return "";
	}

	public String optionReadout() {
		return "";
	}

	public int[] getArray() {
		int[] temp = {1,2};
		return temp;
	}
}