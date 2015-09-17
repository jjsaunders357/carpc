package gui;

import java.awt.Color;

import main.Car;
import main.Sensor;

import com.pheiffware.lib.geometry.shapes.LineSegment;
import com.pheiffware.lib.geometry.shapes.Sphere;
import com.pheiffware.lib.swing.graphics.G2D;
import com.pheiffware.lib.swing.graphics.G2DRender;
import com.pheiffware.lib.swing.renderPanel.RenderPanel;

public class BaseCarEnvironmentPanel extends RenderPanel
{
	private static final long serialVersionUID = 5045478802857384247L;

	private final Car car;
	private final Sphere[] spheres;
	private final LineSegment[] lines;

	public BaseCarEnvironmentPanel(int width, int height, double renderPeriod,
			Car car, Sphere[] spheres, LineSegment[] lines)
	{
		super(width, height, renderPeriod, -150, -150, 300, 300);
		this.car = car;
		this.spheres = spheres;
		this.lines = lines;
	}

	@Override
	public void render(G2D g2d)
	{
		g2d.setColor(new Color(0, 0, 0));
		g2d.fillRectAbsolute(0, 0, getWidth(), getHeight());
		g2d.setColor(new Color(255, 0, 0));
		for (Sphere sphere : spheres)
		{
			G2DRender.render(g2d, sphere);
		}
		for (LineSegment line : lines)
		{
			G2DRender.render(g2d, line);
		}
		for (int i = 0; i < 3; i++)
		{
			g2d.drawLine(car.getCorners()[i], car.getCorners()[i + 1]);
		}
		g2d.drawLine(car.getCorners()[3], car.getCorners()[0]);

		for (Sensor sensor : car.getSensors())
		{
			g2d.drawArrowAboluteLength(sensor.getPosition(), sensor.getAngle());
		}
	}
}
