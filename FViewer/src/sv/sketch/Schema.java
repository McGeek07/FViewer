package sv.sketch;

import java.awt.Color;

public class Schema {
	public final static Color[]
			DEFAULT_SCHEMA = {
					Color.BLACK,
					Color.WHITE
			};
	public final static double[]
			DEFAULT_WEIGHT = {
					0.0,
					1.0
			};
	public final static boolean
			DEFAULT_INTERPOLATE = true;
	
	protected Color[]
			schema;
	protected double[]
			weight;
	protected boolean
			interpolate;
	
	public Schema() {
		this(
				DEFAULT_SCHEMA,
				DEFAULT_WEIGHT,
				DEFAULT_INTERPOLATE
				);
	}
	
	public Schema(Color[] schema, double[] weight) {
		this(
				schema,
				weight,
				DEFAULT_INTERPOLATE
				);
	}
	
	public Schema(Color[] schema, double[] weight, boolean interpolate) {
		this.schema = schema;
		this.weight = weight;
		this.interpolate = interpolate;
	}
	
	public Color color(double value) {
		return color(value, interpolate);
	}
	
	public Color color(double value, boolean interpolate) {
		if(value <= 0.)
			return schema[0];
		if(value >= 1.)
			return schema[schema.length - 1];		
		int n = Math.min(
				schema.length,
				weight.length
				);
		for(int i = 1; i < n; i ++) {
			if(value <= weight[i]) {				
				if(interpolate) {
					Color
						j = schema[i - 1],
						k = schema[i - 0];
					double
						r = (k.getRed()   - j.getRed())   * value + j.getRed(),
						g = (k.getGreen() - j.getGreen()) * value + j.getGreen(),
						b = (k.getBlue()  - j.getBlue())  * value + j.getBlue();
					return new Color(
							(int)r,
							(int)g,
							(int)b
							);						
				} else
					return schema[i];
			}
		}
		return schema[0];
	}
}
