package gui;

import java.awt.event.KeyEvent;

import main.Car;

import com.pheiffware.lib.geometry.shapes.LineSegment;
import com.pheiffware.lib.geometry.shapes.Sphere;
import com.pheiffware.lib.swing.keylisten.PheiffKeyStrokeManager;

public class LiveBaseCarEnvironmentPanel extends BaseCarEnvironmentPanel
{
	public LiveBaseCarEnvironmentPanel(int width, int height,
			double renderPeriod, Car car, Sphere[] spheres, LineSegment[] lines)
	{
		super(width, height, renderPeriod, car, spheres, lines);
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

	}

	private class CarProcessingRunnable implements Runnable
	{
		@Override
		public void run()
		{
			long timeStamp = System.nanoTime();
			while (true)
			{

			}
		}
	}
}
