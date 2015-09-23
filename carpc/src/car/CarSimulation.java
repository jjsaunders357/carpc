package car;

import com.pheiffware.lib.geometry.shapes.BaseLineSegment;
import com.pheiffware.lib.geometry.shapes.Sphere;
import com.pheiffware.lib.simulation.Simulation;

public class CarSimulation implements Simulation<CarSimulationState>
{
	// Input can be set to any valid speed for the car based on user input
	public static final String TARGET_SPEED = "TARGET SPEED";

	// Input can be set to -1, 0 or 1 for turn left, not turning or
	// turn right. Actual turning determined by car itself.
	public static final String TURN = "TURN";
	private final SimulatedCar car;
	private final Sphere[] spheres;
	private final BaseLineSegment[] lines;
	private double targetSpeedInput;
	private int turningInput;

	public CarSimulation(SimulatedCar car, Sphere[] spheres, BaseLineSegment[] lines)
	{
		this.car = car;
		this.spheres = spheres;
		this.lines = lines;
	}

	public CarSimulation(CarSimulation carEnvironmentSimulation)
	{
		car = new SimulatedCar(carEnvironmentSimulation.car);
		// Can cheat and not deep copy these as they are static. This is within
		// the confines the copyState() contract.
		spheres = carEnvironmentSimulation.spheres;
		lines = carEnvironmentSimulation.lines;
	}

	@Override
	public void performTimeStep(double elapsedTime)
	{
		car.simulateTimeStep(elapsedTime, turningInput, targetSpeedInput);

	}

	@Override
	public void applyExternalInput(String key, Object value)
	{
		if (key.equals(TARGET_SPEED))
		{
			targetSpeedInput = (double) value;
		}
		else if (key.equals(TURN))
		{
			turningInput = (int) value;
		}

	}

	@Override
	public CarSimulationState copyState()
	{
		return new CarSimulationState(this);
	}

	public final SimulatedCar getCar()
	{
		return car;
	}

	public final Sphere[] getSpheres()
	{
		return spheres;
	}

	public final BaseLineSegment[] getLines()
	{
		return lines;
	}

}
