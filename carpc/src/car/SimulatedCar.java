package car;

import java.util.Random;

import com.pheiffware.lib.geometry.Vec3D;

/**
 * Tracks the car itself's position, angle, etc. Updates it based on signals
 * received from the environment.
 * 
 * @author Steve
 */
public class SimulatedCar extends Car
{
	// TODO: Useless as the time steps ended up being too small.
	private static final double accRandomness = 0.1;
	private static final double turnRandomness = 0.1;
	private static final double speedRandomness = 0.1;
	// Rate of car acceleration/deceleration
	private final double acceleration;
	private final double deceleration;
	private final double turningRadius;
	private final Random random = new Random();
	private double speed = 0.0;

	public SimulatedCar(double length, double width, double[] sensorAngles, Vec3D[] sensorPositionOffsets, Vec3D center, double angle,
			double acceleration, double deceleration, double turningRadius)
	{
		super(length, width, sensorAngles, sensorPositionOffsets, center, angle);
		this.acceleration = acceleration;
		this.deceleration = deceleration;
		this.turningRadius = turningRadius;
	}

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
		// Speed will not be perfect. This is simulated by randomly changing the
		// target speed.
		targetSpeedInput *= (1 + random.nextDouble() * speedRandomness);
		final double acc;
		if (targetSpeedInput > speed)
		{
			// Acceleration will not be perfect. This is simulated by randomly
			// changing the acceleration
			acc = acceleration * (1 + random.nextDouble() * accRandomness);
		}
		else
		{
			// Deceleration will not be perfect. This is simulated by randomly
			// changing the acceleration
			acc = -deceleration * (1 + random.nextDouble() * accRandomness);
		}
		double accTime = (targetSpeedInput - speed) / acc;
		final double coastTime;
		if (accTime < elapsedTime)
		{
			coastTime = elapsedTime - accTime;
		}
		else
		{
			coastTime = 0;
		}
		double totalForwardTranslation = speed * accTime + acc * accTime * accTime / 2 + targetSpeedInput * coastTime;
		if (turningInput == 0)
		{
			translateForward(totalForwardTranslation);
		}
		else
		{
			// Assume translation happens around circle of given turning radius (with randomness applied)
			double totalRotationRadians = turningInput * totalForwardTranslation / (turningRadius * (1 + random.nextDouble() * turnRandomness));

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
