
package chemicals;

import chemsDatabase.*;

public class Structure extends RealChemical {
	private double[] moleculeComponents = new double[24];
	private double number = 0;
	private double[] alpha = new double[24];
	private double r = 8.314472;

	public Structure(Database d) {
		super(d);
		this.moleculeComponents = d.getMolComponents().clone();
		for (int i = 0; i < moleculeComponents.length; i++) {
			if (this.moleculeComponents[i] != 0) {
				number++;
			}
		}
	}

	public Structure(Structure s) {
		super( s.getName(),  s.getMolarMass(), s.getCpConstGas(), s.getCpConstLiq(), s.getTc(), s.getPc(), s.getAF() );
		this.moleculeComponents = s.moleculeComponents.clone();
		this.number = s.number;
		this.alpha = s.alpha.clone();
		this.r = s.r;
	}
	
	public double littleAValues(double temperature) {

		double ac = 0.457235529 * ((this.r * this.r * super.getTc() * super.getTc()) / super.getPc());
		double a = 0;
		double ci = 0;
		if (super.getAF() <= 0.491)
		{
			ci = 0.37464 + (1.5422 * super.getAF()) - (0.2699 * super.getAF() * super.getAF());
		} else
		{
			ci = 0.379642 + (1.48503 * super.getAF()) - (0.164423 * super.getAF() * super.getAF())
					+ (0.01666 * super.getAF() * super.getAF() * super.getAF());
		}
		a = ac * (Math.pow((1 + (ci * (1 - Math.sqrt(temperature / super.getTc())))), 2));
		return a;
	}

	public double littleBValues() {
		double b = 0.0777960739 * ((this.r * super.getTc()) / super.getPc());
		return b;
	}

	public double[] calca() {
		for (int i = 0; i < 24; i++) {
			alpha[i] = this.moleculeComponents[i] / number;
		}
		return alpha.clone();
	}
	public double[] getMolComponents() {
		return this.moleculeComponents.clone();
	}
}
