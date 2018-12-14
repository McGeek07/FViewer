package sv.sketch;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Canvas extends JPanel implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {
	private static final long
			serialVersionUID = 1L;
	protected int
			canvas_w,
			canvas_h,
			canvas_w_2,
			canvas_h_2,
			gfx_aspect_dx,
			gfx_aspect_dy,
			gfx_resolution,		
			itr_resolution,
			gfx_w,
			gfx_h,
			gfx_w_2,
			gfx_h_2;
	protected double
			canvas_tx,
			canvas_ty,
			canvas_sx,
			canvas_sy,
			gfx_tx,
			gfx_ty,
			gfx_sx,
			gfx_sy;
	protected boolean
			repaint = true;	
	protected BufferedImage
			gfx_img;
	protected Graphics2D
			gfx;
	
	protected Schema
			schema;
	protected Sketch
			sketch;
	
	public Canvas(
			int canvas_w,
			int canvas_h,
			int gfx_aspect_dx,
			int gfx_aspect_dy,
			int gfx_resolution,
			int itr_resolution
			) {
		this.canvas_w = canvas_w;
		this.canvas_h = canvas_h;
		this.canvas_w_2 = this.canvas_w / 2;
		this.canvas_h_2 = this.canvas_h / 2;
		this.gfx_aspect_dx = gfx_aspect_dx;
		this.gfx_aspect_dy = gfx_aspect_dy;		
		this.gfx_resolution = gfx_resolution;
		this.itr_resolution = itr_resolution;
		this.gfx_w = this.gfx_aspect_dx * this.gfx_resolution;
		this.gfx_h = this.gfx_aspect_dy * this.gfx_resolution;
		this.gfx_w_2 = this.gfx_w / 2;
		this.gfx_h_2 = this.gfx_h / 2;
		
		this.canvas_tx = canvas_w_2;
		this.canvas_ty = canvas_h_2;
		double
			sx = (double)canvas_w / gfx_w,
			sy = (double)canvas_h / gfx_h;
		if(sx <= sy) {
			this.canvas_sx =  sx;
			this.canvas_sy = -sx;
		} else {
			this.canvas_sx =  sy;
			this.canvas_sy = -sy;
		}
		
		this.gfx_tx = - gfx_w_2;
		this.gfx_ty = - gfx_h_2;
		if(gfx_w >= gfx_h) {
			this.gfx_sx = 4. / gfx_w;
			this.gfx_sy = 4. / gfx_w;
		} else {
			this.gfx_sx = 4. / gfx_h;
			this.gfx_sy = 4. / gfx_h;
		}		
		
		Dimension size = new Dimension(
				canvas_w,
				canvas_h
		);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setPreferredSize(size);
		
		this.gfx_img = new BufferedImage(
				gfx_w,
				gfx_h,
				Transparency.OPAQUE
				);
		this.gfx = gfx_img.createGraphics();
		
		this.setFocusable(true);
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void set_gfx_resolution(int gfx_resolution) {
		this.gfx_resolution = gfx_resolution;
		handle_gfx_changed();
	}
	
	public void set_itr_resolution(int itr_resolution) {
		this.itr_resolution = itr_resolution;
		handle_gfx_changed();
	}
	
	public void set_schema(Schema schema) {
		this.schema = schema;
		this.repaint = true;
		repaint();
	}
	
	public void set_sketch(Sketch sketch) {
		this.sketch = sketch;
		this.repaint= true;
		repaint();
	}	
	
	public void handle_gfx_changed() {
		double
			_gfx_w = this.gfx_w,
			_gfx_h = this.gfx_h,
			_gfx_w_2 = this.gfx_w_2,
			_gfx_h_2 = this.gfx_h_2;
		this.gfx_w = this.gfx_aspect_dx * this.gfx_resolution;
		this.gfx_h = this.gfx_aspect_dy * this.gfx_resolution;
		this.gfx_w_2 = this.gfx_w / 2;
		this.gfx_h_2 = this.gfx_h / 2;
		double
			sx = (double)canvas_w / gfx_w,
			sy = (double)canvas_h / gfx_h;
		if(sx <= sy) {
			this.canvas_sx =  sx;
			this.canvas_sy = -sx;
		} else {
			this.canvas_sx =  sy;
			this.canvas_sy = -sy;
		}
		this.gfx_sx *= (_gfx_w / gfx_w);
		this.gfx_sy *= (_gfx_h / gfx_h);
		this.gfx_tx = (gfx_tx + _gfx_w_2) * (gfx_w / _gfx_w) - gfx_w_2;
		this.gfx_ty = (gfx_ty + _gfx_h_2) * (gfx_h / _gfx_h) - gfx_h_2;
		this.gfx_img = new BufferedImage(
				gfx_w,
				gfx_h,
				Transparency.OPAQUE
				);
		this.gfx = gfx_img.createGraphics();
		repaint = true;
		repaint();
	}	
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		if(repaint && sketch != null) {
			for(int x = 0; x < gfx_w; x ++)
				for(int y = 0; y < gfx_h; y ++) {
					double f = sketch.pixel(
							(x + gfx_tx) * gfx_sx,
							(y + gfx_ty) * gfx_sy,
							itr_resolution
							);
					Color c = schema.color(f);
					gfx.setColor(c);
					gfx.fillRect(
							x, y,
							1, 1
							);
				}
			repaint = false;
		}
		g2D.translate(
				canvas_tx,
				canvas_ty
				);
		g2D.scale(
				canvas_sx,
				canvas_sy
				);
		g2D.drawImage(
				gfx_img,
				- gfx_w_2,
				- gfx_h_2,
				null
				);
	}
	
	protected double
			mx,
			my,
			ax,
			ay;

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ax = e.getX();
		ay = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int w = e.getWheelRotation();
		if(w != 0) {
			if(w < 0) {
				this.gfx_sx /= 1.1;
				this.gfx_sy /= 1.1;
				this.gfx_tx = (gfx_tx + gfx_w_2) * 1.1 - gfx_w_2;
				this.gfx_ty = (gfx_ty + gfx_h_2) * 1.1 - gfx_h_2;
			}
			if(w > 0) {
				this.gfx_sx *= 1.1;
				this.gfx_sy *= 1.1;
				this.gfx_tx = (gfx_tx + gfx_w_2) / 1.1 - gfx_w_2;
				this.gfx_ty = (gfx_ty + gfx_h_2) / 1.1 - gfx_h_2;
			}
			repaint = true;
			repaint();
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mx = e.getX();
		my = e.getY();		
		gfx_tx += (ax - mx) / canvas_sx;
		gfx_ty += (ay - my) / canvas_sy;
		repaint = true;
		ax = mx;
		ay = my;
		
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
	}	
}
