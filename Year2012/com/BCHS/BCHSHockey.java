
package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.AnalogChannel;


public class BCHSHockey 
{
	Jaguar limitJag;
	AnalogChannel topSwitch, bottomSwitch;
	
	public BCHSHockey(int jagChannel, int topPort, int bottomPort, int joystickChannel)
	{
		limitJag = new Jaguar(jagChannel);
		topSwitch = new AnalogChannel(topPort);
		bottomSwitch = new AnalogChannel(bottomPort);	
	}
	
	public void set(double speed)
	{
		limitJag.set(speed);
	}
	
	public void stop()
	{
		if (topSwitch.getVoltage() > 0 || bottomSwitch.getVoltage() > 0)
		{
			limitJag.stopMotor();
		}
	}
}
