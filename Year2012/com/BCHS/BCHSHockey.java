package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.Jaguar;

public class BCHSHockey
{
	Jaguar driveJag;
	LimitSwitch topLimit, bottomLimit;
        SafetyHelper safety;
	
	/**
	 * Creates object for hockey sticks.
	 * @param jagChannel Channel for jaguar.
	 * @param topLimitChannel Channel for top limit switch.
	 * @param bottomLimitChannel Channel for bottom limit switch
	 */
	public BCHSHockey(int jagChannel, int topLimitChannel, int bottomLimitChannel)
	{
                safety = SafetyHelper.getInstance();
		driveJag = new Jaguar(jagChannel);
		topLimit = new LimitSwitch(topLimitChannel);
		bottomLimit = new LimitSwitch(bottomLimitChannel);
                
                safety.
	}
	
	/**
	 * Set method for hockey sticks.
	 * @param speed Sets speed of jaguar.
	 */
	public void set(double speed)
	{	
		if (!topLimit.get() && speed > 0)
			driveJag.set(speed);
		else if (!bottomLimit.get() && speed < 0)
			driveJag.set(speed);
		else if (topLimit.get() && bottomLimit.get())
			driveJag.set(speed);
		else
			driveJag.set(0.0);
	}
	
	/**
	 * Stop method for hockey sticks.
	 */
	public void stop()
	{
		driveJag.stopMotor();
	}
}
