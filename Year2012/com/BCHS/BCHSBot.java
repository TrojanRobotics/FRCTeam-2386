package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

public class BCHSBot extends IterativeRobot
{	
	//camera declrations
	AxisCamera camera;
	ParticleAnalysisReport[] particles;
	ParticleAnalysisReport first;
	int firstsWidth, pixelCentre, close;
	AnalogChannel ultraSonic;

	// driving decleration 
	RobotDrive drive;
	BCHSBundle leftSide, rightSide;
	Joystick driveJoystick, secondaryJoystick;
	
	// functional mechanisms
	BCHSLauncher launcher; 
	BCHSRetrieval retrieval;
	BCHSHockey hockeySticks;
	
	
	public void robotInit() 
	{
		camera = AxisCamera.getInstance();

		leftSide = new BCHSBundle(1, 2);
		rightSide = new BCHSBundle(3,4);
		drive = new RobotDrive(leftSide, rightSide);
		driveJoystick = new Joystick(1);
		secondaryJoystick = new Joystick(2);
		ultraSonic = new AnalogChannel(1);

		launcher = new BCHSLauncher(5,6,7,8);
		retrieval = new BCHSRetrieval(5);
		launcher.encoder.setDistancePerPulse(0.0237);
		
		hockeySticks = new BCHSHockey(6,8,9);
	}

	public void autonomousPeriodic() 
	{
		particles = BCHSCamera.getParticles(camera, new int[]{0, 0, 0, 0, 0, 0});
		for (int i = 0; i < particles.length; i++) {
			System.out.println("Particle number " + i + " is " + particles[i]);
		}
	}

	public void teleopPeriodic() 
	{
		drive.arcadeDrive(driveJoystick);
		
		if (driveJoystick.getRawButton(11))
			hockeySticks.set(0.2);
		else if (driveJoystick.getRawButton(10))
			hockeySticks.set(-0.2);
		else
			hockeySticks.set(0);
		
		//bchsLauncher
		if (driveJoystick.getTrigger())
			launcher.set(-0.7);
		else
			launcher.set(0.0);
		
		//bchsRetrieval
		if (driveJoystick.getRawButton(3))
			retrieval.set(-0.5);
		else if (driveJoystick.getRawButton(2))
			retrieval.set(0.5);
		else
			retrieval.set(0.0);
	}
}