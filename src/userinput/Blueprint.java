package userinput;

import java.util.ArrayList;

import chemicals.Chemical;
import chemicals.Structure;

public class Blueprint {
	
	////////////Instance Variables//////////////
	private int caseChoice = 0;
	private int textChoice = 0;
	private int idealChoice = 0;
	private int fluidPckgChoice = 0;
	private int BIPChoice = 0;
	private int chemAddChoice = 0;
	private double opTemp = 0;
	private double tankPress = 0;
	private double feedTemp = 0;
	private double flashTemp = 0;
	private double flowRate = 0;
	private int[] flowComp;
	private ArrayList<Double> chemComp;
	private int chemNum;
	private ArrayList<Chemical> newChems = new ArrayList <Chemical>();
	private ArrayList<Chemical> idealChems = new ArrayList <Chemical>();

	
	

	private int idealCp;
	private double[] newZ = new double[1];
	/////////////////////////////////////
	
	
	
	public Blueprint() {
		
	}
	/////////////Constructor//////////////////
	public Blueprint(Blueprint bp) {
		this.textChoice = bp.textChoice;
		this.caseChoice = bp.caseChoice;
		this.idealChoice = bp.idealChoice;
		this.fluidPckgChoice = bp.fluidPckgChoice;
		this.BIPChoice = bp.BIPChoice;
		this.chemAddChoice = bp.chemAddChoice;
		this.opTemp = bp.opTemp;
		this.tankPress = bp.tankPress;
		this.feedTemp = bp.feedTemp;
		this.flashTemp = bp.flashTemp;
		this.flowRate = bp.flowRate;
 		this.flowComp = bp.flowComp;
		this.chemComp = bp.chemComp;
		this.chemNum = bp.chemNum;
		this.newChems = bp.newChems;
		this.idealCp = bp.idealCp;
		this.newZ = new double[bp.newZ.length];
		this.newZ = bp.newZ.clone();
	

		
	}
		
	
	//////////////Mutators////////////////////
	public void setTextChoice(int tc) {
		this.textChoice= tc;
	}
	public void setCaseChoice(int cc) {
		this.caseChoice = cc;
	}
	public void setIdealChoice(int ic) {
		this.idealChoice = ic;
	}
	public void setFluidPckgChoice(int fpc) {
		this.fluidPckgChoice = fpc;
	}
	public void setBIPChoice(int bipc) {
		this.BIPChoice = bipc;
	}
	public void setChemAddChoice(int cac) {
		this.chemAddChoice = cac;
	}
	public void setOpTemp(double ot) {
		this.opTemp = ot;
	}
	public void setTankPress(double tp) {
		this.tankPress = tp;
	}
	public void setFeedTemp(double ft) {
		this.feedTemp = ft;
	}
	public void setFlashTemp(double ft) {
		this.flashTemp = ft;
	}
	public void setFlowRate(double fr) {
		this.flowRate = fr;
	}
	public void setFlowComp(int[] fc) {
		this.flowComp = new int[fc.length];
		for(int i = 0; i<fc.length; i++) {
			this.flowComp[i] = fc[i];
		}
	}
	public void setChemComp(ArrayList<Double> arrayList) {
		this.chemComp= arrayList;
		
	}
	public void setChemNum(int chemNum) {
		this.chemNum = chemNum;
	}
	
	public void setNewChems(ArrayList<Chemical> newChems2) {
		this.newChems = newChems2;
	}
	public void setIdealCp(int idealCp) {
		this.idealCp = idealCp;
	}
	public void setNewZ(double[] newZ) {
		this.newZ = newZ;
	}
	public void setIdealChems(ArrayList<Chemical> idealChems) {
		this.idealChems = idealChems;
	}
	///////////////Accessors///////////////////
	public int getTextChoice() {
		return this.textChoice;
	}
	public int getCaseChoice() {
		return this.caseChoice;
	}
	public int getIdealChoice() {
		return this.idealChoice;
	}
	public int getFluidPckgChoice() {
		return this.fluidPckgChoice;
	}
	public int getBIPChoice() {
		return this.BIPChoice;
	}
	public int getChemAddChoice() {
		return this.chemAddChoice;
	}
	public double getOpTemp() {
		return this.opTemp;
	}
	public double getTankPress() {
		return this.tankPress;
	}
	public double getFeedTemp() {
		return this.feedTemp;
	}
	public double getFlashTemp() {
		return this.flashTemp;
	}
	public double getFlowRate() {
		return this.flowRate;
	}
	public int[] getFlowComp() {
		return this.flowComp.clone();
	}
	public ArrayList<Double> getChemComp() {
		return this.chemComp;
	}
	public int getChemNum() {
		return chemNum;
	}
	public ArrayList<Chemical> getNewChems() {
		return newChems;
	}
	public int getIdealCp() {
		return idealCp;
	}
	
	public double[] getNewZ() {
		return newZ;
	}
	public ArrayList<Chemical> getIdealChems() {
		return idealChems;
	}
	
	////////////////////////////////////////
	
	
	
	
}
