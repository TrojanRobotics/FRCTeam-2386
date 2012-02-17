package Year2012.com.BCHS;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
<<<<<<< HEAD
// dont forget to change port nums hocketstick,rbotdrive
public class BCHSBot extends IterativeRobot
{		//camera declrations
=======

public class BCHSBot extends IterativeRobot {

>>>>>>> 5624b974216d7a5866bba8b9b15b872b1697e891
	AxisCamera camera;
	ParticleAnalysisReport[] particles;
	ParticleAnalysisReport first;
	int firstsWidth, pixelCentre, close;
	AnalogChannel ultraSonic;

	
	// driving decleration 
	RobotDrive drive;
	Joystick driveJoystick;
	double distance;
	
	
	// lift jag declerations
	BCHSLauncher launcher; 
	BCHSRetrieval retrieval;
	BCHSBundle motorBundle;
	
	
	//hockeystick declerations
	BCHSHockey hockeySticks;

	

<<<<<<< HEAD
	public void robotInit()
	{	
=======
	public void robotInit() 
	{
>>>>>>> 5624b974216d7a5866bba8b9b15b872b1697e891
		camera = AxisCamera.getInstance();

		drive = new RobotDrive(1, 3, 2, 4);
		driveJoystick = new Joystick(1);
		ultraSonic = new AnalogChannel(1);

		launcher = new BCHSLauncher(0, 0, 1, 2);
		retrieval = new BCHSRetrieval(3);
		motorBundle = new BCHSBundle(1,2);
		launcher.encoder.setDistancePerPulse(0.00237);
		
		hockeySticks = new BCHSHockey(5,1,2,3);
	}

	public void autonomousPeriodic() 
	{
		particles = BCHSCamera.getParticles(camera, new int[]{0, 0, 0, 0, 0, 0});
		for (int i = 0; i < particles.length; i++) {
			System.out.println("Particle number " + i + " is " + particles[i]);
		}
	}

<<<<<<< HEAD
	public void teleopPeriodic()
	{	//retrival system
=======
	public void teleopPeriodic() 
	{
>>>>>>> 5624b974216d7a5866bba8b9b15b872b1697e891
		drive.arcadeDrive(driveJoystick);

		
		if (driveJoystick.getRawButton(11))
			hockeySticks.set(0.2);
		else if (driveJoystick.getRawButton(10))
			hockeySticks.set(-0.2);
		else
			hockeySticks.set(0);
		
		//bchsLauncher
		if (driveJoystick.getTrigger())
			launcher.set(0.7);
		else
			launcher.set(0.0);
		
		//bchsRetrieval
		if (driveJoystick.getRawButton(3))
			retrieval.set(0.5);
		else
			retrieval.set(0.0);
	}
}