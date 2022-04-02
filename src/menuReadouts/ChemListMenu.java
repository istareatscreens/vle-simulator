package menuReadouts;

public class ChemListMenu implements MenuReadout{//used to set up error handling and questions if needed

	public String introReadout() {
		return "You have chosen\n"; //need to add in a case number
	}

	public String questionReadout() {
		return "Would you like to manually add a new chemical to the system?\n";
	}

	public String optionReadout() {
		return "1. Yes\n2. No\n";
	}

	public int[] getArray() {
		int[] temp = {1, 2};
		return temp;
	}
}