package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;


public class LimitSwitch extends SafetyObject 
{
	DigitalInput limitSwitch;
	boolean inverted;
	public LimitSwitch(int channel)
	{
		this(channel, false, null);
	}
	public LimitSwitch(int channel, boolean inverted)
	{
		this(channel, false, null);
	}
	public LimitSwitch(int channel, boolean inverted, SpeedController controller)
	{
		limitSwitch = new DigitalInput(channel);
		this.inverted = inverted;
		this.controller = controller;
	}
	
	public void stop() 
	{
		controller.disable();
	}

	public String getDescription() 
	{
		return "Limit Switch";
	}
	public boolean get()
	{
		if (inverted)
			return !limitSwitch.get();
		else
			return limitSwitch.get();
	}
}
