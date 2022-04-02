package main;


//Stores generic methods used through the program
public class GenericMethods {
	//used to check when determining if a double value is equal to a number
	private static final double errorFactor = 0.00000000000000000000000000001;

	// find largest value
	public static double findMaxDoubleArray(double[] array) {
		double temp = -1;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > temp) {
				temp = array[i];
			}
		}
		return temp;
	}

	// find smallest value
	public static double findMinDoubleArray(double[] array) {
		double temp = 999999999;
		for (int i = 0; i < array.length; i++) {
			if (array[i] < temp) {
				temp = array[i];
			}
		}
		return temp;
	}

	// Down casts Double[] to double[]
	public static double[] downCastDoubleArray(Double[] arrayDoubles) {
		double[] temp = new double[arrayDoubles.length];
		for (int i = 0; i < arrayDoubles.length; i++) {
			temp[i] = (double) arrayDoubles[i];
		}
		return temp;
	}

	// Down casts Object[] to Double[]
	public static Double[] downCastObjectArrayToDouble(Object[] arrayObject) {
		Double[] temp = new Double[arrayObject.length];
		for (int i = 0; i < arrayObject.length; i++) {
			temp[i] = (Double) arrayObject[i];
		}
		return temp;
	}

	// Down casts Object[] to String[]
	public static String[] downCastObjectArrayToString(Object[] arrayObject) {
		String[] temp = new String[arrayObject.length];
		for (int i = 0; i < arrayObject.length; i++) {
			temp[i] = (String) arrayObject[i];
		}
		return temp;
	}

	// Up Cast double[] to Double[]
	public static Double[] upCastPrimitiveDoubleArrayToDoubleArray(double[] arrayPDouble) {
		Double[] temp = new Double[arrayPDouble.length];
		for (int i = 0; i < arrayPDouble.length; i++) {
			temp[i] = (Double) arrayPDouble[i];
		}
		return temp;
	}

	// Down Cast from Object[] to double[] array
	public static double[] castObjectArrayToPrimitiveDoubleArray(Object[] oArray) {
		return downCastDoubleArray(downCastObjectArrayToDouble(oArray));
	}

	// makes arrays so no waste of memory occurs
	public static double[] makeDoubleArray(double... value) {
		return value;
	}

	// Declares error allowed in comparison for doubles
	public static double getErrorFactor() {
		return errorFactor;
	}

	// Clone 2D double array
	public static double[][] clone2Ddoublearray(double[][] array2d) {
		double[][] temp = new double[array2d.length][];
		for (int j = 0; j < array2d.length; j++) {
			temp[j] = array2d[j].clone();
		}
		return temp;
	}
}
