package car;

import com.pheiffware.lib.geometry.Vec3D;

public class TrackedCar extends Car
{
	// Rate of car acceleration/deceleration
	private final double acceleration;
	private final double deceleration;
	private double speed = 0.0;

	public TrackedCar(double length, double width, double[] sensorAngles, Vec3D[] sensorPositionOffsets, Vec3D center, double angle,
			double acceleration, double deceleration)
	{
		super(length, width, sensorAngles, sensorPositionOffsets, center, angle);
		this.acceleration = acceleration;
		this.deceleration = deceleration;
	}

	/**
	 * Update car and environment based on sensors, timing, etc.
	 * 
	 * @param elapsedTime How much time has passed since last update.
	 * @param knownAngleRadians Readout of compass
	 * @param targetSpeedInput The target speed being requested by input during the time step
	 */
	public void timeStepGuess(double elapsedTime, double knownAngleRadians, double targetSpeedInput)
	{
		// Main.liveCarEnvironmentPanel.clearRenderables();
		// Main.liveCarEnvironmentPanel.addRenderable(new Vec3D(centerOfRotation), new Color(255, 0, 0));

		final double acc;
		if (targetSpeedInput > speed)
		{
			acc = acceleration;
		}
		else
		{
			acc = -deceleration;
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

		double totalRotationRadians = knownAngleRadians - angle;
		predictArc(totalForwardTranslation, totalRotationRadians);
	}

	/**
	 * Predict what happens between time steps when car moves forward while
	 * turning. Assumes constant velocity and angular rotation. With fast enough
	 * time steps, this should be good enough.
	 * 
	 * @param totalForwardTranslation
	 * @param totalRotationRadians
	 */
	protected void predictArc(double totalForwardTranslation, double totalRotationRadians)
	{
		// TODO:IMPLEMENT
		if (totalRotationRadians == 0)
		{
			Vec3D translation = new Vec3D(totalForwardTranslation, 0, 0);
			translation.rotate2D(totalRotationRadians);
			translate(translation);
			return;
		}

	}

}
