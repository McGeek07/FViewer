package fv.def;

public interface Function {
	public double fitness(double x, double y);
	
	public static class Line implements Function {
		public final static long
				DEFAULT_A = -1,
				DEFAULT_B =  1,
				DEFAULT_C =  0;
		private final static double
				DEFAULT_R = 2;
		protected long
				a,
				b,
				c;
		protected double
				r;
		
		public Line() {
			this(
					DEFAULT_A,
					DEFAULT_B,
					DEFAULT_C,
					DEFAULT_R
					);
		}
		
		public Line(long a, long b, long c, double r) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.r = r;
		}

		@Override
		public double fitness(double x, double y) {
			double f = Math.abs(a*x + b*y + c);
			return f <= r ? 1 - (f / r): 0.;
		}
	}
	
	public static class Mandelbrot implements Function {
		public final static int
				DEFAULT_RESOLUTION = 64;
		protected int
				resolution;
		
		public Mandelbrot() {
			this(
					DEFAULT_RESOLUTION
					);
		}
		
		public Mandelbrot(
				int resolution
				) {
			this.resolution = resolution;
		}
		
		@Override
		public double fitness(double x, double y) {
			double
				a = x,
				b = y,
				c = x,
				d = y,
				i;
			for(i = 0; i < resolution; i ++) {
				double r = Math.sqrt(c * c + d * d);
				if(r > 2) break;				
				double
					j = (c*c - d*d),
					k = (c*d + d*c);
				c = a + j;
				d = b + k;
			}
			return i / resolution;
		}		
	}
}
