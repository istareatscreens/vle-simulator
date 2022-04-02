package menuReadouts;
//interface for all menu/newChemReadouts
public interface MenuReadout {//used to set up error handling and questions if needed

	public String introReadout();
	public String questionReadout();
	public String optionReadout();
	public int[] getArray();
	
}
