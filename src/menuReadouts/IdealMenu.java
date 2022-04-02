package menuReadouts;

public class IdealMenu implements MenuReadout{//used to set up error handling and questions if needed

	public String introReadout() {
		return ""; 
	}

	public String questionReadout() {
		return "Would you like to use an ideal or a non-ideal solution?\n";
	}

	public String optionReadout() {
		return "1. Ideal\n2. Non-Ideal\n";
	}

	public int[] getArray() {
		int[] temp = {1, 2};
		return temp;
	}
}


