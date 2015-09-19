package car;

import com.pheiffware.lib.geometry.Vec3D;

public class Car
{
	// Track corners of the car
	private final Vec3D[] corners;

	// Track the distance sensors on the car
	private final Sensor[] sensors;

	protected final Vec3D center;
	protected double angle;

	public Car(double length, double width, double[] sensorAngles, Vec3D[] sensorPositionOffsets, Vec3D center, double angle)
	{
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

	public Car(Car car)
	{
		center = new Vec3D(car.center);
		angle = car.angle;
		corners = new Vec3D[car.corners.length];
		for (int i = 0; i < corners.length; i++)
		{
			corners[i] = new Vec3D(car.corners[i]);
		}
		sensors = new Sensor[car.sensors.length];
		for (int i = 0; i < sensors.length; i++)
		{
			sensors[i] = new Sensor(car.sensors[i]);
		}
	}

	public void translate(Vec3D translate)
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

	public void rotate(double angle)
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

	public void rotateCarAroundPoint(double angle, Vec3D centerOfRotation)
	{
		this.angle += angle;
		center.rotateAround2D(angle, centerOfRotation);
		for (Vec3D corner : corners)
		{
			corner.rotateAround2D(angle, centerOfRotation);
		}
		for (Sensor sensor : sensors)
		{
			sensor.rotateAround2D(angle, centerOfRotation);
		}
	}

	public final Vec3D[] getCorners()
	{
		return corners;
	}

	public final Sensor[] getSensors()
	{
		return sensors;
	}

	public final Vec3D getCenter()
	{
		return center;
	}

	public final double getAngle()
	{
		return angle;
	}

}
