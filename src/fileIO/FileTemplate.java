package fileIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import userinput.Blueprint;
import flashvessel.Vessel;
import java.util.Formatter;


public class FileTemplate extends BuildFile{
	
	private Vessel vessel;
	private Blueprint blp;

	
	public FileTemplate(String fileName, boolean appendToFile, Vessel vessel, Blueprint blp){
		super(fileName, appendToFile);
		this.vessel= new Vessel(vessel);
		this.blp=new Blueprint(blp);
	}
	//
	public  Vessel getVessel(){
		return this.vessel;
	}
	
	public void setVessel(Vessel vessel){
		this.vessel=vessel;
	}
	
	
	public void writeWelcomeMessage(String fileName, boolean appendToFile) throws IOException{
		fileName=super.getName();
		appendToFile=super.getAppendToFile();
		java.util.Date date = new java.util.Date();
		
		try{
		writeToFile(date.toString()+"\n");	
		writeToFile("_________________________________________________________________________________________________________________________________________________________\n");
		writeToFile("\t\t\t\t\t"+"CHG4343 COMPUTER-AIDED DESIGN IN CHEMICAL ENGINEERING"+"\n");
		writeToFile("\t\t\t\t\t\t"+"FALL 2017: DESIGN OF A FLASH SEPARATOR "+"\n");
		writeToFile("\t\t\t\t\t\t\t"+"University of Ottawa"+"\n");
		writeToFile("\t\t\t\t\t"+"Department of Chemical and Biological Engineering "+"\n");
		writeToFile("\t\t\t\t\t\t\t"+"Designed for Dr Taylor"+"\n");
		writeToFile("\t\t\t\t\t\t\t\t"+"Group 25"+"\n");
		writeToFile("\t"+"STEVE CHARBONNEAU (6303742); KARMAN DADIALA (7547130);  SHAIL JOSHI (7282674); FELIPE DE LARRINAGA(6873582); VICTOR ROMANOWICH (7253633)"+"\n");
		writeToFile("_________________________________________________________________________________________________________________________________________________________\n");
		}
		catch(FileNotFoundException e){
			System.out.println("Error! File not found exception. \n");
			e.printStackTrace();
			System.exit(0);
		}
		catch( IOException e){
			System.out.println("Error! IO exception \n");
			e.printStackTrace();
			System.exit(0);
		}}
		
		public void writeCaseOneOutput(String fileName, boolean appendToFile) throws IOException{
			fileName=super.getName();
			appendToFile=super.getAppendToFile();
			
			try{	
				
			//case choice	
			writeToFile("Thank you for choosing case \n"+blp.getCaseChoice());
			
			writeToFile("Given the tank pressure, a specified constant operating temperature, the feed composition and flowrate. \n");
			writeToFile("The program calculates the vapor and liquid product compositions, flowrates, as well as heat to maintain the operating temperature as specified. \n");
			
			//Ideal/ non ideal
			writeToFile("This file has output the simulation for the "+blp.getIdealChoice()+" case \n");
			
			//fluid package
			writeToFile("The "+(blp.getFluidPckgChoice()+ " fluid package is being used as the thermodynamic model \n"));
			
			//bips
			writeToFile("Binary interaction parameters choice:"+blp.getBIPChoice()+" \n");
			
			//operating temperature
			writeToFile("The operating temperature for the vessel is: "+vessel.getTemperature()+"\t [K] \n");
			
			//tank pressure
			writeToFile("The tank pressure in the vessel is:"+vessel.getPressure()+"\t [kPa] \n");
			
			//feed temperature
			writeToFile("The feed temperature for the vessel is:"+vessel.getFeedT()+"\t [K] \n");
			
			//flash temperature
			writeToFile("The flash temperature for the vessel is:"+blp.getFlashTemp()+"\t [K] \n");
			
			//CHEMICALS
			writeToFile("Inlet feed chemicals \n");
			for(int i=0; i<vessel.getAllChemicalsInStream()[0].length; i++){
			writeToFile("The flow rates of species:"+vessel.getAllChemicalsInStream()[0][i]+" "+vessel.getFlowRates()[0]+"\t [mols/s] \n");
			}
			
			//liquid outlets
			writeToFile("Liquid outlet \n");
			for(int i=0; i<vessel.getAllChemicalsInStream()[1].length; i++){
			writeToFile("The flow rates of species:"+vessel.getAllChemicalsInStream()[1][i]+" "+vessel.getFlowRates()[1]+"\t [mols/s] \n");
			}
			
			//vapour outlet
			writeToFile("Vapour outlet \n");
			for(int i=0; i<vessel.getAllChemicalsInStream()[2].length; i++){
			writeToFile("The flow rates of species:"+vessel.getAllChemicalsInStream()[2][i]+" "+vessel.getFlowRates()[2]+"\t [mols/s] \n");
			}
			
			//Q
			writeToFile("Q  heat of vessel \n");
			writeToFile("The heat Q of the vessel is:"+vessel.getQ()+"\t [kJ]");
			
			//Bubble point 
			writeToFile("Bubble point \n");
			writeToFile("The bubble point is:"+vessel.getBubblePP()+"\t [K]");
			
			//Dew point
			writeToFile("Dew point \n");
			writeToFile("The dew point is:"+vessel.getDewPP()+"\t [K]");
			
			}
			catch(FileNotFoundException e){
			System.out.println("Error! File not found exception. \n");
			e.printStackTrace();
			System.exit(0);
		}
		catch( IOException e){
			System.out.println("Error! IO exception \n");
			e.printStackTrace();
			System.exit(0);
		}
			
	}
}
