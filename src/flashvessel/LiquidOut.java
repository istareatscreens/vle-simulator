package flashvessel;

//Stores State objects for each chemical in Liquid out stream
public class LiquidOut extends Stream {

	public LiquidOut(String ID, String[] chemicals) {
		super(ID, -1, new double[chemicals.length], chemicals);
		setVapourAndMoleFraqToZero();
	}

	private void setVapourAndMoleFraqToZero() {
		for(String name: this.composition.keySet()){
			//Sets to default assume pure liquid entering
			super.setGasFraction(name, 0.0);
			super.setMoleFraction(name, 0);
		}
	}

	// Over written methods
	//Doesnt make sense to add vapour to liquid outlet
	public void setVapourFraction(double[] values) {
		System.out.println("cannot have vapour in liquid outlet");
	};
	//Doesnt make sense to add vapour to liquid outlet
	public void setVapourFraction(String name, double value) {
		System.out.println("cannot have vapour in liquid outlet");
	};

}
