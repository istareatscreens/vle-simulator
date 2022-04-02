package flashvessel;
//Stores states for each chemical in the inlet feed to vessel
public class InletFeed extends Stream{

	public InletFeed(String ID, double flowRate, double[] moleComposition, String[] chemicals) {
		super(ID, flowRate, moleComposition, chemicals);
		setMoleFracToLiq(moleComposition);
	}
	
	//Feed assumption sub-cooled liquid
	private void setMoleFracToLiq(double[] comp){
			super.setLiquidFraction(comp);
			super.setMoleFraction(comp);
	}
	


}
