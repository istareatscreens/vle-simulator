package menuReadouts;

public class TextMenu implements MenuReadout{//used to set up error handling and questions if needed

	public String introReadout() {
		return "Welcome to the Fantastic Flash Separator Extraordinaire!\n";
	}

	public String questionReadout() {
		return "Would you like to load your parameters via text file, or input them manually?\n";
	}

	public String optionReadout() {
		return "1. Text file\n2. Manual input\n";
	}

	public int[] getArray() {
		int[] temp = {1, 2};
		return temp;
	}
}
