package gui;

import java.awt.Color;
import java.awt.event.KeyEvent;

import car.CarEnvironmentSimulation;
import car.Sensor;
import car.SimulatedCar;

import com.pheiffware.lib.geometry.shapes.LineSegment;
import com.pheiffware.lib.geometry.shapes.Sphere;
import com.pheiffware.lib.simulation.RealTimeSimulationRunner;
import com.pheiffware.lib.swing.graphics.G2D;
import com.pheiffware.lib.swing.graphics.G2DRender;
import com.pheiffware.lib.swing.keylisten.PheiffKeyStrokeManager;
import com.pheiffware.lib.swing.renderPanel.graphicDebug2D.GraphicDebugPanel2D;

public class LiveCarEnvironmentPanel extends GraphicDebugPanel2D
{
	private final PheiffKeyStrokeManager keyManager;
	private final RealTimeSimulationRunner<CarEnvironmentSimulation> simulationRunner;

	private double topSpeed = 0.002;
	private double targetSpeed = 0.0;
	private double targetSpeedAcc = 0.00003;

	public LiveCarEnvironmentPanel(int width, int height, double renderPeriod, CarEnvironmentSimulation simulation)
	{
		super(width, height, renderPeriod, -150, -150, 300, 300);
		keyManager = installKeyStrokeManager();
		simulationRunner = new RealTimeSimulationRunner<CarEnvironmentSimulation>(simulation, 1, 0.01, 0.000001);
	}

	public void start()
	{
		simulationRunner.start();
		startRender();
	}

	@Override
	public void render(G2D g2d)
	{
		if (keyManager.getKeyState(KeyEvent.VK_UP))
		{
			targetSpeed += targetSpeedAcc;
			if (targetSpeed > topSpeed)
			{
				targetSpeed = topSpeed;
			}
		}
		else
		{
			targetSpeed -= targetSpeedAcc;
			if (targetSpeed < 0)
			{
				targetSpeed = 0;
			}
		}
		simulationRunner.applyExternalInput(CarEnvironmentSimulation.TARGET_SPEED, targetSpeed);
		if (keyManager.getKeyState(KeyEvent.VK_LEFT))
		{
			simulationRunner.applyExternalInput(CarEnvironmentSimulation.TURN, -1);
		}
		else if (keyManager.getKeyState(KeyEvent.VK_RIGHT))
		{
			simulationRunner.applyExternalInput(CarEnvironmentSimulation.TURN, 1);
		}
		else
		{
			simulationRunner.applyExternalInput(CarEnvironmentSimulation.TURN, 0);
		}
		g2d.setColor(new Color(0, 0, 0));
		g2d.fillRectAbsolute(0, 0, getWidth(), getHeight());
		g2d.setColor(new Color(255, 0, 0));
		CarEnvironmentSimulation state = simulationRunner.getState();

		for (Sphere sphere : state.getSpheres())
		{
			G2DRender.render(g2d, sphere);
		}
		for (LineSegment line : state.getLines())
		{
			G2DRender.render(g2d, line);
		}
		SimulatedCar car = state.getCar();
		for (int i = 0; i < 3; i++)
		{
			g2d.drawLine(car.getCorners()[i], car.getCorners()[i + 1]);
		}
		g2d.drawLine(car.getCorners()[3], car.getCorners()[0]);

		for (Sensor sensor : car.getSensors())
		{
			g2d.drawArrowAboluteLength(sensor.getPosition(), sensor.getAngle());
		}

		super.renderDebugObjects(g2d);
	}

	private PheiffKeyStrokeManager installKeyStrokeManager()
	{
		PheiffKeyStrokeManager keyManager = new PheiffKeyStrokeManager()
		{
			@Override
			public void released(int keyCode)
			{
				switch (keyCode)
				{
				case KeyEvent.VK_UP:
					break;
				case KeyEvent.VK_LEFT:
					break;
				case KeyEvent.VK_RIGHT:
					break;
				}
			}

			@Override
			public void pressed(int keyCode)
			{
				switch (keyCode)
				{
				case KeyEvent.VK_UP:
					break;
				case KeyEvent.VK_LEFT:
					break;
				case KeyEvent.VK_RIGHT:
					break;
				}
			}
		};
		keyManager.install();
		return keyManager;
	}

}
