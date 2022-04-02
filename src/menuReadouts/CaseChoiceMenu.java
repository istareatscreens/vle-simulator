package menuReadouts;

public class CaseChoiceMenu implements MenuReadout{//used to set up error handling and questions if needed

	public String introReadout() {
		return "Welcome to the Fantastic Flash Separator Extraordinaire!\n";
	}

	public String questionReadout() {
		return "Please select your Case from the list below:\\n";
	}

	public String optionReadout() {
		return "1. Case 1:\n   Known values: Tank pressure, Operating temperature, Feed composition and flowrate\n"
				+ "2. Case 2:\n   Known values: Tank pressure, Feed temperature, Feed composition and flowrate\n"
				+ "3. Case 3:\n   Known values: Tank pressure, Flash temperature, Feed composition and flowrate\n";
	}
	public int[] getArray() {
		int[] temp = {1, 2, 3};
		return temp;
	}
}