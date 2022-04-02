package userinput;
import java.util.Scanner;

import menuReadouts.*;

public class MainMenu extends UserUI {
	
	private Scanner input = new Scanner(System.in);
	
	
	Blueprint bp;
	ChemChoice cc;
//	BlueprintReader br;
	
	public MainMenu() {
		
	}
//primary method, gathers all datadata and assigns it to a blueprint object
	public Blueprint runMainMenu() {
		int ideality;
			this.bp = new Blueprint();
			
				this.bp.setCaseChoice(getUserInput(new CaseChoiceMenu()));//asks which case the user would like
				
				
			this.bp.setIdealChoice(getUserInput(new IdealMenu()));//asks whether the system solves ideally
			if(this.bp.getIdealChoice()==2) {
				this.bp.setFluidPckgChoice(getUserInput(new FluidPckgMenu()));//pick a fluid package
				if(this.bp.getFluidPckgChoice()==1) {
					this.bp.setBIPChoice(getUserInput(new BIPMenu()));//whether to set BIPs to 0
					}
				}
			this.bp.setIdealCp(getUserInput(new IdealCp()));//whether to use ideal or real heat capacities
			
			switch(this.bp.getCaseChoice()) {//collects temperature and pressure depending on the case chosen
			case 1:
				this.bp.setTankPress(getUserInputDouble(new TankPressMenu(), 1000, 0));
				this.bp.setOpTemp(getUserInputDouble(new OpTempMenu(), 1000, 0));
				break;
				//run case1menu
			case 2:
				this.bp.setTankPress(getUserInputDouble(new TankPressMenu(), 1000, 0));
				this.bp.setFeedTemp(getUserInputDouble(new FeedTempMenu(), 1000, 0));
				break;
				//run case2menu
			case 3:
				this.bp.setTankPress(getUserInputDouble(new TankPressMenu(), 1000, 0));
				this.bp.setFlashTemp(getUserInputDouble(new FlashTempMenu(), 1000, 0));
				break;
				//run case3menu
			default:
				System.out.println("Error: Invalid case.");
				break;
				
			}
			
			//makes a new chemChoice object
			this.cc = new ChemChoice();
			
			this.cc.runChemChoice(this.bp.getFluidPckgChoice(),this.bp.getIdealCp());//runs the chemChoice menu to get an array of chemicals used 
			System.out.println("Thank you! Now solving the system.");
			//translates the new info from chemChoice to blueprint
			this.bp.setNewChems(this.cc.newChems);
			if(this.bp.getIdealCp()==2) {
				this.bp.setIdealChems(this.cc.idealChems);
			}
			this.bp.setFlowComp(this.cc.getChemIndex());
			this.bp.setChemComp(this.cc.getChemComp());
			this.bp.setFlowRate(this.cc.getFlowRate());
			
			return this.bp;
			
	}//End of MainMenu
	
	//makes an infinite sized array
	public int[] makeArray(int... array) {
		return array;
	}
//performs the error handling for each input
	public int getUserInput(MenuReadout mr) {
		int i=-1;
		InterfaceHandler tQ = new GenericQuestion(mr.getArray());
		i = userErrorHandling(tQ, input, mr);
		System.out.println("User selected: " + i + "\n");
		return i;
	}
	public double getUserInputDouble(MenuReadout mr, double ub, double lb) {
		double d=-999.;
		InterfaceHandler tQ = new GenericQuestion(ub, lb);
		d = doubleErrorHandling(tQ, input, mr, ub, lb);
		System.out.println("User selected: " + d + "\n");
		return d;
	}
	//Accessor for the blueprint object
	public Blueprint getBlueprint() {
		return this.bp;
	}
	
}//end of MainMenu
