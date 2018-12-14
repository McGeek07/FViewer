package sv.sketch;

public class Mandelbrot2 implements Sketch {

	@Override
	public double pixel(double x, double y, int resolution) {
		double
			a = x,
			b = y,
			c = x,
			d = y,
			i;
		for(i = 0; i < resolution; i ++) {
			double r = c*c + d*d;//Math.sqrt(c * c + d * d);
			if(r > 4) break;				
			double
				j = (c*c - d*d),
				k = (c*d + d*c);
			c = (j*j - k*k);
			d = (j*k + k*j);
			c = a + c;
			d = b + d;
		}
		return i / resolution;
	}

}
