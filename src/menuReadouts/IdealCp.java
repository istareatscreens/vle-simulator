package menuReadouts;

public class IdealCp implements MenuReadout {//used to set up error handling and questions if needed

	public String introReadout() {
		return ""; 
	}

	public String questionReadout() {
		return "Would you like to use ideal or non-ideal heat capacities?";
	}

	public String optionReadout() {
		return "1. Ideal (More Efficient)\n2. Non-Ideal (More Accurate)\n";
	}

	public int[] getArray() {
		int[] temp = {1, 2};
		return temp;
	}
}
