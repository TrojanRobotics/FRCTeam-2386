package Year2012.com.BCHS;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

public class BCHSBot extends IterativeRobot
{
	AxisCamera camera;
	ParticleAnalysisReport[] particles;
	ParticleAnalysisReport first;
	int firstsWidth, pixelCentre, close;
	AnalogChannel ultraSonic;
	RobotDrive drive;
	Joystick driveJoystick;

	public void robotInit()
	{
		camera = AxisCamera.getInstance();
		drive = new RobotDrive(1, 3, 2, 4);
		driveJoystick = new Joystick(1);
		ultraSonic = new AnalogChannel(1);
	}

	public void autonomousPeriodic()
	{

		BCHSCamera.getParticles(camera, new int[]{0, 0, 0, 0, 0, 0});
		double volts = (ultraSonic.getVoltage() * 1000) / 0.9766;
		first = particles[0];
		firstsWidth = first.imageWidth;

		pixelCentre = camera.getResolution().width / 2;

		double d = 10; //TODO: Implement rangefinder

		double x = pixelCentre - first.center_mass_x;
		int tarPix = 2 * 320 / firstsWidth;

		double a = d / x;

		double theta = MathUtils.atan((tarPix * 0.5) / d);


		if (theta < 0)
		{
			theta = theta * -1;
		}

		for (int i = 0; i < particles.length; i++)
		{
			System.out.println("Particle number " + i + " is " + particles[i]);
		}

		System.out.println("\n\n\n");
		System.out.println("The tangent angle is " + theta);

	}

	public void teleopPeriodic()
	{
		drive.arcadeDrive(driveJoystick);
	}
}