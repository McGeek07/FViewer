package fv;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

import fv.def.Function;
import fv.def.Function.Mandelbrot;
import fv.gui.Canvas;
import fv.gui.Schema;

public class FViewer {
	public final static int
			CANVAS_W = 640,
			CANVAS_H = 480,
			GFX_ASPECT_DX = 4,
			GFX_ASPECT_DY = 3,
			GFX_RESOLUTION = 128;
	public final static Schema
			SCHEMA = new Schema(
					new Color[] {
							Color.BLACK,
							Color.RED,
							Color.YELLOW,
							Color.RED,
							Color.BLACK,
							Color.RED,
							Color.YELLOW,
							Color.RED,
							Color.BLACK
					},
					new double[] {
							0.0,
							0.2,
							0.4,
							0.5,
							0.6,
							0.7,
							0.8,
							0.9,
							1.0
					}
					);
	public final static Function
			FUNCTION = new Mandelbrot(128);
	public static void main(String[] args) {
		JFrame frame = new JFrame("FViewer");
		JPanel
				body = new JPanel(),
				foot = new JPanel();	
		
		body.setLayout(new BorderLayout());
		foot.setLayout(new BoxLayout(foot, BoxLayout.Y_AXIS));
		
		Canvas canvas = new Canvas(
				CANVAS_W,
				CANVAS_H,
				GFX_ASPECT_DX,
				GFX_ASPECT_DY,
				GFX_RESOLUTION,
				SCHEMA,
				FUNCTION
				);
		
		JSlider
				itr_slider = new JSlider(1, 2048, 128),
				gfx_slider = new JSlider(1, 256,  128);
		itr_slider.addChangeListener((ce) -> {
			canvas.set_function(new Mandelbrot(itr_slider.getValue()));
		});		
		gfx_slider.addChangeListener((ce) -> {
			canvas.set_gfx_resolution(gfx_slider.getValue());
		});
		
		foot.add(itr_slider);
		foot.add(gfx_slider);
		
		body.add(canvas, BorderLayout.CENTER);
		body.add(foot  , BorderLayout.SOUTH);
		
		
		frame.add(body);
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setVisible(true);		
	}
}
