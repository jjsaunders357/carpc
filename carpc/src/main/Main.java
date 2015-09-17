package main;

import gui.BaseCarEnvironmentPanel;
import gui.LiveBaseCarEnvironmentPanel;

import com.pheiffware.lib.geometry.Vec3D;
import com.pheiffware.lib.geometry.shapes.LineSegment;
import com.pheiffware.lib.geometry.shapes.Sphere;
import com.pheiffware.lib.swing.MainFrame;

public class Main
{
	public static void main(String[] args)
	{
        // @formatter:off    
		Car car = new Car(24, 16, 
				1, 
				2, 
				new double[] { Math.PI * -35.0/180.0, 0.0, Math.PI * 35.0/180.0}, 
				new Vec3D[]
				{ 
					new Vec3D(11, -7, 0), 
					new Vec3D(11, 0, 0), 
					new Vec3D(11, 7, 0) 
				},
				new Vec3D(35, 10, 0), 
				Math.PI * 25 / 180.0);
		Sphere[] spheres = new Sphere[]
		{
				new Sphere(new Vec3D(50,50,0),10.0),
				new Sphere(new Vec3D(-30,70,0),20.0),
				new Sphere(new Vec3D(80,-80,0),40.0),
				new Sphere(new Vec3D(-50,-50,0),20.0),
		};

		LineSegment[] lines = new LineSegment[]
		{
				new LineSegment(60, -50, 80, 50)
		};
		
		// @formatter:on
		
		new MainFrame("Car Panel", new LiveBaseCarEnvironmentPanel(800, 800, 0.01,car,spheres,lines));
	}
}
