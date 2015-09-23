package gui;

import java.awt.Color;
import java.awt.event.KeyEvent;

import car.CarSimulation;
import car.CarSimulationState;
import car.Sensor;
import car.SimulatedCar;

import com.pheiffware.lib.geometry.shapes.BaseLineSegment;
import com.pheiffware.lib.geometry.shapes.Sphere;
import com.pheiffware.lib.simulation.RealTimeSimulationRunner;
import com.pheiffware.lib.swing.graphics.G2D;
import com.pheiffware.lib.swing.graphics.G2DRender;
import com.pheiffware.lib.swing.keylisten.PheiffKeyStrokeManager;
import com.pheiffware.lib.swing.renderPanel.graphicDebug2D.GraphicDebugPanel2D;

public class LiveCarEnvironmentPanel extends GraphicDebugPanel2D
{
	private final PheiffKeyStrokeManager keyManager;
	private final RealTimeSimulationRunner<CarSimulationState> simulationRunner;

	private double topSpeed = 250;
	private double targetSpeed = 0.0;
	private double targetSpeedAcc = 2;

	public LiveCarEnvironmentPanel(int width, int height, double renderPeriod, CarSimulation simulation)
	{
		super(width, height, renderPeriod, -150, -150, 300, 300);
		keyManager = installKeyStrokeManager();
		simulationRunner = new RealTimeSimulationRunner<CarSimulationState>(simulation, 1, 0.01, 0.0001);
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
		else if (keyManager.getKeyState(KeyEvent.VK_DOWN))
		{
			targetSpeed -= targetSpeedAcc;
			if (targetSpeed < 0)
			{
				targetSpeed = 0;
			}
		}

		simulationRunner.applyExternalInput(CarSimulation.TARGET_SPEED, targetSpeed);
		if (keyManager.getKeyState(KeyEvent.VK_LEFT))
		{
			simulationRunner.applyExternalInput(CarSimulation.TURN, -1);
		}
		else if (keyManager.getKeyState(KeyEvent.VK_RIGHT))
		{
			simulationRunner.applyExternalInput(CarSimulation.TURN, 1);
		}
		else
		{
			simulationRunner.applyExternalInput(CarSimulation.TURN, 0);
		}
		g2d.setColor(new Color(0, 0, 0));
		g2d.fillRectAbsolute(0, 0, getWidth(), getHeight());
		g2d.setColor(new Color(255, 0, 0));
		clearRenderables();
		CarSimulationState state = simulationRunner.getState();

		for (Sphere sphere : state.getSpheres())
		{
			G2DRender.render(g2d, sphere);
		}
		for (BaseLineSegment line : state.getLines())
		{
			G2DRender.render(g2d, line);
		}
		SimulatedCar car = state.getCar();
		for (int i = 0; i < 3; i++)
		{
			g2d.drawLine(car.getCorners()[i], car.getCorners()[i + 1]);
		}
		g2d.drawLine(car.getCorners()[3], car.getCorners()[0]);

		double[] sensorDistances = state.getSensorDistances();
		for (int i = 0; i < sensorDistances.length; i++)
		{
			Sensor sensor = car.getSensor(i);
			g2d.setColor(new Color(255, 255, 255));
			g2d.drawArrow(sensor.getPosition(), sensor.getDirection(), sensorDistances[i]);
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

			}

			@Override
			public void pressed(int keyCode)
			{

			}
		};
		keyManager.install();
		return keyManager;
	}

}
