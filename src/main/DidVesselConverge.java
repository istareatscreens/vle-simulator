package main;

import flashvessel.Vessel;
//Checks if solution is logical if not program ends
public class DidVesselConverge {
	//Constructor
	public DidVesselConverge(Vessel v){
		if(v.getBubblePP() < v.getDewPP() || v.getFlowRates()[0] < 0 || v.getFlowRates()[0] < 0 || v.getFlowRates()[1]<0 || v.getFlowRates()[2]<0||v.getBubblePP()<0 || v.getDewPP() < 0 || v.getQ() < 0){
			System.out.println("System Did Not Converge! Try Different Conditions");
		}
		
	}
	
}
