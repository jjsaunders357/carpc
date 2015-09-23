package car;

import main.Geocalc;

import com.pheiffware.lib.geometry.shapes.BaseLineSegment;

/**
 * Same as CarSimulation with the distance of every sensor on the car to the nearest object.
 * @author Steve
 *
 */
public class CarSimulationState extends CarSimulation
{
	private static final double MAX_SENSOR_DISTANCE = 100;
	private final double[] sensorDistances;

	public CarSimulationState(CarSimulation carEnvironmentSimulation)
	{
		super(carEnvironmentSimulation);
		sensorDistances = new double[carEnvironmentSimulation.getCar().getSensors().length];
		updateSensorDistances(getCar().getSensors());
	}

	/**
	 * Gets the distance to the nearest sphere/line for each sensor.  Will return MAX_SENSOR_DISTANCE if nothing is in range.
	 * @param sensors 
	 * @return
	 */
	private double[] updateSensorDistances(Sensor[] sensors)
	{
		for (int i = 0; i < sensors.length; i++)
		{
			Sensor sensor = sensors[i];
			sensorDistances[i] = MAX_SENSOR_DISTANCE;
			// for (Sphere sphere : getSpheres())
			// {
			// double distance = Geocalc.distanceRayToSphere(sphere, sensor.getPosition(), sensor.getDirection());
			// if (distance < sensorDistances[i])
			// {
			// sensorDistances[i] = distance;
			// }
			// }
			for (BaseLineSegment line : getLines())
			{
				double distance = Geocalc.distanceRayToLineSegment2D(line, sensor.getPosition(), sensor.getDirection());
				if (distance < sensorDistances[i])
				{
					sensorDistances[i] = distance;
				}
			}
		}
		return sensorDistances;
	}

	public double[] getSensorDistances()
	{
		return sensorDistances;
	}
}
