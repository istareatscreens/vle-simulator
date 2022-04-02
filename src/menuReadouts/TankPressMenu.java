package menuReadouts;

public class TankPressMenu implements MenuReadout{//used to set up error handling and questions if needed

	public String introReadout() {
		return "";
	}

	public String questionReadout() {
		return "Please input the Tank Pressure for the system in kPa.\n";
	}

	public String optionReadout() {
		return "";
	}

	public int[] getArray() {
		int[] temp = {1,2};
		return temp;
	}
}
