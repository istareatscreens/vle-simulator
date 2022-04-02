package userinput;

import java.util.ArrayList;
import java.util.Scanner;

import chemicals.Chemical;
import chemicals.Structure;

public class ChemChoice {

	protected ArrayList<String> chems = new ArrayList<String>();// names of the chemicals
	protected int[] chemIndex;
	protected ArrayList<Double> chemComp = new ArrayList<Double>();// composition of the feed
	protected double[] usedChemComp;
	protected double flowRate;// flow rate of feed
	protected ArrayList<Chemical> newChems = new ArrayList<Chemical>();// custom chemicals
	protected ArrayList<Chemical> idealChems = new ArrayList<Chemical>();// custom chemicals

	protected int newChoice = 0;// counter to see if they want to make another chemical

	protected double counter = 0;

	public ChemChoice() {

	}

	// primary method
	public void runChemChoice(int ideality, int realCp) {
		Scanner input = new Scanner(System.in);
		//adds the default names to the arraylist of names
		chems.add("Ethane");
		chems.add("Pentane");
		chems.add("Hexane");
		chems.add("Cyclohexane");
		chems.add("Water");
		chems.add("Nitrogen");

		System.out.println("Would you like to add a custom chemical?\n1. Yes\n2. No");
		this.newChoice = input.nextInt();
		while (this.newChoice == 1) {
			NewChem nc = new NewChem();
			Chemical c = nc.MakeNewChem(ideality, realCp);//creates a new custom chemical
			if(realCp==2) {
				Structure cI =new Structure((Structure)c);
				this.idealChems.add(cI);
				
			}
			this.chems.add(c.getName());//adds the custom name to the array of names
			newChems.add(c);
			System.out.println("Would you like to add another chemical?\n1. Yes\n2. No");
			this.newChoice = input.nextInt();

		}
//sizes the chem index array
		this.chemIndex = new int[this.chems.size()];
//collects the flow rate
		System.out.println("Please input the total Flow Rate of the system in Mol/s:");
		this.flowRate = input.nextDouble();
		int count = 0;
//asks whether each chemical is present, if yes it asks for the mole component
		for (int i = 0; i < this.chems.size(); i++) {
			if (counter <= 1.000000000001) {//checks to see if the mol component total exceeds 1
				System.out.println("\nIs there " + this.chems.get(i) + " present in the system?");
				System.out.println("1. Yes\n2. No\n");
				this.chemIndex[i] = input.nextInt();
				if (this.chemIndex[i] == 1) {
					System.out.println("What is the Mole Fraction of " + this.chems.get(i));
					this.chemComp.add(input.nextDouble());
					counter += this.chemComp.get(count);
					count++;
				}
			} else {//reruns the program if the mole comps don't add up properly
				counter = 0.;
				System.out.println(
						"Error: Mole Fractions do not add up to 1. Please re-enter your chemical information.");
				runChemChoice(ideality, realCp);
			}

		}

	}
//////////Accessors///////////////
	public int[] getChemIndex() {
		return this.chemIndex.clone();
	}

	public ArrayList<Double> getChemComp() {
		return this.chemComp;
	}

	public double getFlowRate() {
		return this.flowRate;
	}

}
