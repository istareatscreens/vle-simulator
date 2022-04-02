package userinput;
import flashvessel.Vessel;

public class VesselWhisperer {//terminal output, unfinished

	public VesselWhisperer() {
		
	}
	
	public void whispersOnTheWind(Vessel v, Blueprint bp) {
		for(int i=0; i<v.getAllChemicalsInStream().length; i++) {
			System.out.println("For the " + v.getStreamIDs()[i] + " stream:");
			for(int j = 0; j<v.getAllChemicalsInStream()[i].length;j++) {
				System.out.printf(v.getAllChemicalsInStream()[i][j] + ": " + v.getGasCompositions()[i][j] + " " + v.getLiquidComposition()[i][j] + " " + v.getMoleCompositions()[i][j] + "\n");
			}
			switch(bp.getCaseChoice()) {
			case 1:
				//calculate the vapour and liquid product
//				compositions and flowrates, as well as heat to maintain the operating temperature
//				as specified
				
				System.out.println(v.getFlowRates());
				break;
			case 2:
//				 calculate the adiabatic flash temperature of the mixture, the vapour and liquid
//				 product compositions and flowrates of the adiabatic flash.
				break;
			case 3:
//				calculate the adiabatic feed temperature, the vapour and liquid compositions and
//				flowrates of the adiabatic flash.
				break;
			
			}
		}//end of outmost for
		
	}
	
	}

