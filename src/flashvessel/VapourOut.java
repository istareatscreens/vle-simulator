package flashvessel;

//Vapour out stream stores vapour chemical state objects
public class VapourOut extends Stream {

	public VapourOut(String ID, String[] chemicals) {
		super(ID, -1, new double[chemicals.length], chemicals);
		setLiquidToZeroAndMoleFrac();
	}

	private void setLiquidToZeroAndMoleFrac() {
		for(String name: this.composition.keySet()){
			super.setLiquidFraction(name, 0.0);
			super.setMoleFraction(name, 0);
		}
	}

	// Over written methods
	//doesnt make sense to have liquid in vapour outlet
	public void setLiquidFraction(double[] values) {
		System.out.println("cannot have liquid in vapour outlet");
	};

	public void setLiquidFraction(String name, double value) {
		System.out.println("cannot have liquid in vapour outlet");
	};

}
