package main;

import com.pheiffware.lib.geometry.Vec3D;

public class Sensor
{
	private double angle;
	private final Vec3D position;

	public Sensor(double angle, Vec3D position)
	{
		this.angle = angle;
		this.position = position;
	}

	public final double getAngle()
	{
		return angle;
	}

	public final Vec3D getPosition()
	{
		return position;
	}

	public void translate(Vec3D translate)
	{
		position.addTo(translate);
	}

	public final void rotateAround2D(final double angleRotatedRadians,
			final Vec3D centerOfRotation)
	{
		position.rotateAround2D(angleRotatedRadians, centerOfRotation);
		angle += angleRotatedRadians;
	}
}
