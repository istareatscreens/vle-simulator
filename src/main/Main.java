package main;

import java.util.Scanner;

import flashvessel.Vessel;
import userinput.Blueprint;
import userinput.BlueprintReader;
import userinput.MainMenu;
import userinput.NewChem;
import userinput.VesselWhisperer;
import fileIO.*;
import java.io.*;


public class Main {
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		//call main menu
//		new NewChem().MakeNewChem(2);
		MainMenu mM = new MainMenu();
		Blueprint bp = new Blueprint(mM.runMainMenu());
		BlueprintReader br = new BlueprintReader(bp);
		Vessel v = new Vessel(br.runBlueprintReader());
//		new VesselWhisperer().whispersOnTheWind(v, bp);test
		new DidVesselConverge(v);
		new PrintResults(v,bp);
		String fileID = null;
		System.out.println("Enter a file name \n");
		fileID=input.nextLine();
		BuildFile flashSepTextFile = new BuildFile(fileID);
		FileTemplate append2FlashSepTextFile = new FileTemplate(fileID, true, v, bp);
		
		try {
			flashSepTextFile.writeToFile("Welcome to the output text file. \n");
			append2FlashSepTextFile.writeWelcomeMessage(fileID, true);
			append2FlashSepTextFile.writeCaseOneOutput(fileID, true);
		}
		catch(FileNotFoundException e) {
			System.out.println("Welcome to the output text file. \n");
			e.printStackTrace();
			System.exit(0);
		}
		catch(IOException e) {
			System.out.println("Error! IO exception");
			e.printStackTrace();
			System.exit(0);
		}
		
		input.close();
		
		System.out.println("Thank you please check the project directory for"+fileID+".txt");
		
	}
		
}
	

