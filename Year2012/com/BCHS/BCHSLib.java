package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;



public class BCHSLib
{
    public void setToAngle(RobotDrive driveTrain, Gyro gyroscope, double newAngle)
	{		
		double startAngle = gyroscope.getAngle();
		
		if (newAngle > 0)	
		{
			while ((int)gyroscope.getAngle() <= (int)(startAngle + newAngle - 1) || (int)gyroscope.getAngle() >= (int)(startAngle + newAngle + 1))
			{
				driveTrain.drive(0.3, 1.0);
				System.out.println("Moving 1");
			}
			System.out.println("Stopped 1");
		}
		else if (newAngle < 0) 
		{
			while ((int)gyroscope.getAngle() <= (int)(startAngle - newAngle - 1) || (int)gyroscope.getAngle() >= (int)(startAngle - newAngle + 1))
			{
				driveTrain.drive(0.3, -1.0);
				System.out.println("Moving");
			}
			System.out.println("Stopped");
		}
		
		driveTrain.stopMotor();
	}
	public double Ultrasonicdistance(AnalogChannel Ultrasonic)
	{
		return (Ultrasonic.getVoltage()*1000.0/9.766);
	}
}