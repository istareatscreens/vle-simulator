package menuReadouts;

public class FluidPckgMenu implements MenuReadout{//used to set up error handling and questions if needed
	
	public String introReadout() {
		return "You have chosen to use a Non-Ideal solution.\n";
	}

	public String questionReadout() {
		return "Which of the following fluid packages would you like to use?\n";
	}

	public String optionReadout() {
		return "1. Peng-Robinson (higher accuracy)\n2. Wilson (more efficient)\n";
	}

	public int[] getArray() {
		int[] temp = {1, 2};
		return temp;
	}
}
