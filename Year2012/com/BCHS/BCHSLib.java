package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;


public class BCHSLib
{
    public void setToAngle(RobotDrive driveTrain, Gyro gyroscope, double newAngle)
	{
		double startAngle = gyroscope.getAngle();
		if (newAngle > 0)
		{
			while (gyroscope.getAngle() != startAngle + newAngle)
			{
				driveTrain.drive(0.5, 1.0);
			}
			driveTrain.stopMotor();
		}
		else if (newAngle < 0) 
		{
			while (gyroscope.getAngle() != startAngle - newAngle)
			{
				driveTrain.drive(0.5, -1.0);
			}
			driveTrain.stopMotor();
		}
	}
}
