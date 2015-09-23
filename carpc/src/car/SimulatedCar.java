package car;

import com.pheiffware.lib.geometry.Vec3D;

/**
 * Tracks the car itself's position, angle, etc. Updates it based on signals
 * received from the environment.
 * 
 * @author Steve
 */
public class SimulatedCar extends Car
{
	// Rate of car acceleration/deceleration
	private final double acceleration;
	private final double deceleration;
	private final double turningRadius;
	private double speed = 0.0;

	public SimulatedCar(double length, double width, double[] sensorAngles, Vec3D[] sensorPositionOffsets, Vec3D center, double angle,
			double acceleration, double deceleration, double turningRadius)
	{
		super(length, width, sensorAngles, sensorPositionOffsets, center, angle);
		this.acceleration = acceleration;
		this.deceleration = deceleration;
		this.turningRadius = turningRadius;
	}

	/**
	 * Does not copy actual sensor distances
	 * @param car
	 */
	public SimulatedCar(SimulatedCar car)
	{
		super(car);
		acceleration = car.acceleration;
		deceleration = car.deceleration;
		turningRadius = car.turningRadius;
	}

	/**
	 * For simulation purposes, this executes a time step
	 * @param elapsedTime
	 * @param turningInput
	 * @param targetSpeedInput
	 */
	public void simulateTimeStep(double elapsedTime, int turningInput, double targetSpeedInput)
	{
		// acceleration (will be negative for deceleration)
		final double acc;
		if (targetSpeedInput > speed)
		{
			acc = acceleration;
		}
		else
		{
			acc = -deceleration;
		}
		// Time spent accelerating
		double accTime = (targetSpeedInput - speed) / acc;

		if (accTime > elapsedTime)
		{
			accTime = elapsedTime;
		}

		// Speed of the car at the end of the time step
		double finalSpeed = speed + acc * accTime;

		// The time spent moving at the final velocity for this time step.
		final double finalSpeedTime = elapsedTime - accTime;

		// Total translation, this is made of three components:
		// 1. Vi * ta (ta=time accelerating)
		// 2. 1/2 *a * ta^2 (ta=time accelerating)
		// 3. vf * tf (tf=time at final speed)
		double totalForwardTranslation = speed * accTime + acc * accTime * accTime / 2 + finalSpeed * finalSpeedTime;
		speed = finalSpeed;

		if (turningInput == 0)
		{
			translateForward(totalForwardTranslation);
		}
		else
		{
			// Assume translation happens around circle of given turning radius (with randomness applied)
			double totalRotationRadians = turningInput * totalForwardTranslation / (turningRadius);// * (1 + random.nextDouble() * turnRandomness));

			// Center of rotation perpendicular to car's angle at turning radius distance
			double angleToCenterOfRotation = angle + turningInput * Math.PI / 2;
			Vec3D centerOfRotation = new Vec3D(turningRadius, 0, 0);
			centerOfRotation.rotate2D(angleToCenterOfRotation);
			centerOfRotation.addTo(center);
			rotateCarAroundPoint(totalRotationRadians, centerOfRotation);
		}
	}

	private void translateForward(double forwardTranslation)
	{
		Vec3D translationVector = new Vec3D(forwardTranslation, 0, 0);
		translationVector.rotate2D(angle);
		translate(translationVector);
	}
}
