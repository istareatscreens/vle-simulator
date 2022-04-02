package chemicals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import main.GenericMethods;

public class kijValues {

	private double[][] alphaaa = new double[24][24];

	private boolean isON;

	private double[][] AGIP = new double[24][24];
	private double[][] BGIP = new double[24][24];

	private double[][] kijValues;
	private Structure[] compounds;

	private final double waterCValues[] = { -0.04, -8.7, -28, -41.5, -8.9, -8.5, -4.8, -10.8, 0, -8.8, -4.9, 0, 0, 0, 0, 0, -5.2, -17.5, 0, 0, 0, 0, 0, 0 };
	private final double waterDValues[] = { 1.5, 6.8, 19.7, 27.5, 8, 7.3, 3.6, 6.6, 0, 6.9, 1.9, 0, 0, 0, 0, 0, 4.9,12.8, 0, 0, 0, 0, 0, 0 };
	private final double waterEValues[] = { -0.08, -1.1, -3.3, -4.6, -1.3, -1.2,-0.6,-1.2,0, -1, -0.05, 0, 0, 0, 0, 0, -0.8,-2.2, 0, 0, 0, 0, 0, 0 };

	public kijValues(ArrayList<Chemical> chemsUsed, boolean isON) //constructor
	{
		this.kijValues = new double[chemsUsed.size()][chemsUsed.size()];
		this.isON = isON;
		this.compounds = new Structure[chemsUsed.size()];
		for (int i = 0; i < chemsUsed.size(); i++) {
			this.compounds[i] = new Structure((Structure) chemsUsed.get(i));
		}
		for (int i = 0; i < chemsUsed.size(); i++) {
			this.alphaaa[i] = this.compounds[i].calca().clone();
			if (i >= chemsUsed.size()) {
				i = 30;
			}
		}

	}

	public kijValues(kijValues kijGen) //copy constructor
	{
		this.compounds = new Structure[kijGen.compounds.length];
		for (int i = 0; i < kijGen.compounds.length; i++) {
			this.compounds[i] = new Structure(kijGen.compounds[i]);
		}
		this.isON = kijGen.isON;
		this.alphaaa = GenericMethods.clone2Ddoublearray(kijGen.alphaaa);
		this.kijValues = GenericMethods.clone2Ddoublearray(kijGen.kijValues);
	}

	private double[][] getAValues() {//load A values from text file
		Scanner reader = null;
		double[][] GroupInteractionParametersA = new double[24][24];
		try {
			reader = new Scanner(new FileInputStream("TextTestA.txt"));
			int i = 0;
			int j = 0;
			while (reader.hasNextDouble()) {
				for (j = 0; j < 24; j++) {
					GroupInteractionParametersA[i][j] = reader.nextDouble();
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Text File A not Found");
			System.exit(0);
		}
		return GroupInteractionParametersA;
	}

	private double[][] getBValues() {//load B values from text file
		Scanner reader = null;
		double[][] GroupInteractionParametersB = new double[24][24];

		try {
			reader = new Scanner(new FileInputStream("TextTestB.txt"));
			int i = 0;
			int j = 0;
			while (reader.hasNextDouble()) {

				for (j = 0; j < 24; j++) {
					GroupInteractionParametersB[i][j] = reader.nextDouble();
				}
				i++;
			}
		}

		catch (FileNotFoundException e) {
			System.out.println("Text File B not Found");
			System.exit(0);
		}

		return GroupInteractionParametersB;
	}

	public double[][] calcValues(double T)//calculate the KIJ values
	{
		double summ = 0;
		double summWater1 = 0;
		double summWater2 = 0;
			if (isON) 
			{
				this.AGIP = getAValues();
				this.BGIP = getBValues();
				for (int i = 0; i < this.compounds.length; i++)
				{
					for (int j = 0; j < this.compounds.length; j++)
					{
						summ = 0;
						for (int k = 0; k < 24; k++) 
						{
							if (alphaaa[i][15] == 1	|| alphaaa[j][15] == 1 & !(alphaaa[i][15] == 1 && alphaaa[j][12] == 1|| alphaaa[j][15] == 1 && alphaaa[i][12] == 1)) //checks for presence of water
							{
								//water is present
								summWater1 = (alphaaa[i][k] - alphaaa[j][k]) * (alphaaa[i][15] - alphaaa[j][15])* (waterCValues[k] * T * T + waterDValues[k] * T + waterEValues[k]);
								summWater2 = (alphaaa[i][15] - alphaaa[j][15]) * (alphaaa[i][k] - alphaaa[j][k])* (waterCValues[k] * T * T + waterDValues[k] * T + waterEValues[k]);
								summ = summ + summWater1 + summWater2;
							} else //no water
							{
								for (int l = 0; l < 24; l++) 
								{
									if (AGIP[k][l] != 0) 
									{
										summ = summ + ((alphaaa[i][k] - alphaaa[j][k]) * (alphaaa[i][l] - alphaaa[j][l])* this.AGIP[k][l]* (Math.pow((298.0 / T), ((this.BGIP[k][l] / this.AGIP[k][l]) - 1))));// error
																				// AGIP
									}
								}
							}

						}
						
						this.kijValues[i][j] = (((-0.5) * (summ)) - (Math.pow((Math.sqrt(this.compounds[i].littleAValues(T) / this.compounds[i].littleBValues()))- (Math.sqrt(this.compounds[j].littleAValues(T) / this.compounds[j].littleBValues())),	2)))/ (2 * (Math.sqrt(this.compounds[i].littleAValues(T) * this.compounds[j].littleAValues(T)))	/ (this.compounds[i].littleBValues() * this.compounds[j].littleBValues()));
						
					}
				}


			} else {//using KIJ of zero
				for (int i = 0; i < kijValues.length; i++)
					for (int j = 0; j < kijValues[i].length; j++) {
						this.kijValues[i][j] = 0;
					}
			}
		return GenericMethods.clone2Ddoublearray(kijValues);//returns kij values
	}


}

