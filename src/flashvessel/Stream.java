package flashvessel;
//Stores common methods used by every
import chemicals.State;
import main.GenericMethods;
import chemicals.Chemical;
import java.util.LinkedHashMap;

//Parent of all stream objects (inlet stream, vapour stream, liquid stream)
public class Stream {

	protected LinkedHashMap<String, State> composition = new LinkedHashMap<String, State>();
	protected Chemical[] chemicals;
	protected String ID;
	protected double flowRate;
//Constructor
	public Stream(String ID, double flowRate, double[] moleComposition, String[] chemicals) {
		this.ID = ID;
		this.flowRate = flowRate;
		this.chemicals = new Chemical[chemicals.length];
		double[] temp = moleComposition.clone();
		for (int i = 0; chemicals.length > i; i++) {
			composition.put(chemicals[i], new State(chemicals[i], temp[i]));
		}		
	}
	//Copy Constructor
	public Stream(Stream stream){
		this.ID = stream.ID;
		this.flowRate = stream.flowRate;
		for(String name: stream.composition.keySet()){
			this.composition.put(name,new State(stream.composition.get(name)));
		}
	}
	
	// Accessers

	public String getChemicalName(int order){
		int i=1;
		for(String name: this.composition.keySet()){
			if(order == i){
				return name;
			}
		}
		return "Invalid Order Number";
	}
	
	public double getFlowRate(){
		return this.flowRate;
	}
	
	public int getNumberOfChemicalsInStream(){
		return this.composition.size();
	}
	
	public double getMoleFraction(String chemical) {
		return composition.get(chemical).getMoleFraction();
	}

	public double getGasFraction(String chemical) {
		return composition.get(chemical).getY();
	}

	public double getLiquidFraction(String chemical) {
		return composition.get(chemical).getX();
	}

	// all properties accessors
	public String getID(){
		return this.ID;
	}
	
	public String[] getAllChemicalsInStream(){
		return GenericMethods.downCastObjectArrayToString(getSpecificSetOfStateValues(4));
	}
	
	public Double[] getMoleComposition() {
		return downCastOtoDArray((getSpecificSetOfStateValues(3)));
	}

	public Double[] getGasComposition() {
		return downCastOtoDArray((getSpecificSetOfStateValues(2)));
	}

	public Double[] getLiquidComposition() {
		return downCastOtoDArray(getSpecificSetOfStateValues(1));
	}
	//Generic method to reduce amount of code can return an array of objects or an array of 
	protected Object[] getSpecificSetOfStateValues(int factor) {
		Object[] comp = new Object[this.composition.size()];
		int i = 0;
		for (String chemical : this.composition.keySet()) {
			comp[i] = this.composition.get(chemical).getSpecificParameterValue(factor);
			i++;
		}
		return comp;
	}
	
	//downcasts Double array using generic
	protected Double[] downCastOtoDArray(Object[] array){
		return GenericMethods.downCastObjectArrayToDouble(array);
	}
	
	//Down Casting from Object to double Array

	// mutators
	public void setFlowRate(double flowRate){
		this.flowRate = flowRate;
	}
	
	//should probably never be used
	public void setGasFraction(String name, double value) {
		if (isBetweenZeroAndOne(value)) {
			this.composition.get(name).setY(value);
		}
	}

	public void setLiquidFraction(String name, double value) {
		if (isBetweenZeroAndOne(value)) {
			this.composition.get(name).setX(value);
		}
	}

	public void setMoleFraction(String name, double value) {
		if (isBetweenZeroAndOne(value)) {
			this.composition.get(name).setMoleFraction(value);
		}
	}
	
	//Total mutator Change - change probably only way to do this
	public void setMoleFraction(double[] values) {
		setSpecificSetOfStateValues(3, values);
	}

	public void setGasFraction(double[] values) {
		setSpecificSetOfStateValues(2, values);
	}

	public void setLiquidFraction(double[] values) {
		setSpecificSetOfStateValues(1, values);
	}

	protected void setSpecificSetOfStateValues(int factor, double value[]) {
		//if (addsToOne(value)) {
			int i = 0;
			for (String name : this.composition.keySet()) {
				this.composition.get(name).setSpecificParameterValue(factor, value[i]);
				i++;
			}
		//}else{
			//could improve this error message
			//System.out.println("Sum of Fractions Do Not Equal to 1");
		//}
	}

	// checking to make sure states sum to 1
	protected boolean checkMoleFractions() {
		double sum = 0;
		for (String name : this.composition.keySet()) {
			sum += getMoleFraction(name);
		}
		return addsToOne(sum);
	}

	protected boolean addsToOne(double value) {
		if (Math.abs(value - 1) < GenericMethods.getErrorFactor())
			return true;
		return false;
	}
//checks freactions to see if they add to 1
	protected boolean addsToOne(double[] value) {
		double sum = 0;
		for (int i = 0; this.composition.size() > i; i++) {
			sum += value[i];
		}
		return addsToOne(sum);
	}
	//checkts to see if value is between 0 and 1
	protected boolean isBetweenZeroAndOne(double value) {
		if ((value < (1+GenericMethods.getErrorFactor())) && (value > -GenericMethods.getErrorFactor())) {
			return true;
		} else {
			return false;
		}
	}
	
	

}
