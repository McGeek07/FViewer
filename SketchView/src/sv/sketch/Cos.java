package sv.sketch;

public class Cos implements Sketch {
	@Override
	public double pixel(double x, double y, int resolution) {
		double r = Math.abs(y - Math.cos(x));
		return 1 - r / (1 + r);
	}
}
