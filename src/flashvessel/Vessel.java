package flashvessel;


import main.GenericMethods;
//Stores all streams objects which hold state of each chemical
//Stores all important information and solutions to all cases
public class Vessel {
//instance variables
	private Stream[] streams = new Stream[3];
	private double temperature;
	private double pressure;
	private double Q;
	private double FeedTemp;
	private double bubblePoint;
	private double dewPoint;
	private String state;
//Copy constructor
	public Vessel(Vessel vessel){
		this.state = vessel.state;
		this.temperature = vessel.temperature;
		this.pressure = vessel.pressure;
		//clones streams
		for(int i = 0; i < streams.length; i++){
			this.streams[i] = new Stream(vessel.streams[i]);
		}
		this.Q = vessel.Q;
		this.FeedTemp = vessel.FeedTemp;
		this.bubblePoint = vessel.bubblePoint;
		this.dewPoint = vessel.dewPoint;
	}
	 //Vessel constructor
	public Vessel(double temperature, double pressure, double flowrate, double[] moleComposition, String[] chemicals) {
		setPressure(pressure);
		setTemperature(temperature);
		//Stores All stream objects that contain states objects associated with all chemicals (stores x/y fractions etc);
		this.state = "State has not been determined";
		this.streams[0] = new InletFeed("Inlet Vapour Stream", flowrate, moleComposition, chemicals);
		this.streams[1] = new LiquidOut("Outlet Liquid Stream", chemicals);
		this.streams[2] = new VapourOut("Outlet Vapour Stream", chemicals);
		//set to -1 if there is no Q set for the system
		this.Q=-1;
		this.FeedTemp = temperature;
		this.bubblePoint = -1;
		this.dewPoint = -1;
	}
	//Vessel constructor for addition of Q
	public Vessel(double temperature, double pressure, double flowrate, double[] moleComposition, String[] chemicals,double Q) {
		this(temperature, pressure, flowrate, moleComposition, chemicals);
		this.Q = Q;
		this.FeedTemp = temperature;
		this.bubblePoint = -1;
		this.dewPoint = -1;
		this.state="State has not been determined";
	}
	// mutators
	
	public void setBPP(double bpP){
		this.bubblePoint = bpP;
	}
	public void setDPP(double dpP){
		this.dewPoint = dpP;
	}
//Sets pressure  checks to see if its valid
	public void setPressure(double pressure) {
		if (pressure > -GenericMethods.getErrorFactor()) {
			this.pressure = pressure;
		} else {
			System.out.println("invalid Pressure");
			System.exit(-1);
		}
	}

	//mutators
	public void setTemperature(double temperature) {
			this.temperature = temperature;
	}

	public void setMoleFlowRates(double[][] values) {
		cycleThroughStreamsMutator(values, 1);
	}

	public void setGasComposition(double[][] values) {
		cycleThroughStreamsMutator(values, 2);
	}

	public void setLiquidComposition(double[][] values) {
		cycleThroughStreamsMutator(values, 3);
	}

	public void setMoleFlowRates(int indexStream, double[] values) {
		cycleThroughStreamsMutator(indexStream, values, 1);
	}

	public void setGasComposition(int indexStream, double[] values) {
		cycleThroughStreamsMutator(indexStream, values, 2);
	}

	public void setLiquidComposition(int indexStream, double[] values) {
		cycleThroughStreamsMutator(indexStream, values, 3);
	}

	public void setFlowRates(double[] values) {
		for (int i = 0; i < streams.length; i++) {
			streams[i].setFlowRate(values[i]);
		}
	}
	
	public String getState(){
		return this.state;
	}
	//returns the state of the chemical
	public void setState(int i){
		if(i==0){
			this.state = "Mixture is subcooled liquid vapour";
		}else if(i == 1){
			this.state = "Mixture is in V-L state";			
		}else if(i==2){
			this.state = "Mixture is in super-heated vapour";			
		}else{
			this.state="State has not been determined";
		}
	}
	//sets flow rates of streams
	public void setFlowRates(int indexStream, double values) {
		streams[indexStream].setFlowRate(values);
	}
	
	// mutator helper methods
	private void cycleThroughStreamsMutator(double[][] values, int factor) {
		for (int j = 0; values.length > j; j++) {
			mutatorSelection(factor, j, values[j]);
		}
	}
	//ggeneric mutator heper method
	private void cycleThroughStreamsMutator(int indexStream, double[] values, int factor) {
		mutatorSelection(factor, indexStream, values);
	}
	//sets stream values
	private void mutatorSelection(int factor, int streamIndex, double[] values) {
		switch (factor) {
		case 1:
			this.streams[streamIndex].setMoleFraction(values);
			break;
		case 2:
			this.streams[streamIndex].setGasFraction(values);
			break;
		case 3:
			this.streams[streamIndex].setLiquidFraction(values);
			break;
		default:
			System.out.println("Invalid mutator index Value Used");
			System.exit(-1);
		}
	}
//accessor
	public double getPressure() {
		return this.pressure;
	}

	public double getTemperature() {
		return this.temperature;
	}

	// Accessors
	public double[][] getMoleCompositions() {
		return cycleThroughStreamArray2DDouble(5, this.streams);
	}

	public double[][] getGasCompositions() {
		return cycleThroughStreamArray2DDouble(3, this.streams);
	}

	public double[][] getLiquidComposition() {
		return cycleThroughStreamArray2DDouble(4, this.streams);
	}

	public String[][] getAllChemicalsInStream() {
		return cycleThroughStreamArray2DString(2, this.streams);
	}

	public double[] getFlowRates() {
		return downCastOtoDArray(cycleThroughStreamArray(6, this.streams));
	}

	public String[] getStreamIDs() {
		return downCastOtoSArray(cycleThroughStreamArray(1, this.streams));
	}

	// helper methods for accessors

	// Downcasts Object array to double array;
	private double[] downCastOtoDArray(Object[] array) {
		return GenericMethods.castObjectArrayToPrimitiveDoubleArray(array);
	}

	private String[] downCastOtoSArray(Object[] array) {
		return GenericMethods.downCastObjectArrayToString(array);
	}

	// accessor switch for arrays;
	private Object[] getStreamArrays(int factor, Stream stream) {
		switch (factor) {
		case 2:
			return stream.getAllChemicalsInStream();
		case 3:
			return stream.getGasComposition();
		case 4:
			return stream.getLiquidComposition();
		case 5:
			return stream.getMoleComposition();
		default:
			System.out.println("Invalid factor value");
			return null;
		}
	}

	// accessor switch for non-arrays
	private Object getStreamValues(int factor, Stream stream) {
		switch (factor) {
		case 1:
			return stream.getID();
		case 6:
			return stream.getFlowRate();
		default:
			return null;
		}
	}

	private Object[] cycleThroughStreamArray(int factor, Stream[] array) {
		Object[] temp = new Object[array.length];
		for (int i = 0; i < array.length; i++) {
			temp[i] = getStreamValues(factor, array[i]);
		}
		return temp;
	}
	
	private double[][] cycleThroughStreamArray2DDouble(int factor, Stream[] array) {
		double[][] temp = new double[array.length][];
		for (int i = 0; i < array.length; i++) {
			temp[i] = downCastOtoDArray(getStreamArrays(factor, array[i]));
		}
		return temp;
	}
	
	public double getQ(){
		return Q;
	}
	
	public void setQ(double Q){
		this.Q = Q;
	}
	
	public double getFeedT(){
		return this.FeedTemp;
	}
	
	public void setFeedT(double FeedT){
		this.FeedTemp = FeedT;
	}
	
	public void setDewPP(double dp){
		this.dewPoint = dp;
	}
	
	public void setBubblePP(double bp){
		 this.bubblePoint = bp;
	}

	
	public double getDewPP(){
		return this.dewPoint;
	}
	
	public double getBubblePP(){
		return this.bubblePoint;
	}


	private String[][] cycleThroughStreamArray2DString(int factor, Stream[] array) {
		String[][] temp = new String[array.length][];
		for (int i = 0; i < array.length; i++) {
			temp[i] = downCastOtoSArray(getStreamArrays(factor, array[i]));
		}
		return temp;
	}
	
	
}