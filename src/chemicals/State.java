package chemicals;

import main.GenericMethods;

//Stores vapour and liquid fraction for each chemical in a stream (inlet, outlet, vapour)
public class State {
	private String name;
	private double x;
	private double y;
	private double moleFrac;
	public State(String name, double moleFrac) {
		this.moleFrac = moleFrac;
		this.name = name;
	}
	
	//Copy constructor
	public State(State state){
		this.name = state.name;
		this.x = state.x;
		this.y = state.y;
		this.moleFrac = state.moleFrac;
	}

	// mutators
	public void setX(double x) {
		if (isGreaterThanZero(x))
			this.x = x;
	}

	public void setY(double y) {
		if (isGreaterThanZero(y))
			this.y = y;
	}

	public void setMoleFraction(double moleFrac) {
		if (isGreaterThanZero(moleFrac))
			this.moleFrac = moleFrac;
	}
	//makes sure value is greater than 0
	public boolean isGreaterThanZero(double value) {
		if ((value > -GenericMethods.getErrorFactor()) && (value < (1+GenericMethods.getErrorFactor()))) {
			return true;
		} else {
			return false;
		}
	}
	//gets specific things depending on what the user wants as Object, which is then converted to double, or string
	public void setSpecificParameterValue(int i, double value) {
		switch (i) {

		case 1:
			setX(value);
			break;
		case 2:
			setY(value);
			break;
		case 3:
			setMoleFraction(value);
			break;
		default:
			System.out.println("Improper setting to State");
		}
	}

	public void setName (String name) {
		this.name = name;
	}

	// accessors

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getMoleFraction() {
		return this.moleFrac;
	}

	//returns specific things depending on what the user wants as Object, which is then converted to double, or string
	public Object getSpecificParameterValue(int i) {
		switch (i) {

		case 1:
			return (Double)getX();
		case 2:
			return (Double)getY();
		case 3:
			return (Double)getMoleFraction();
		case 4:
			return this.name;
		default:
			System.out.println("Improper access to State");
			return null;
		}
	}

}
