package menuReadouts;

public class FeedTempMenu  implements MenuReadout{//used to set up error handling and questions if needed

	public String introReadout() {
		return "";
	}

	public String questionReadout() {
		return "Please input the Feed Temperature for the system in degrees Kelvin.\n";
	}

	public String optionReadout() {
		return "";
	}

	public int[] getArray() {
		int[] temp = {1,2};
		return temp;
	}
}