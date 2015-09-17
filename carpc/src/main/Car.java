package main;

import com.pheiffware.lib.geometry.Vec3D;

/**
 * Tracks the car itself's position, angle, etc. Updates it based on signals
 * received from the environment.
 * 
 * @author Steve
 */
public class Car
{
	// Track corners of the car
	private final Vec3D[] corners;

	// Track the distance sensors on the car
	private final Sensor[] sensors;

	// Length of car
	private final double length;

	// Width of car
	private final double width;

	// Rate of car acceleration
	private final double acceleration;
	private final double deceleration;

	private final Vec3D center;
	private double angle;
	private double speed = 0.0;

	public Car(double length, double width, double acceleration,
			double deceleration, double[] sensorAngles,
			Vec3D[] sensorPositionOffsets, Vec3D center, double angle)
	{
		this.length = length;
		this.width = width;
		this.acceleration = acceleration;
		this.deceleration = deceleration;
		this.sensors = new Sensor[sensorPositionOffsets.length];
		for (int i = 0; i < sensorPositionOffsets.length; i++)
		{
			sensors[i] = new Sensor(sensorAngles[i], sensorPositionOffsets[i]);
		}
		corners = new Vec3D[4];
		corners[0] = new Vec3D(-length / 2, -width / 2, 0);
		corners[1] = new Vec3D(length / 2, -width / 2, 0);
		corners[2] = new Vec3D(length / 2, width / 2, 0);
		corners[3] = new Vec3D(-length / 2, width / 2, 0);
		this.center = new Vec3D(0, 0, 0);
		translate(center);
		this.angle = 0;
		rotate(angle);

	}

	private void translate(Vec3D translate)
	{
		center.addTo(translate);
		for (Vec3D corner : corners)
		{
			corner.addTo(translate);
		}
		for (Sensor sensor : sensors)
		{
			sensor.translate(translate);
		}
	}

	private void rotate(double angle)
	{
		this.angle += angle;
		for (Vec3D corner : corners)
		{
			corner.rotateAround2D(angle, center);
		}
		for (Sensor sensor : sensors)
		{
			sensor.rotateAround2D(angle, center);
		}
	}

	private void rotateCarAroundPoint(double angle, Vec3D centerOfRotation)
	{
		this.angle += angle;
		center.rotateAround2D(angle, centerOfRotation);
		for (Vec3D corner : corners)
		{
			corner.rotateAround2D(angle, centerOfRotation);
		}
		for (Sensor sensor : sensors)
		{
			sensor.rotateAround2D(angle, center);
		}
	}

	/**
	 * Calculates what happens between time steps when car moves forward while
	 * turning. Assumes constant velocity and angular rotation. With fast enough
	 * time steps, this should be good enough.
	 * 
	 * @param totalForwardTranslation
	 * @param totalRotationRadians
	 */
	private void arc(double totalForwardTranslation, double totalRotationRadians)
	{
		if (totalRotationRadians == 0)
		{
			Vec3D translation = new Vec3D(totalForwardTranslation, 0, 0);
			translation.rotate2D(totalRotationRadians);
			translate(translation);
			return;
		}
		double radiusOfRotation = totalForwardTranslation
				* totalRotationRadians;
		double angleToCenterOfRotation = angle
				+ Math.signum(totalRotationRadians) * Math.PI;
		Vec3D centerOfRotation = new Vec3D(radiusOfRotation, 0, 0);
		centerOfRotation.rotate2D(angleToCenterOfRotation);
		rotateCarAroundPoint(totalRotationRadians, centerOfRotation);
	}

	/**
	 * Update car based on current information.
	 * 
	 * @param elapsedTime
	 *            How much time has passed since last update.
	 * @param newAngleRadians
	 * @param targetSpeed
	 */
	public void timeStep(double elapsedTime, double newAngleRadians,
			double targetSpeed)
	{
		final double acc;
		if (targetSpeed > speed)
		{
			acc = acceleration;
		}
		else
		{
			acc = -deceleration;
		}
		double accTime = (targetSpeed - speed) / acc;
		final double coastTime;
		if (accTime < elapsedTime)
		{
			coastTime = elapsedTime - accTime;
		}
		else
		{
			coastTime = 0;
		}
		double totalForwardTranslation = speed * accTime + acc * accTime
				* accTime / 2 + targetSpeed * coastTime;

		double totalRotationRadians = newAngleRadians - angle;
		arc(totalForwardTranslation, totalRotationRadians);
	}

	public Vec3D[] getCorners()
	{
		return corners;
	}

	public Sensor[] getSensors()
	{
		return sensors;
	}

	public Vec3D getCenter()
	{
		return center;
	}

	public double getAngle()
	{
		return angle;
	}

	public double getSpeed()
	{
		return speed;
	}
}
