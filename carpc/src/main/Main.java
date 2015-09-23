package main;

import gui.LiveCarEnvironmentPanel;
import car.CarSimulation;
import car.SimulatedCar;

import com.pheiffware.lib.geometry.Vec3D;
import com.pheiffware.lib.geometry.shapes.BaseLineSegment;
import com.pheiffware.lib.geometry.shapes.Sphere;
import com.pheiffware.lib.log.Log;
import com.pheiffware.lib.log.PCLogHandler;
import com.pheiffware.lib.swing.MainFrame;

public class Main
{
	public static LiveCarEnvironmentPanel liveCarEnvironmentPanel;

	public static void main(String[] args)
	{
		Log.install(new PCLogHandler());
		// @formatter:off
		SimulatedCar car = new SimulatedCar(
				24, 
				16, 
				new double[] { Math.PI * -35.0 / 180.0, 0.0, Math.PI * 35.0 / 180.0 }, 
				new Vec3D[] { new Vec3D(11, -7, 0), new Vec3D(11, 0, 0), new Vec3D(11, 7, 0) }, 
				new Vec3D(0, 0, 0), 
				Math.PI * 0 / 180.0,
				24*3,
				24*7,
				40);
		Sphere[] spheres = new Sphere[]
		{ new Sphere(new Vec3D(50, 50, 0), 10.0), new Sphere(new Vec3D(-30, 70, 0), 20.0), new Sphere(new Vec3D(80, -80, 0), 40.0),
				new Sphere(new Vec3D(-50, -50, 0), 20.0), };

		BaseLineSegment[] lines = new BaseLineSegment[]
		{ new BaseLineSegment(60, -50, 80, 50) };

		// @formatter:on
		CarSimulation simulation = new CarSimulation(car, spheres, lines);
		liveCarEnvironmentPanel = new LiveCarEnvironmentPanel(800, 800, 0.01, simulation);
		liveCarEnvironmentPanel.start();
		new MainFrame("Car Panel", liveCarEnvironmentPanel);
	}
}
