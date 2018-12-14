package sv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import sv.sketch.Canvas;
import sv.sketch.Mandelbrot2;
import sv.sketch.Schema;
import sv.sketch.Sketch;

public class SketchView {	
	public final static int
			CANVAS_W = 640,
			CANVAS_H = 480,
			GFX_ASPECT_DX = 4,
			GFX_ASPECT_DY = 3,
			GFX_RESOLUTION = 64,
			ITR_RESOLUTION = 64,
			MIN_GFX_RESOLUTION = 1,
			MAX_GFX_RESOLUTION = 256,
			MIN_ITR_RESOLUTION = 1,
			MAX_ITR_RESOLUTION = 2048;	
	
	public static void main(String[] args) {
		Canvas canvas = new Canvas(
				CANVAS_W,
				CANVAS_H,
				GFX_ASPECT_DX,
				GFX_ASPECT_DY,
				GFX_RESOLUTION,
				ITR_RESOLUTION
				);
		canvas.set_schema(SCHEMA_ARRAY[0]);
		canvas.set_sketch(SKETCH_ARRAY[0]);
		
		JFrame frame = new JFrame("Sketch View");
		JPanel
				panelA = new JPanel(),
				panelB = new JPanel(),
				gfx_panel = new JPanel(),
				itr_panel = new JPanel(),				
				schema_panel = new JPanel(),
				sketch_panel = new JPanel();
		
		panelA.setLayout(new BorderLayout());
		panelB.setLayout(new BoxLayout(panelB, BoxLayout.Y_AXIS));		
		itr_panel.setLayout(new BorderLayout());
		gfx_panel.setLayout(new BorderLayout());
		schema_panel.setLayout(new BorderLayout());
		sketch_panel.setLayout(new BorderLayout());
		
		JLabel
				gfx_label = new JLabel("GFX Resolution "),
				itr_label = new JLabel("ITR Resolution "),
				schema_label = new JLabel("Schema Select "),
				sketch_label = new JLabel("Sketch Select ");
		
		JSlider
				gfx_slider = new JSlider(MIN_GFX_RESOLUTION, MAX_GFX_RESOLUTION,  GFX_RESOLUTION),
				itr_slider = new JSlider(MIN_ITR_RESOLUTION, MAX_ITR_RESOLUTION,  ITR_RESOLUTION),
				schema_slider = new JSlider(0, SCHEMA_ARRAY.length - 1, 0),
				sketch_slider = new JSlider(0, SKETCH_ARRAY.length - 1, 0);
		JTextField
				gfx_textfield = new JTextField("" + GFX_RESOLUTION, 10),
				itr_textfield = new JTextField("" + ITR_RESOLUTION, 10),
				schema_textfield = new JTextField(SCHEMA_NAMES[0], 10),
				sketch_textfield = new JTextField(SKETCH_NAMES[0], 10);
		gfx_slider.addChangeListener((e) -> {
			int gfx_resolution = gfx_slider.getValue();
			canvas.set_gfx_resolution(gfx_resolution);
			gfx_textfield.setText(""+ gfx_resolution);
		});
		itr_slider.addChangeListener((e) -> {
			int itr_resolution = itr_slider.getValue();
			canvas.set_itr_resolution(itr_resolution);
			itr_textfield.setText(""+ itr_resolution);
		});
		schema_slider.addChangeListener((e) -> {
			int schema = schema_slider.getValue();
			canvas.set_schema(SCHEMA_ARRAY[schema]);
			schema_textfield.setText(SCHEMA_NAMES[schema]);
		});
		sketch_slider.addChangeListener((e) -> {
			int sketch = sketch_slider.getValue();
			canvas.set_sketch(SKETCH_ARRAY[sketch]);
			sketch_textfield.setText(SKETCH_NAMES[sketch]);
		});
		gfx_textfield.setHorizontalAlignment(JTextField.CENTER);
		gfx_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int gfx_resolution = gfx_slider.getValue();
				try {
					gfx_resolution = Integer.parseInt(gfx_textfield.getText());
					if(gfx_resolution < MIN_GFX_RESOLUTION)
						gfx_resolution = MIN_GFX_RESOLUTION;
					if(gfx_resolution > MAX_GFX_RESOLUTION)
						gfx_resolution = MAX_GFX_RESOLUTION;
					gfx_slider.setValue(gfx_resolution);
				} catch(Exception ex) {
					//do nothing
				}			
			}
		});
		itr_textfield.setHorizontalAlignment(JTextField.CENTER);
		itr_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int itr_resolution = itr_slider.getValue();
				try {
					itr_resolution = Integer.parseInt(itr_textfield.getText());
					if(itr_resolution < MIN_ITR_RESOLUTION)
						itr_resolution = MIN_ITR_RESOLUTION;
					if(itr_resolution > MAX_ITR_RESOLUTION)
						itr_resolution = MAX_ITR_RESOLUTION;
					itr_slider.setValue(itr_resolution);
				} catch(Exception ex) {
					//do nothing
				}
			}
		});
		schema_textfield.setHorizontalAlignment(JTextField.CENTER);
		schema_textfield.setEditable(false);
		sketch_textfield.setHorizontalAlignment(JTextField.CENTER);
		sketch_textfield.setEditable(false);
		
		gfx_panel.add(gfx_label, BorderLayout.WEST);
		gfx_panel.add(gfx_slider, BorderLayout.CENTER);
		gfx_panel.add(gfx_textfield, BorderLayout.EAST);
		
		itr_panel.add(itr_label, BorderLayout.WEST);
		itr_panel.add(itr_slider, BorderLayout.CENTER);
		itr_panel.add(itr_textfield, BorderLayout.EAST);
		
		schema_panel.add(schema_label, BorderLayout.WEST);
		schema_panel.add(schema_slider, BorderLayout.CENTER);
		schema_panel.add(schema_textfield, BorderLayout.EAST);
		
		sketch_panel.add(sketch_label, BorderLayout.WEST);
		sketch_panel.add(sketch_slider, BorderLayout.CENTER);
		sketch_panel.add(sketch_textfield, BorderLayout.EAST);		
		
		panelB.add(gfx_panel);
		panelB.add(itr_panel);
		panelB.add(schema_panel);
		panelB.add(sketch_panel);		
		
		panelA.add(canvas, BorderLayout.CENTER);
		panelA.add(panelB, BorderLayout.SOUTH );		
		
		frame.add(panelA);
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setVisible(true);		
	}
	
	public final static Sketch
			MANDELBROT_2 = new Mandelbrot2();	
	public final static String[]
			SKETCH_NAMES = {
				"MANDELBROT_2"
			};	
	public final static Sketch[]
			SKETCH_ARRAY = {
				MANDELBROT_2
			};
	
	
	public final static Schema
			EMBER = new Schema(
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
				}),
			FROST = new Schema(
				new Color[] {
						Color.WHITE,
						Color.CYAN,
						Color.BLUE,
						Color.CYAN,
						Color.WHITE,
						Color.CYAN,
						Color.BLUE,
						Color.CYAN,
						Color.WHITE
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
				}),
			JUNGLE = new Schema(
				new Color[] {
						Color.DARK_GRAY,
						Color.ORANGE,
						Color.GREEN,
						Color.ORANGE,
						Color.DARK_GRAY,
						Color.ORANGE,
						Color.GREEN,
						Color.ORANGE,
						Color.DARK_GRAY
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
				});
	public final static String[]
			SCHEMA_NAMES = {
				"EMBER",
				"FROST",
				"JUNGLE"
			};
	public final static Schema[]
			SCHEMA_ARRAY = {
				EMBER,
				FROST,
				JUNGLE
			};
}
