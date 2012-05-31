package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;

public class BCHSLib
{
	public static double limitOutput(double value)
	{
		if (value > 1.0)
			return 1.0;
		else if (value < -1.0)
			return -1.0;
		else
			return value;
	}
	
    public static void setToAngle(RobotDrive driveTrain, Gyro gyroscope, double newAngle)
	{		
		double startAngle = gyroscope.getAngle();
		
		if (newAngle > 0)	
			while ((int)gyroscope.getAngle() <= (int)(startAngle + newAngle - 1) || 
					(int)gyroscope.getAngle() >= (int)(startAngle + newAngle + 1))
				driveTrain.drive(0.3, 1.0);
		else if (newAngle < 0) 
			while ((int)gyroscope.getAngle() <= (int)(startAngle - newAngle - 1) || 
					(int)gyroscope.getAngle() >= (int)(startAngle - newAngle + 1))
				driveTrain.drive(0.3, -1.0);
		
		driveTrain.stopMotor();
	}
	
	public static double signSquare(double value)
	{
		if (value < 0.0)
			return -1.0 * value * value;
		else
			return value * value;
	}
	
	public static double voltageToDistance(AnalogChannel Ultrasonic)
	{
		return (Ultrasonic.getVoltage()*1000.0/9.766);
	}
}