package fv;

import java.awt.Color;

import javax.swing.JFrame;

import fv.def.Function;
import fv.def.Function.Mandelbrot;
import fv.gui.Canvas;
import fv.gui.Schema;

public class FViewer {
	public final static int
			CANVAS_W = 1366,
			CANVAS_H =  768,
			GFX_ASPECT_DX = 4,
			GFX_ASPECT_DY = 3,
			GFX_RESOLUTION = 128;
	public final static Schema
			SCHEMA = new Schema(
					new Color[] {
							Color.BLACK,
							Color.RED,
							Color.YELLOW,
							Color.WHITE
					},
					new double[] {
							0.0,
							0.2,
							0.9,
							1.0
					}
					);
	public final static Function
			FUNCTION = new Mandelbrot(256);
	public static void main(String[] args) {
		JFrame viewer = new JFrame("FViewer");
		Canvas canvas = new Canvas(
				CANVAS_W,
				CANVAS_H,
				GFX_ASPECT_DX,
				GFX_ASPECT_DY,
				GFX_RESOLUTION,
				SCHEMA,
				FUNCTION
				);
		
		viewer.add(canvas);
		viewer.pack();
		
		viewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		viewer.setLocationRelativeTo(null);
		viewer.setAlwaysOnTop(true);
		viewer.setResizable(false);
		viewer.setVisible(true);		
	}
}
