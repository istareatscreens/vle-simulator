package menuReadouts;

public class BIPMenu implements MenuReadout{//used to set up error handling and questions if needed

	public String introReadout() {
		return "You have chosen \n";
	}

	public String questionReadout() {
		return "Would you like to set Binary Interaction coefficients to 0?\n";
	}

	public String optionReadout() {
		return "1. No (more accurate)\n2. Yes (more efficient)\n";
	}

	public int[] getArray() {
		int[] temp = {1, 2};
		return temp;
	}
}
