package main;

import flashvessel.Vessel;
import userinput.Blueprint;

public class PrintResults {

	public PrintResults(Vessel v, Blueprint bp){
		System.out.println("System is Solved!\n");
		//Pro
		System.out.println("Case: " + bp.getCaseChoice());
		System.out.println("Vessel Properties:");
		System.out.println("Vessel Temperature: "+v.getTemperature()+" K");
		System.out.println("Feed Temperature: "+v.getFeedT()+" K");
		System.out.println("Heat Transfer to Vessel (Q) "+v.getQ()+ " kJ" );	

		System.out.println("Bubble Point Pressure: "+v.getBubblePP() + " KPa");
		System.out.println("Dew Point Pressure: "+v.getDewPP() + " KPa");
		System.out.println("Vessel Pressure: "+v.getPressure() + " KPa\n");
		
		//Prints Inlet
		double sum=0;
		System.out.println(v.getStreamIDs()[0]);
		for(int i = 0; i < v.getMoleCompositions()[0].length; i++){
			System.out.println(v.getAllChemicalsInStream()[1][i]+": "+v.getMoleCompositions()[0][i]);
			sum +=v.getMoleCompositions()[0][i];
		}
		System.out.println("Sum of Fractions: "+sum+"\n");
		
		//prints Outlet Liquid
		sum=0;
		System.out.println(v.getStreamIDs()[1]);
		for(int i = 0; i < v.getMoleCompositions()[0].length; i++){
			System.out.println(v.getAllChemicalsInStream()[1][i]+": "+v.getLiquidComposition()[1][i]);
			sum +=v.getLiquidComposition()[1][i];
		}
		System.out.println("Sum of Fractions: "+sum+"\n");
		//prints Outlet Vapour
		sum=0;
		System.out.println(v.getStreamIDs()[2]);
		for(int i = 0; i < v.getMoleCompositions()[0].length; i++){
			System.out.println(v.getAllChemicalsInStream()[2][i]+": "+v.getGasCompositions()[2][i]);
			sum +=v.getGasCompositions()[2][i];
		}
		//Prints Sum of compositoon fractions
		System.out.println("Sum of Fractions: "+sum+"\n");
		
	}
	
	
}
