package userinput;

import chemsDatabase.*;
import flashvessel.Vessel;
import fluidpackages.FluidPackage;
import numericalmethods.RiddersMethod;
import numericalmethods.RootFindingMethod;
import solver.Solver;
import fluidpackages.*;

import java.util.ArrayList;

import cases.*;
import chemicals.*;
import main.GenericMethods;

public class BlueprintReader {

	private Blueprint bp;
	private ArrayList<Chemical> chemsUsed = new ArrayList<Chemical>();//array of chemicals used in the system
	private FluidPackage fp;
	private double temp;//temperature of the system
	private double[][] TcPc;//an array which contains the critical temp, critical pressure and acentric factor for all of the chemicals
	private kijValues kij;//kij values used for peng-robinson
	private Case caseUsed;
	private RootFindingMethod rfm;
	private int count = 0;
	private ArrayList<Chemical> addedChems = new ArrayList<Chemical>();//an array of custom-made chemicals
	private Chemical[] finalChems;//a master chemical list made into a static array by combining the two arrayLists
	private double[] finalComp;//the molar composition of the feed
	private ArrayList <Chemical> idealCpChems = new ArrayList<Chemical>();
	// copy constructor
	public BlueprintReader(Blueprint bp) {
		this.bp = bp;
	}
//primary method used to take data from the blueprint object and build all of the necessary objects
	public Vessel runBlueprintReader() {
		//switch to file in
		if (this.bp.getTextChoice() == 1) {
			System.out.println("Text loader");
		}
//size the flowIndex array
		int[] flowIndex = new int[this.bp.getFlowComp().length];
		flowIndex = this.bp.getFlowComp().clone();
//checks each point in the array to see if the chemical is present, if so it makes it and adds it to the list of chems used
		//a Database object holds chemical info and is used to make the chemical objects
		for (int i = 0; i < flowIndex.length; i++) {
			if (flowIndex[i] == 1) {
				switch (i) {
				case 0:
					EthaneData e = new EthaneData();
					Database ed = new Database(e);
					chemSwitch(this.bp.getFluidPckgChoice(), ed);

					break;
				case 1:
					PentaneData p = new PentaneData();
					Database pd = new Database(p);
					chemSwitch(this.bp.getFluidPckgChoice(), pd);
					// create Pentane
					break;
				case 2:
					HexaneData h = new HexaneData();
					Database hd = new Database(h);
					chemSwitch(this.bp.getFluidPckgChoice(), hd);
					// create Hexane
					break;
				case 3:
					CyclohexaneData c = new CyclohexaneData();
					Database cd = new Database(c);
					chemSwitch(this.bp.getFluidPckgChoice(), cd);
					// create Cyclo
					break;
				case 4:
					WaterData w = new WaterData();
					Database wd = new Database(w);
					chemSwitch(this.bp.getFluidPckgChoice(), wd);
					// create Water
					break;
				case 5:
					NitrogenData n = new NitrogenData();
					Database nd = new Database(n);
					chemSwitch(this.bp.getFluidPckgChoice(), nd);
					// create Nitrogen
					break;
				}
			}
		} // end of for
		if(this.bp.getIdealCp()==2) {
		for (int i = 0; i < flowIndex.length; i++) {
			if (flowIndex[i] == 1) {
				switch (i) {
				case 0:
					EthaneData e = new EthaneData();
					Database ed = new Database(e);
					chemSwitchIdeal(ed);

					break;
				case 1:
					PentaneData p = new PentaneData();
					Database pd = new Database(p);
					chemSwitchIdeal(pd);					// create Pentane
					break;
				case 2:
					HexaneData h = new HexaneData();
					Database hd = new Database(h);
					chemSwitchIdeal(hd);					// create Hexane
					break;
				case 3:
					CyclohexaneData c = new CyclohexaneData();
					Database cd = new Database(c);
					chemSwitchIdeal(cd);					// create Cyclo
					break;
				case 4:
					WaterData w = new WaterData();
					Database wd = new Database(w);
					chemSwitchIdeal(wd);					// create Water
					break;
				case 5:
					NitrogenData n = new NitrogenData();
					Database nd = new Database(n);
					chemSwitchIdeal(nd);					// create Nitrogen
					break;
				}
			}
		} // end of for
		}
		if(this.bp.getIdealCp()==2) {
			this.idealCpChems.addAll(this.bp.getIdealChems());
		}
//joins the two arrayLists of chemicals
		this.addedChems = this.bp.getNewChems();
		this.chemsUsed.addAll(this.addedChems);
//makes the TcPc array
		makeTemp(bp.getCaseChoice());
		if(this.bp.getIdealCp()==1) {
		if (this.bp.getFluidPckgChoice() == 0) {
			this.TcPc = null;
		} else {
			this.TcPc = new double[3][this.chemsUsed.size()];
			this.TcPc = GenericMethods.clone2Ddoublearray(makeCriticalArray(this.chemsUsed, this.bp.getFluidPckgChoice()));
		}
	}else {
		
			this.TcPc = new double[3][this.idealCpChems.size()];
			this.TcPc = GenericMethods.clone2Ddoublearray(makeCriticalArray(this.idealCpChems, 1));
		
	}
//makes kij values if necessary
		if(this.bp.getIdealCp()==1) {
		if (this.bp.getFluidPckgChoice() == 1) {
			if (this.bp.getBIPChoice() == 2) {
				this.kij = new kijValues(this.chemsUsed, false);
			} else {
				this.kij = new kijValues(this.chemsUsed, true);

			}
		}
		} else if(this.bp.getIdealCp()==2) {
			if (this.bp.getBIPChoice() == 2) {
				this.kij = new kijValues(this.idealCpChems, false);
			} else {
				this.kij = new kijValues(this.idealCpChems, true);

			}
	}else {
			this.kij = null;
		}
//makes the arrayLists into basic arrays
		this.finalChems = new Chemical[this.chemsUsed.size()];
		for (int i = 0; i < this.chemsUsed.size(); i++) {
			this.finalChems[i] = this.chemsUsed.get(i);

		}
		this.finalComp = new double[this.chemsUsed.size()];
		for (int i = 0; i < this.chemsUsed.size(); i++) {
			this.finalComp[i] = this.bp.getChemComp().get(i);

		}
//makes a fluid package based on user input
		switch (bp.getFluidPckgChoice()) {
		case 0:
			RouletsLaw rl = new RouletsLaw(this.temp, this.bp.getTankPress(), this.finalChems, this.finalComp);
			this.fp = new RouletsLaw(rl);
			break;
		// make roulets law
		case 1:
			PengRobinson pr = new PengRobinson(this.temp, this.bp.getTankPress(), this.TcPc[0], this.TcPc[1],
					this.TcPc[2], this.bp.getFlowRate(), this.finalComp, this.finalChems, this.kij, makeBpoint(this.finalChems), makeIsGas(this.finalChems));
			this.fp = new PengRobinson(pr);
			break;
		// make pengrobinson
		case 2:
			WilsonsCorrelation wc = new WilsonsCorrelation(this.temp, this.bp.getTankPress(), this.TcPc[0],
					this.TcPc[1], this.TcPc[2], this.finalComp,makeBpoint(this.finalChems), makeIsGas(this.finalChems));
			this.fp = new WilsonsCorrelation(wc);
			break;
		// make wilson
		}
//makes a rootfinding method object
		rfm = new RiddersMethod(0., this.temp);
//makes the case object depending on user input
		switch (this.bp.getCaseChoice()) {
		case 1:
			if (this.bp.getIdealCp() == 1) {
				this.caseUsed = new Case1(this.temp, this.bp.getTankPress(), this.bp.getFlowRate(), this.finalComp,
						this.fp, transformIdeality(this.bp.getFluidPckgChoice()), this.finalChems);
			} else {
				this.caseUsed = new Case1(this.temp, this.bp.getTankPress(), this.bp.getFlowRate(), this.finalComp,
						this.fp, transformIdeality(this.bp.getFluidPckgChoice()), this.finalChems, this.TcPc[0], this.TcPc[1],
						this.TcPc[2], this.kij);
			}
			break;
		case 2:
			rfm.setBounds(1, this.temp);
			if (this.bp.getIdealCp() == 1) {
				this.caseUsed = new Case2(this.temp, this.bp.getTankPress(), this.bp.getFlowRate(), this.finalComp,
						this.fp, transformIdeality(this.bp.getFluidPckgChoice()), this.finalChems, this.rfm,this.kij, makeBpoint(this.finalChems), makeIsGas(this.finalChems));
			} else {
				this.caseUsed = new Case2(this.temp, this.bp.getTankPress(), this.bp.getFlowRate(), this.finalComp,
						this.fp, transformIdeality(this.bp.getFluidPckgChoice()), this.finalChems, this.rfm, this.kij, this.TcPc[1],
						this.TcPc[0], this.TcPc[2],makeBpoint(this.finalChems), makeIsGas(this.finalChems));
			}
			break;
		case 3:
			if (this.bp.getIdealCp() == 1) {
				this.caseUsed = new Case3(this.temp, this.bp.getTankPress(), this.bp.getFlowRate(), this.finalComp,
						this.fp, transformIdeality(this.bp.getFluidPckgChoice()), this.finalChems, this.rfm);
			} else {
				this.caseUsed = new Case3(this.temp, this.bp.getTankPress(), this.bp.getFlowRate(), this.finalComp,
						this.fp, transformIdeality(this.bp.getFluidPckgChoice()), this.finalChems, this.rfm, this.TcPc[0], this.TcPc[1],
						this.TcPc[2], this.kij);
			}
			break;
		}
//returns the vessel object with all solved values
		return this.caseUsed.getVessel();

	}
//makes chemicals from the default list, with different types of chemicals depending on the choice of ideality
	public void chemSwitch(int ideal, Database d) {
		switch (ideal) {
		case 0:
			IdealChemical c = new IdealChemical(d);
			this.chemsUsed.add(new IdealChemical(c));
			this.count++;
			break;
		case 1:
			Structure s = new Structure(d);
			this.chemsUsed.add(new Structure(s));
			this.count++;
			break;
		case 2:
			RealChemical r = new RealChemical(d);
			this.chemsUsed.add(new RealChemical(r));
			this.count++;
			break;
		default:
			System.out.print("Error: Invalid ideality! Oh no! Nothing is real, everything is forbidden.");
		}

	}
	public void chemSwitchIdeal(Database d) {
		
		
			Structure s = new Structure(d);
			this.idealCpChems.add(new Structure(s));
			this.count++;
			
		
		

	}
//checks the amount of chems used in the system from the array index
	public int checkChemLength(int[] array) {
		int count = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == 1) {
				count++;
			}
		}
		return count;
	}
//sets the temperature depending on the case
	public void makeTemp(int i) {
		switch (i) {
		case 1:
			this.temp = this.bp.getOpTemp();
			break;
		case 2:
			this.temp = this.bp.getFeedTemp();
			break;
		case 3:
			this.temp = this.bp.getFlashTemp();
			break;
		default:
			break;
		}
	}
	public double[] makeBpoint(Chemical[] chems) {
		double[] boilingPoint = new double[chems.length];
		for(int i=0; i <chems.length;i++) {
			boilingPoint[i]= chems[i].getBoilingPoint();
		}
		return boilingPoint;
	}
	public boolean[] makeIsGas(Chemical[] chems) {
		boolean[] isItGas = new boolean[chems.length];
		for(int i=0; i <chems.length;i++) {
			isItGas[i]= chems[i].isGas();
		}
		return isItGas;
	}
	public int transformIdeality(int i) {
		int out = 0;
		switch(i) {
		case 0:
			out = 0;
			break;
		case 1:
			out = 2;
			break;
		case 2:
			out = 1;
			break;
		}
		return out;
	}
//creates the TcPc array from the array of chemicals
	public double[][] makeCriticalArray(ArrayList<Chemical> chemsUsed2, int ideality) {
		double[][] array = new double[3][chemsUsed2.size()];

		for (int j = 0; j < chemsUsed2.size(); j++) {
			switch (ideality) {
			case 0:
				array[0][j] = new RealChemical((RealChemical) new Chemical((Chemical) chemsUsed2.get(j))).getTc();
				break;
			case 1:
				array[0][j] = new Structure((Structure) chemsUsed2.get(j)).getTc();
				break;
			case 2:
				array[0][j] = new RealChemical((RealChemical) chemsUsed2.get(j)).getTc();
				break;

			}

		}
		for (int i = 0; i < chemsUsed2.size(); i++) {
			switch (ideality) {
			case 0:
				array[1][i] = new RealChemical((RealChemical) new Chemical((Chemical) chemsUsed2.get(i))).getPc();
				break;
			case 1:
				array[1][i] = new Structure((Structure) chemsUsed2.get(i)).getPc();
				break;
			case 2:
				array[1][i] = new RealChemical((RealChemical) chemsUsed2.get(i)).getPc();
				break;

			}
		}
		for (int i = 0; i < chemsUsed2.size(); i++) {
			switch (ideality) {
			case 0:
				array[2][i] = new RealChemical((RealChemical) new Chemical((Chemical) chemsUsed2.get(i))).getAF();
				break;
			case 1:
				array[2][i] = new Structure((Structure) chemsUsed2.get(i)).getAF();
				break;
			case 2:
				array[2][i] = new RealChemical((RealChemical) chemsUsed2.get(i)).getAF();
				break;

			}
		}

		return array;
	}
}
