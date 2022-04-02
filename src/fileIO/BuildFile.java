package fileIO;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import flashvessel.Vessel;
import userinput.BlueprintReader;
import userinput.MainMenu;
import userinput.VesselWhisperer;
import fileIO.*;
import fileIO.BuildFile;


public class BuildFile {
	private String name;
	private boolean appendToFile=false;
	
	//use when you want to start form the beginning of the file and overwrite everything
	public BuildFile(String name){
		this.name=name;
	}
	
	public BuildFile(String name, boolean appendToFile){
		this.name=name;
		this.appendToFile=appendToFile;
	}
	
	public String getName(){
		return this.name;
	}
	
	public boolean getAppendToFile(){
		return this.appendToFile;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setAppendToFile(boolean appendToFile){
		this.appendToFile=appendToFile;
	}
	
	public BuildFile(BuildFile bfObject){
		this.name=bfObject.name;
		this.appendToFile=bfObject.appendToFile;
	}
	
	public BuildFile clone(){
		return new BuildFile(this);
	}
	
	public void writeToFile(String textLine) throws IOException{
		FileWriter write = new FileWriter(name, appendToFile);
		PrintWriter printLine = new PrintWriter(write);
		
		printLine.printf("%s"+"%n", textLine);
		printLine.close();
	}
	
	public void writeDoubleToFile(double doubleLine) throws IOException{
		FileWriter write = new FileWriter(name, appendToFile);
		PrintWriter printLine = new PrintWriter(write);
		
		printLine.printf("%s"+"%n",doubleLine);
		printLine.close();
	}
	
//	public void writeTable(String columnName1, String columnName2){
//	FileWriter write = new FileWriter
//	}
	
}
